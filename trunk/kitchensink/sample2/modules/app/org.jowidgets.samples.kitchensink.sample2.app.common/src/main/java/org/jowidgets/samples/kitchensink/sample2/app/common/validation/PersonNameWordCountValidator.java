/*
 * Copyright (c) 2012, grossmann
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

package org.jowidgets.samples.kitchensink.sample2.app.common.validation;

import java.util.StringTokenizer;

import org.jowidgets.cap.common.tools.validation.AbstractSingleConcernBeanValidator;
import org.jowidgets.samples.kitchensink.sample2.app.common.bean.IPerson;
import org.jowidgets.util.EmptyCheck;
import org.jowidgets.validation.IValidationResult;
import org.jowidgets.validation.ValidationResult;

public final class PersonNameWordCountValidator extends AbstractSingleConcernBeanValidator<IPerson> {

	private static final long serialVersionUID = 3995642751853640816L;

	public PersonNameWordCountValidator() {
		super(IPerson.NAME_PROPERTY);
	}

	@Override
	public IValidationResult validateBean(final IPerson bean) {
		final String name = bean.getName();
		if (!EmptyCheck.isEmpty(name)) {
			final StringTokenizer tokenizer = new StringTokenizer(name, " ");
			if (tokenizer.countTokens() > 1) {
				return ValidationResult.error("Must no have more than one word");
			}
		}
		return ValidationResult.ok();
	}

}
