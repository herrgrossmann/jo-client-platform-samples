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

package org.jowidgets.samples.kitchensink.sample1.ui.attribute;

import java.util.LinkedList;
import java.util.List;

import org.jowidgets.cap.common.api.bean.IProperty;
import org.jowidgets.cap.common.api.service.IEntityService;
import org.jowidgets.cap.ui.api.CapUiToolkit;
import org.jowidgets.cap.ui.api.attribute.IAttribute;
import org.jowidgets.cap.ui.api.attribute.IAttributeBuilder;
import org.jowidgets.cap.ui.api.attribute.IAttributeGroup;
import org.jowidgets.cap.ui.api.attribute.IAttributeToolkit;
import org.jowidgets.i18n.api.MessageReplacer;
import org.jowidgets.samples.kitchensink.sample1.common.entity.EntityIds;
import org.jowidgets.service.api.ServiceProvider;

public class GenericBeanAttributesFactory {

	private static final String GROUP_N = Messages.getString("GenericBeanAttributesFactory.group_n");
	private static final String DESCRIPTION_OF_GROUP_N = Messages.getString("GenericBeanAttributesFactory.description_of_group_n");

	private final IAttributeToolkit attributeToolkit;

	public GenericBeanAttributesFactory() {
		this.attributeToolkit = CapUiToolkit.attributeToolkit();
	}

	public List<IAttribute<String>> tableAttributes() {
		final List<IAttribute<String>> attributes = new LinkedList<IAttribute<String>>();

		final List<IProperty> properties = ServiceProvider.getService(IEntityService.ID).getDescriptor(EntityIds.GENERIC_BEAN).getProperties();

		int groupIndex = 0;
		IAttributeGroup group = null;
		int columnIndex = 0;
		for (final IProperty property : properties) {
			final IAttributeBuilder<String> builder = attributeToolkit.createAttributeBuilder(property);

			if (columnIndex % 8 == 0) {
				final String id = MessageReplacer.replace(GROUP_N, String.valueOf(groupIndex));
				final String description = MessageReplacer.replace(DESCRIPTION_OF_GROUP_N, String.valueOf(groupIndex));

				group = new IAttributeGroup() {

					@Override
					public String getId() {
						return id;
					}

					@Override
					public String getLabel() {
						return id;
					}

					@Override
					public String getDescription() {
						return description;
					}
				};
				groupIndex++;
			}

			builder.setGroup(group);

			attributes.add(builder.build());
			columnIndex++;
		}

		return attributes;

	}
}
