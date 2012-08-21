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

package org.jowidgets.samples.kitchensink.sample1.service;

import java.util.List;

import org.jowidgets.cap.common.api.bean.IBean;
import org.jowidgets.cap.common.api.execution.IExecutableChecker;
import org.jowidgets.cap.common.api.service.IEntityClassProviderService;
import org.jowidgets.cap.common.api.service.IEntityService;
import org.jowidgets.cap.common.api.service.IExecutorService;
import org.jowidgets.cap.common.api.service.ILookUpService;
import org.jowidgets.cap.common.api.service.IReaderService;
import org.jowidgets.cap.service.api.CapServiceToolkit;
import org.jowidgets.cap.service.api.adapter.ISyncLookUpService;
import org.jowidgets.cap.service.api.adapter.ISyncReaderService;
import org.jowidgets.cap.service.api.bean.IBeanAccess;
import org.jowidgets.cap.service.api.executor.IBeanExecutor;
import org.jowidgets.cap.service.api.executor.IExecutorServiceBuilder;
import org.jowidgets.cap.service.impl.dummy.datastore.EntityDataStore;
import org.jowidgets.samples.kitchensink.sample1.common.entity.IUser;
import org.jowidgets.samples.kitchensink.sample1.common.service.executor.ChangeGenderExecutableChecker;
import org.jowidgets.samples.kitchensink.sample1.common.service.executor.UserComponentExecutorServices;
import org.jowidgets.samples.kitchensink.sample1.common.service.reader.ReaderServices;
import org.jowidgets.samples.kitchensink.sample1.common.service.security.AuthorizationProviderServiceId;
import org.jowidgets.samples.kitchensink.sample1.service.datastore.DataStoreInitializer;
import org.jowidgets.samples.kitchensink.sample1.service.entity.SampleEntityClassProviderServiceBuilder;
import org.jowidgets.samples.kitchensink.sample1.service.entity.SampleEntityServiceBuilder;
import org.jowidgets.samples.kitchensink.sample1.service.executor.ChangeBirthdayExecutor;
import org.jowidgets.samples.kitchensink.sample1.service.executor.ChangeGenderExecutor;
import org.jowidgets.samples.kitchensink.sample1.service.executor.LongLastingExecutor;
import org.jowidgets.samples.kitchensink.sample1.service.lookup.Countries;
import org.jowidgets.samples.kitchensink.sample1.service.lookup.CountriesLookUpService;
import org.jowidgets.samples.kitchensink.sample1.service.lookup.Languages;
import org.jowidgets.samples.kitchensink.sample1.service.lookup.LanguagesLookUpService;
import org.jowidgets.samples.kitchensink.sample1.service.lookup.RolesLookUpService;
import org.jowidgets.samples.kitchensink.sample1.service.reader.AllUsersReaderService;
import org.jowidgets.samples.kitchensink.sample1.service.reader.LinkedRolesOfUsersReaderService;
import org.jowidgets.samples.kitchensink.sample1.service.security.AuthorizationProviderServiceImpl;
import org.jowidgets.service.api.IServiceId;
import org.jowidgets.service.tools.ServiceId;
import org.jowidgets.service.tools.ServiceProviderBuilder;
import org.jowidgets.util.IAdapterFactory;

public class SampleServiceProviderBuilder extends ServiceProviderBuilder {

	public SampleServiceProviderBuilder() {
		super();

		DataStoreInitializer.initialize();

		addService(AuthorizationProviderServiceId.ID, new AuthorizationProviderServiceImpl());

		addService(IEntityService.ID, new SampleEntityServiceBuilder(this).build());
		addService(IEntityClassProviderService.ID, new SampleEntityClassProviderServiceBuilder().build());

		addReader(ReaderServices.ALL_USERS, new AllUsersReaderService());
		addReader(ReaderServices.ROLES_OF_USERS, new LinkedRolesOfUsersReaderService());

		addPersonExecutor(
				UserComponentExecutorServices.CHANGE_GENDER,
				new ChangeGenderExecutor(),
				new ChangeGenderExecutableChecker());

		addPersonExecutor(UserComponentExecutorServices.CHANGE_BIRTHDAY, new ChangeBirthdayExecutor());
		addPersonExecutor(UserComponentExecutorServices.LONG_LASTING, new LongLastingExecutor());

		addLookUpService(Countries.LOOK_UP_ID, new CountriesLookUpService());
		addLookUpService(Languages.LOOK_UP_ID, new LanguagesLookUpService());
		addLookUpService(RolesLookUpService.LOOK_UP_ID, new RolesLookUpService());
	}

	private <BEAN_TYPE extends IBean, PARAM_TYPE> void addPersonExecutor(
		final IServiceId<? extends IExecutorService<PARAM_TYPE>> id,
		final IBeanExecutor<? extends BEAN_TYPE, PARAM_TYPE> beanExecutor) {
		addPersonExecutor(id, beanExecutor, null);
	}

	private <BEAN_TYPE extends IBean, PARAM_TYPE> void addPersonExecutor(
		final IServiceId<? extends IExecutorService<PARAM_TYPE>> id,
		final IBeanExecutor<? extends BEAN_TYPE, PARAM_TYPE> beanExecutor,
		final IExecutableChecker<? extends BEAN_TYPE> executableChecker) {
		addExecutor(id, beanExecutor, executableChecker, EntityDataStore.getEntityData(IUser.class), IUser.ALL_PROPERTIES);
	}

	private <PARAM_TYPE> void addReader(
		final IServiceId<? extends IReaderService<PARAM_TYPE>> id,
		final ISyncReaderService<PARAM_TYPE> readerService) {
		final IAdapterFactory<IReaderService<PARAM_TYPE>, ISyncReaderService<PARAM_TYPE>> adapterFactoryProvider;
		adapterFactoryProvider = CapServiceToolkit.adapterFactoryProvider().reader();
		addService(id, adapterFactoryProvider.createAdapter(readerService));
	}

	private <BEAN_TYPE extends IBean, PARAM_TYPE> void addExecutor(
		final IServiceId<? extends IExecutorService<? extends PARAM_TYPE>> id,
		final IBeanExecutor<? extends BEAN_TYPE, ? extends PARAM_TYPE> beanExecutor,
		final IExecutableChecker<? extends BEAN_TYPE> executableChecker,
		final IBeanAccess<? extends BEAN_TYPE> beanAccess,
		final List<String> propertyNames) {

		final IExecutorServiceBuilder<BEAN_TYPE, PARAM_TYPE> builder = CapServiceToolkit.executorServiceBuilder(beanAccess);
		builder.setExecutor(beanExecutor);
		builder.setExecutableChecker(executableChecker);
		builder.setBeanDtoFactory(propertyNames);

		addService(id, builder.build());
	}

	private void addLookUpService(final Object lookUpId, final ISyncLookUpService lookUpService) {
		final IAdapterFactory<ILookUpService, ISyncLookUpService> adapterFactoryProvider;
		adapterFactoryProvider = CapServiceToolkit.adapterFactoryProvider().lookup();
		final ILookUpService asyncService = adapterFactoryProvider.createAdapter(lookUpService);
		final ServiceId<ILookUpService> serviceId = new ServiceId<ILookUpService>(lookUpId, ILookUpService.class);
		addService(serviceId, asyncService);
	}
}
