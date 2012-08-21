/*
 * Copyright (c) 2011, grossmann
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 * * Neither the name of the jo-widgets.org nor the
 *   names of its contributors may be used to endorse or promote products
 *   derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL jo-widgets.org BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 */

package org.jowidgets.samples.kitchensink.sample1.service.reader;

import java.util.LinkedList;
import java.util.List;

import org.jowidgets.cap.common.api.bean.IBean;
import org.jowidgets.cap.common.api.bean.IBeanDto;
import org.jowidgets.cap.common.api.bean.IBeanKey;
import org.jowidgets.cap.common.api.execution.IExecutionCallback;
import org.jowidgets.cap.common.api.filter.IFilter;
import org.jowidgets.cap.common.api.sort.ISort;
import org.jowidgets.cap.service.api.CapServiceToolkit;
import org.jowidgets.cap.service.api.adapter.ISyncReaderService;
import org.jowidgets.cap.service.api.bean.IBeanDtoFactory;
import org.jowidgets.cap.service.impl.dummy.datastore.EntityDataStore;
import org.jowidgets.cap.service.impl.dummy.datastore.IEntityData;
import org.jowidgets.cap.service.tools.bean.BeanDtoFactoryHelper;
import org.jowidgets.samples.kitchensink.sample1.common.entity.IUser;
import org.jowidgets.security.api.SecurityContextHolder;

public class AllUsersReaderService implements ISyncReaderService<Integer> {

	private final IEntityData<? extends IBean> data;
	private final IBeanDtoFactory<IUser> dtoFactory;

	public AllUsersReaderService() {
		this.data = EntityDataStore.getEntityData(IUser.class);
		this.dtoFactory = CapServiceToolkit.dtoFactory(IUser.class, IUser.ALL_PROPERTIES);
	}

	@Override
	public List<IBeanDto> read(
		final List<? extends IBeanKey> parentBeanKeys,
		final IFilter filter,
		final List<? extends ISort> sorting,
		final int firstRow,
		final int maxRows,
		final Integer delay,
		final IExecutionCallback executionCallback) {

		if (delay != null) {
			try {
				if (delay.intValue() > 100) {
					final int sleepTime = delay.intValue() / 100;
					for (int i = 0; i < 100 && !executionCallback.isCanceled(); i++) {
						Thread.sleep(sleepTime);
					}
				}
				else {
					Thread.sleep(Math.max(delay.intValue(), 0));
				}
			}

			catch (final InterruptedException e) {
				throw new RuntimeException(e);
			}
		}

		//CHECKSTYLE:OFF
		System.out.println(SecurityContextHolder.getSecurityContext());
		//CHECKSTYLE:ON

		if (filter == null && (sorting == null || sorting.size() == 0)) {
			return BeanDtoFactoryHelper.createDtos(dtoFactory, data.getAllData(firstRow, maxRows), executionCallback);
		}
		else {
			List<IBeanDto> result = BeanDtoFactoryHelper.createDtos(dtoFactory, data.getAllData(), executionCallback);

			if (filter != null) {
				result = CapServiceToolkit.beanDtoCollectionFilter().filter(result, filter, executionCallback);
			}
			if (sorting != null && sorting.size() > 0) {
				result = CapServiceToolkit.beanDtoCollectionSorter().sort(result, sorting, executionCallback);
			}

			if (result.size() >= firstRow) {
				return new LinkedList<IBeanDto>(result.subList(firstRow, Math.min(firstRow + maxRows, result.size())));
			}
			else {
				return new LinkedList<IBeanDto>();
			}
		}
	}

	@Override
	public Integer count(
		final List<? extends IBeanKey> parentBeanKeys,
		final IFilter filter,
		final Integer delay,
		final IExecutionCallback executionCallback) {
		if (filter == null) {
			return Integer.valueOf(data.getAllData().size());
		}
		else {
			final List<IBeanDto> result = BeanDtoFactoryHelper.createDtos(dtoFactory, data.getAllData(), executionCallback);
			return Integer.valueOf(CapServiceToolkit.beanDtoCollectionFilter().filter(result, filter, executionCallback).size());
		}
	}

}
