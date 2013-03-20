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

package org.jowidgets.samples.kitchensink.sample1.ui.workbench.component.generic;

import org.jowidgets.cap.common.api.service.IEntityService;
import org.jowidgets.cap.ui.api.CapUiToolkit;
import org.jowidgets.cap.ui.api.table.IBeanTableModel;
import org.jowidgets.cap.ui.api.table.IBeanTableModelBuilder;
import org.jowidgets.cap.ui.api.workbench.CapWorkbenchActionsProvider;
import org.jowidgets.common.types.IVetoable;
import org.jowidgets.samples.kitchensink.sample1.common.entity.EntityIds;
import org.jowidgets.samples.kitchensink.sample1.ui.attribute.GenericBeanAttributesFactory;
import org.jowidgets.samples.kitchensink.sample1.ui.workbench.component.generic.view.GenericBeanTableView;
import org.jowidgets.service.api.ServiceProvider;
import org.jowidgets.workbench.api.IComponent;
import org.jowidgets.workbench.api.IComponentContext;
import org.jowidgets.workbench.api.IView;
import org.jowidgets.workbench.api.IViewContext;
import org.jowidgets.workbench.toolkit.api.IComponentNodeModel;
import org.jowidgets.workbench.tools.AbstractComponent;

public class GenericBeanComponent extends AbstractComponent implements IComponent {

	private final IBeanTableModel<Object> tableModel;

	public GenericBeanComponent(final IComponentNodeModel componentNodeModel, final IComponentContext componentContext) {
		componentContext.setLayout(new GenericBeanComponentDefaultLayout().getLayout());
		this.tableModel = createTableModel();
	}

	@Override
	public IView createView(final String viewId, final IViewContext context) {
		if (GenericBeanTableView.ID.equals(viewId)) {
			return new GenericBeanTableView(context, tableModel);
		}
		else {
			throw new IllegalArgumentException("View id '" + viewId + "' is not known.");
		}
	}

	@Override
	public void onDispose() {}

	@Override
	public void onActivation() {
		CapWorkbenchActionsProvider.loadAction().addDataModel(tableModel);
		CapWorkbenchActionsProvider.saveAction().addDataModel(tableModel);
		CapWorkbenchActionsProvider.undoAction().addDataModel(tableModel);
		CapWorkbenchActionsProvider.cancelAction().addDataModel(tableModel);
	}

	@Override
	public void onDeactivation(final IVetoable vetoable) {
		CapWorkbenchActionsProvider.loadAction().removeDataModel(tableModel);
		CapWorkbenchActionsProvider.saveAction().removeDataModel(tableModel);
		CapWorkbenchActionsProvider.undoAction().removeDataModel(tableModel);
		CapWorkbenchActionsProvider.cancelAction().removeDataModel(tableModel);
	}

	private IBeanTableModel<Object> createTableModel() {
		final IBeanTableModelBuilder<Object> builder = CapUiToolkit.beanTableModelBuilder(Object.class);
		builder.setAttributes(new GenericBeanAttributesFactory().tableAttributes());
		builder.setEntityServices(ServiceProvider.getService(IEntityService.ID).getBeanServices(EntityIds.GENERIC_BEAN));
		return builder.build();
	}

}
