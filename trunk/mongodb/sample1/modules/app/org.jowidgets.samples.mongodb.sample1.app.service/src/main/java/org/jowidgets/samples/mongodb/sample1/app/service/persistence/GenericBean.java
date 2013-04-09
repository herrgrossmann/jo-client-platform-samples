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

package org.jowidgets.samples.mongodb.sample1.app.service.persistence;

import org.jowidgets.samples.mongodb.sample1.app.common.bean.IGenericBean;
import org.jowidgets.samples.mongodb.sample1.mongodb.tools.DBObjectBean;
import org.jowidgets.util.Assert;

import com.mongodb.DBObject;

public class GenericBean extends DBObjectBean implements IGenericBean {

	private static final long serialVersionUID = -872265010584978183L;

	protected GenericBean(final String beanTypeId) {
		this(beanTypeId, null);
	}

	protected GenericBean(final String beanTypeId, final DBObject dbObject) {
		Assert.paramNotEmpty(beanTypeId, "beanTypeId");
		put(BEAN_TYPE_ID_TYPE_PROPERTY, beanTypeId);
		if (dbObject != null) {
			putAll(dbObject.toMap());
		}
	}

	@Override
	public String getBeanTypeId() {
		return getString(BEAN_TYPE_ID_TYPE_PROPERTY);
	}

}
