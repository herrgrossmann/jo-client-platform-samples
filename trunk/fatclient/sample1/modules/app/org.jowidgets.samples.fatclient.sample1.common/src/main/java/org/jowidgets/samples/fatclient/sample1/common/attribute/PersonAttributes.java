/*
 * Copyright (c) 2014, grossmann
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

package org.jowidgets.samples.fatclient.sample1.common.attribute;

import java.util.Collections;
import java.util.List;

import org.jowidgets.cap.ui.api.attribute.Attributes;
import org.jowidgets.cap.ui.api.attribute.IAttribute;
import org.jowidgets.cap.ui.api.attribute.IBeanAttributeBluePrint;
import org.jowidgets.cap.ui.api.attribute.IBeanAttributesBuilder;
import org.jowidgets.cap.ui.api.control.DateDisplayFormat;
import org.jowidgets.samples.fatclient.sample1.common.bean.ByteValue;
import org.jowidgets.samples.fatclient.sample1.common.bean.ByteValue.ByteUnit;
import org.jowidgets.samples.fatclient.sample1.common.bean.Person;
import org.jowidgets.samples.fatclient.sample1.common.control.ByteValueControlCreator;

public final class PersonAttributes {

	public static final List<IAttribute<Object>> INSTANCE = createInstance();

	private PersonAttributes() {}

	private static List<IAttribute<Object>> createInstance() {
		final IBeanAttributesBuilder builder = Attributes.builder(Person.class);

		//name
		builder.add(Person.NAME_PROPERTY).setLabel("Name");

		//lastname
		builder.add(Person.LASTNAME_PROPERTY).setLabel("Lastname");

		//gender
		builder.add(Person.GENDER_PROPERTY).setLabel("Gender");

		//day of birth 
		IBeanAttributeBluePrint<Object> bp = builder.add(Person.DAY_OF_BIRTH_PROPERTY).setLabel("Day of birth");
		bp.setDisplayFormat(DateDisplayFormat.DATE);

		//quota
		bp = builder.add(Person.QUOTA_PROPERTY).setLabel("Quota");
		bp.setDefaultValue(new ByteValue(250, ByteUnit.GB));
		bp.setControlPanel().setControlCreator(new ByteValueControlCreator());

		//roles
		bp = builder.add(Person.ROLES_PROPERTY).setLabel("Roles");
		bp.setSortable(true);
		bp.setDefaultValue(Collections.emptyList());

		return builder.build();
	};

}
