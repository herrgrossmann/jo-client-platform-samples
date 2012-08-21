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

package org.jowidgets.samples.kitchensink.sample1.ui.workbench;

import org.jowidgets.api.model.item.IMenuModel;
import org.jowidgets.cap.ui.api.login.LoginService;
import org.jowidgets.common.types.Dimension;
import org.jowidgets.common.types.IVetoable;
import org.jowidgets.samples.kitchensink.sample1.ui.SampleDefaultsInitializer;
import org.jowidgets.samples.kitchensink.sample1.ui.workbench.application.SampleApplication;
import org.jowidgets.samples.kitchensink.sample1.ui.workbench.command.WorkbenchActions;
import org.jowidgets.workbench.api.ILoginCallback;
import org.jowidgets.workbench.api.IWorkbench;
import org.jowidgets.workbench.api.IWorkbenchFactory;
import org.jowidgets.workbench.toolkit.api.IWorkbenchModel;
import org.jowidgets.workbench.toolkit.api.IWorkbenchModelBuilder;
import org.jowidgets.workbench.toolkit.api.WorkbenchToolkit;
import org.jowidgets.workbench.tools.WorkbenchModelBuilder;

public class SampleWorkbench implements IWorkbenchFactory {

	@Override
	public IWorkbench create() {
		SampleDefaultsInitializer.initialize();

		final IWorkbenchModelBuilder builder = new WorkbenchModelBuilder();
		builder.setInitialDimension(new Dimension(1024, 768));
		builder.setInitialSplitWeight(0.2);
		builder.setLabel(Messages.getString("SampleWorkbench.sample_application_1")); //$NON-NLS-1$
		builder.setLoginCallback(new ILoginCallback() {
			@Override
			public void onLogin(final IVetoable vetoable) {
				final boolean doLogin = LoginService.doLogin();
				if (!doLogin) {
					vetoable.veto();
				}
			}
		});

		final IWorkbenchModel model = builder.build();
		model.addApplication(new SampleApplication().getModel());

		model.getToolBar().addAction(WorkbenchActions.loadAction());
		model.getToolBar().addAction(WorkbenchActions.cancelAction());
		model.getToolBar().addSeparator();
		model.getToolBar().addAction(WorkbenchActions.undoAction());
		model.getToolBar().addAction(WorkbenchActions.saveAction());

		final IMenuModel dataMenu = model.getMenuBar().addMenu(Messages.getString("SampleWorkbench.data")); //$NON-NLS-1$
		dataMenu.addAction(WorkbenchActions.loadAction());
		dataMenu.addAction(WorkbenchActions.cancelAction());
		dataMenu.addSeparator();
		dataMenu.addAction(WorkbenchActions.undoAction());
		dataMenu.addAction(WorkbenchActions.saveAction());

		return WorkbenchToolkit.getWorkbenchPartFactory().workbench(model);
	}

}
