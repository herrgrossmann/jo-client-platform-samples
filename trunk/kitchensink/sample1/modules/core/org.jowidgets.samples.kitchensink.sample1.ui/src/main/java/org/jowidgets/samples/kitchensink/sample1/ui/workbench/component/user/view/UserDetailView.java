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

package org.jowidgets.samples.kitchensink.sample1.ui.workbench.component.user.view;

import org.jowidgets.api.widgets.IContainer;
import org.jowidgets.cap.ui.api.CapUiToolkit;
import org.jowidgets.cap.ui.api.table.IBeanTableModel;
import org.jowidgets.cap.ui.api.widgets.IBeanSelectionFormBluePrint;
import org.jowidgets.samples.kitchensink.sample1.common.entity.IUser;
import org.jowidgets.tools.layout.MigLayoutFactory;
import org.jowidgets.workbench.api.IViewContext;
import org.jowidgets.workbench.tools.AbstractView;

public class UserDetailView extends AbstractView {

	public static final String ID = UserDetailView.class.getName();
	public static final String DEFAULT_LABEL = Messages.getString("UserDetailView.user_details"); //$NON-NLS-1$
	public static final String DEFAULT_TOOLTIP = Messages.getString("UserDetailView.user_details_tooltip"); //$NON-NLS-1$

	public UserDetailView(final IViewContext context, final IBeanTableModel<IUser> parentModel) {
		final IContainer container = context.getContainer();
		container.setLayout(MigLayoutFactory.growingCellLayout());
		final IBeanSelectionFormBluePrint selectionFormBp = CapUiToolkit.bluePrintFactory().beanSelectionForm(parentModel);
		container.add(selectionFormBp, MigLayoutFactory.GROWING_CELL_CONSTRAINTS);
	}
}
