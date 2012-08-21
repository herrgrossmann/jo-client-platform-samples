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

package org.jowidgets.samples.kitchensink.sample1.service.entity;

import java.util.LinkedList;
import java.util.List;

import org.jowidgets.cap.common.api.CapCommonToolkit;
import org.jowidgets.cap.common.api.bean.IBeanDtoDescriptor;
import org.jowidgets.cap.common.api.bean.IProperty;
import org.jowidgets.cap.common.api.bean.IPropertyBuilder;
import org.jowidgets.samples.kitchensink.sample1.service.datastore.GenericBeanInitializer;

public class GenericBeanDtoDescriptorBuilder {

	private final List<IProperty> properties;

	public GenericBeanDtoDescriptorBuilder() {

		this.properties = new LinkedList<IProperty>();

		final String label = Messages.getString("GenericBeanDtoDescriptorBuilder.column_n_short"); //$NON-NLS-1$
		final String labelLong = Messages.getString("GenericBeanDtoDescriptorBuilder.column_n_long"); //$NON-NLS-1$
		final String description = Messages.getString("GenericBeanDtoDescriptorBuilder.description_of_column_n"); //$NON-NLS-1$

		final IPropertyBuilder propertyBuilder = CapCommonToolkit.propertyBuilder();
		propertyBuilder.setValueType(String.class).setSortable(true).setFilterable(true);
		final List<String> propertyNames = GenericBeanInitializer.ALL_PROPERTIES;
		for (int columnIndex = 0; columnIndex < propertyNames.size(); columnIndex++) {
			propertyBuilder.setName(propertyNames.get(columnIndex));
			propertyBuilder.setLabel(label.replace("%1", String.valueOf(columnIndex))); //$NON-NLS-1$
			propertyBuilder.setLabelLong(labelLong.replace("%1", String.valueOf(columnIndex))); //$NON-NLS-1$
			propertyBuilder.setDescription(description.replace("%1", String.valueOf(columnIndex))); //$NON-NLS-1$
			if (columnIndex < 80) {
				propertyBuilder.setReadonly(false);
			}
			else {
				propertyBuilder.setReadonly(true);
			}
			properties.add(propertyBuilder.build());
		}
	}

	IBeanDtoDescriptor build() {
		return CapCommonToolkit.dtoDescriptor(properties);
	}

}
