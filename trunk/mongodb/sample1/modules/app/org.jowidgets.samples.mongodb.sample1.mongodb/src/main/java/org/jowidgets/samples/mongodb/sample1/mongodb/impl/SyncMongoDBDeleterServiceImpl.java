/*
 * Copyright (c) 2013, grossmann
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

package org.jowidgets.samples.mongodb.sample1.mongodb.impl;

import java.util.Collection;

import org.jowidgets.cap.common.api.bean.IBean;
import org.jowidgets.cap.common.api.bean.IBeanKey;
import org.jowidgets.cap.common.api.execution.IExecutableChecker;
import org.jowidgets.cap.common.api.execution.IExecutionCallback;
import org.jowidgets.cap.service.api.CapServiceToolkit;
import org.jowidgets.cap.service.api.adapter.ISyncDeleterService;
import org.jowidgets.cap.service.api.adapter.ISyncExecutorService;
import org.jowidgets.cap.service.api.bean.IBeanAccess;
import org.jowidgets.cap.service.api.deleter.IDeleterServiceInterceptor;
import org.jowidgets.cap.service.api.executor.IBeanExecutor;
import org.jowidgets.cap.service.api.executor.IExecutorServiceBuilder;
import org.jowidgets.samples.mongodb.sample1.mongodb.api.BeanTypeIdMapperProvider;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;

final class SyncMongoDBDeleterServiceImpl<BEAN_TYPE extends IBean> implements ISyncDeleterService {

	private final ISyncExecutorService<Void> executorService;

	SyncMongoDBDeleterServiceImpl(
		final IBeanAccess<BEAN_TYPE> beanAccess,
		final IExecutableChecker<BEAN_TYPE> executableChecker,
		final IDeleterServiceInterceptor<BEAN_TYPE> interceptor,
		final boolean allowDeletedData,
		final boolean allowStaleData) {

		final IExecutorServiceBuilder<BEAN_TYPE, Void> executorServiceBuilder = CapServiceToolkit.executorServiceBuilder(beanAccess);
		if (executableChecker != null) {
			executorServiceBuilder.setExecutableChecker(executableChecker);
		}
		executorServiceBuilder.setAllowDeletedBeans(allowDeletedData).setAllowStaleBeans(allowStaleData);
		executorServiceBuilder.setExecutor(new DeleteExecutor(beanAccess, allowDeletedData, interceptor));
		this.executorService = executorServiceBuilder.buildSyncService();
	}

	@Override
	public void delete(final Collection<? extends IBeanKey> beanKeys, final IExecutionCallback executionCallback) {
		executorService.execute(beanKeys, null, executionCallback);
	}

	private final class DeleteExecutor implements IBeanExecutor<BEAN_TYPE, Void> {

		private final String beanTypeId;
		private final IDeleterServiceInterceptor<BEAN_TYPE> interceptor;

		public DeleteExecutor(
			final IBeanAccess<BEAN_TYPE> beanAccess,
			final boolean allowDeletedData,
			final IDeleterServiceInterceptor<BEAN_TYPE> interceptor) {
			if (!(beanAccess.getBeanTypeId() instanceof String)) {
				throw new IllegalArgumentException("Param 'beanTypeId' must be the collection name and therefore a string!");
			}
			this.beanTypeId = (String) beanAccess.getBeanTypeId();
			this.interceptor = interceptor;
		}

		@Override
		public BEAN_TYPE execute(final BEAN_TYPE data, final Void parameter, final IExecutionCallback executionCallback) {
			CapServiceToolkit.checkCanceled(executionCallback);
			interceptor.beforeDelete(data, executionCallback);
			final DBCollection collection = BeanTypeIdMapperProvider.getCollection(beanTypeId);
			collection.remove((DBObject) data);
			return null;
		}

	}
}
