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

package org.jowidgets.samples.kitchensink.sample1.ui.workbench.component.roles;

import java.util.Collections;
import java.util.Set;

import org.jowidgets.cap.common.api.bean.IBeanDto;
import org.jowidgets.cap.ui.api.CapUiToolkit;
import org.jowidgets.cap.ui.api.bean.IBeanProxy;
import org.jowidgets.cap.ui.api.bean.IBeanProxyLabelRenderer;
import org.jowidgets.cap.ui.api.model.ILabelModel;
import org.jowidgets.cap.ui.api.tabfolder.IBeanTabFolderModel;
import org.jowidgets.cap.ui.api.tabfolder.IBeanTabFolderModelBuilder;
import org.jowidgets.cap.ui.tools.model.LabelModelAdapter;
import org.jowidgets.common.types.IVetoable;
import org.jowidgets.samples.kitchensink.sample1.common.entity.EntityIds;
import org.jowidgets.samples.kitchensink.sample1.ui.workbench.command.WorkbenchActions;
import org.jowidgets.util.ITypedKey;
import org.jowidgets.workbench.api.IComponent;
import org.jowidgets.workbench.api.IComponentContext;
import org.jowidgets.workbench.api.IView;
import org.jowidgets.workbench.api.IViewContext;
import org.jowidgets.workbench.toolkit.api.IComponentNodeModel;
import org.jowidgets.workbench.tools.AbstractComponent;

public class RolesTabFolderComponent extends AbstractComponent implements IComponent {

	private final IBeanTabFolderModel<IBeanDto> model;

	public RolesTabFolderComponent(final IComponentNodeModel componentNodeModel, final IComponentContext componentContext) {
		componentContext.setLayout(new RolesComponentDefaultLayout().getLayout());
		this.model = createRoleModel();
	}

	@Override
	public IView createView(final String viewId, final IViewContext context) {
		if (RolesTabFolderView.ID.equals(viewId)) {
			return new RolesTabFolderView(context, model);
		}
		else {
			throw new IllegalArgumentException("View id '" + viewId + "' is not known.");
		}
	}

	@Override
	public void onDispose() {}

	@Override
	public void onActivation() {
		WorkbenchActions.loadAction().addDataModel(model);
		WorkbenchActions.saveAction().addDataModel(model);
		WorkbenchActions.undoAction().addDataModel(model);
		WorkbenchActions.cancelAction().addDataModel(model);

		WorkbenchActions.saveAction().addDataModel(model);
		WorkbenchActions.undoAction().addDataModel(model);
		WorkbenchActions.cancelAction().addDataModel(model);
	}

	@Override
	public void onDeactivation(final IVetoable vetoable) {
		WorkbenchActions.loadAction().removeDataModel(model);
		WorkbenchActions.saveAction().removeDataModel(model);
		WorkbenchActions.undoAction().removeDataModel(model);
		WorkbenchActions.cancelAction().removeDataModel(model);

		WorkbenchActions.saveAction().removeDataModel(model);
		WorkbenchActions.undoAction().removeDataModel(model);
		WorkbenchActions.cancelAction().removeDataModel(model);
	}

	private IBeanTabFolderModel<IBeanDto> createRoleModel() {
		final IBeanTabFolderModelBuilder<IBeanDto> builder = CapUiToolkit.beanTabFolderModelBuilder(EntityIds.ROLE);
		builder.setLabelRenderer(createLabelRenderer());
		return builder.build();
	}

	private IBeanProxyLabelRenderer<IBeanDto> createLabelRenderer() {
		return new IBeanProxyLabelRenderer<IBeanDto>() {
			@Override
			public ILabelModel getLabel(final IBeanProxy<IBeanDto> bean) {
				return new LabelModelAdapter() {

					@Override
					public String getText() {
						return (String) bean.getValue("name");
					}

				};
			}

			@Override
			public Set<String> getPropertyDependencies() {
				return Collections.singleton("name");
			}

			@Override
			public Set<? extends ITypedKey<?>> getCustomPropertyDependencies() {
				return null;
			}
		};
	}

}
