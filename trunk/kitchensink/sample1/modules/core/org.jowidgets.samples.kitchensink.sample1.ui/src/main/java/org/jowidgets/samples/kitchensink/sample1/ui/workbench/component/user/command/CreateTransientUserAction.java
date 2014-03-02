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

package org.jowidgets.samples.kitchensink.sample1.ui.workbench.component.user.command;

import java.util.Collections;
import java.util.List;

import org.jowidgets.addons.icons.silkicons.SilkIcons;
import org.jowidgets.api.command.IAction;
import org.jowidgets.api.command.IExecutionContext;
import org.jowidgets.cap.ui.api.CapUiToolkit;
import org.jowidgets.cap.ui.api.bean.IBeanProxy;
import org.jowidgets.cap.ui.api.command.IExecutorActionBuilder;
import org.jowidgets.cap.ui.api.execution.BeanSelectionPolicy;
import org.jowidgets.cap.ui.api.execution.IExecutor;
import org.jowidgets.cap.ui.api.table.IBeanTableModel;
import org.jowidgets.cap.ui.api.widgets.IBeanTable;
import org.jowidgets.samples.kitchensink.sample1.common.entity.IUser;
import org.jowidgets.tools.command.ActionWrapper;

public class CreateTransientUserAction extends ActionWrapper {

	public CreateTransientUserAction(final IBeanTable<IUser> table) {
		super(create(table));
	}

	private static IAction create(final IBeanTable<IUser> table) {
		final IExecutorActionBuilder<IUser, Void> builder = CapUiToolkit.actionFactory().executorActionBuilder(table.getModel());
		builder.setText("Add new user");
		builder.setToolTipText("Adds a new user");
		builder.setIcon(SilkIcons.USER);
		builder.setSelectionPolicy(BeanSelectionPolicy.ANY_SELECTION);
		builder.setExecutor(createExecutor(table));
		return builder.build();
	}

	private static IExecutor<IUser, Void> createExecutor(final IBeanTable<IUser> table) {
		return new IExecutor<IUser, Void>() {
			@Override
			public void execute(
				final IExecutionContext executionContext,
				final List<IBeanProxy<IUser>> beans,
				final Void defaultParameter) throws Exception {
				final IBeanTableModel<IUser> model = table.getModel();
				final IBeanProxy<IUser> person = model.addTransientBean();
				model.setSelectedBeans(Collections.singleton(person));
				table.scrollToSelection();
			}
		};
	}

}
