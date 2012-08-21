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

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import org.jowidgets.cap.common.api.bean.IBean;
import org.jowidgets.cap.service.api.CapServiceToolkit;
import org.jowidgets.cap.service.api.bean.IBeanPropertyMap;
import org.jowidgets.cap.service.impl.dummy.datastore.EntityDataStore;
import org.jowidgets.cap.service.impl.dummy.datastore.IEntityData;
import org.jowidgets.samples.kitchensink.sample1.common.entity.EntityIds;
import org.jowidgets.samples.kitchensink.sample1.common.entity.IUser;
import org.jowidgets.samples.kitchensink.sample1.service.datastore.UserRoleLinkInitializer;
import org.jowidgets.util.Assert;

public class User extends AbstractSampleBean implements IUser {

	private String name;
	private String lastName;
	private Date dateOfBirth;
	private String gender;
	private Integer country;
	private List<Integer> languages;
	private boolean admin;
	private Boolean married;
	private Double weight;
	private Short height;

	public User(final Long id) {
		super(id);
		this.languages = new LinkedList<Integer>();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(final String name) {
		this.name = name;
		increaseVersion();
	}

	@Override
	public String getLastName() {
		return lastName;
	}

	@Override
	public void setLastName(final String lastName) {
		this.lastName = lastName;
		increaseVersion();
	}

	@Override
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	@Override
	public void setDateOfBirth(final Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
		increaseVersion();
	}

	@Override
	public Double getWeight() {
		return weight;
	}

	@Override
	public void setWeight(final Double weight) {
		this.weight = weight;
		increaseVersion();
	}

	@Override
	public Short getHeight() {
		return height;
	}

	@Override
	public void setHeight(final Short height) {
		this.height = height;
		increaseVersion();
	}

	@Override
	public Double getBmi() {
		if (height == null || weight == null) {
			return null;
		}
		final double quot = (double) (height * height) / 10000;
		if (quot != 0) {
			return weight / quot;
		}
		return null;
	}

	@Override
	public Integer getAge() {
		if (dateOfBirth != null) {
			final Calendar currentDate = new GregorianCalendar();
			final Calendar birthDay = new GregorianCalendar();
			birthDay.setTime(dateOfBirth);
			int result = currentDate.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR) - 1;
			if (currentDate.get(Calendar.DAY_OF_YEAR) >= birthDay.get(Calendar.DAY_OF_YEAR)) {
				result++;
			}
			return Integer.valueOf(result);
		}
		else {
			return null;
		}
	}

	@Override
	public String getGender() {
		return gender;
	}

	@Override
	public void setGender(final String gender) {
		this.gender = gender;
		increaseVersion();
	}

	@Override
	public Integer getCountry() {
		return country;
	}

	@Override
	public void setCountry(final Integer country) {
		this.country = country;
	}

	@Override
	public List<Integer> getLanguages() {
		return new LinkedList<Integer>(languages);
	}

	@Override
	public void setLanguages(final List<Integer> languages) {
		if (languages != null) {
			this.languages = new LinkedList<Integer>(languages);
		}
		else {
			this.languages.clear();
		}
		increaseVersion();
	}

	@Override
	public boolean getAdmin() {
		return admin;
	}

	@Override
	public void setAdmin(final boolean admin) {
		this.admin = admin;
	}

	@Override
	public Boolean getMarried() {
		return married;
	}

	@Override
	public void setMarried(final Boolean married) {
		this.married = married;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getRoles() {
		final List<Long> result = new LinkedList<Long>();
		final IEntityData<IBeanPropertyMap> userRoleLinks = (IEntityData<IBeanPropertyMap>) EntityDataStore.getEntityData(EntityIds.USER_ROLE_LINK);
		for (final IBeanPropertyMap userRoleLink : userRoleLinks.getAllData()) {
			final Object value = userRoleLink.getValue(UserRoleLinkInitializer.USER_ID);
			if (getId().equals(value)) {
				result.add((Long) userRoleLink.getValue(UserRoleLinkInitializer.ROLE_ID));
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setRoles(final List<Long> newRoles) {
		final IEntityData<IBeanPropertyMap> data = (IEntityData<IBeanPropertyMap>) EntityDataStore.getEntityData(EntityIds.USER_ROLE_LINK);
		for (final IBeanPropertyMap bean : new LinkedList<IBeanPropertyMap>(data.getAllData())) {
			final Object value = bean.getValue(UserRoleLinkInitializer.USER_ID);
			if (getId().equals(value)) {
				data.deleteData(bean.getValue(IBean.ID_PROPERTY));
			}
		}
		if (newRoles != null) {
			for (final Long roleId : newRoles) {
				final IEntityData<IBeanPropertyMap> rolesData = (IEntityData<IBeanPropertyMap>) EntityDataStore.getEntityData(EntityIds.ROLE);
				final IBeanPropertyMap role = rolesData.getData(roleId);
				if (role != null) {
					final IBeanPropertyMap bean = CapServiceToolkit.beanPropertyMap(EntityIds.USER_ROLE_LINK);
					bean.setId(data.nextId());
					bean.setValue(UserRoleLinkInitializer.USER_ID, getId());
					bean.setValue(UserRoleLinkInitializer.ROLE_ID, roleId);
					data.add(bean);
				}
				else {
					throw new IllegalArgumentException("Role with the id '" + roleId + "' is unknown");
				}
			}
		}
	}

	public void addLanguage(final Integer language) {
		Assert.paramNotNull(language, "language");
		this.languages.add(language);
		increaseVersion();
	}

	public void removeLanguage(final Integer language) {
		Assert.paramNotNull(language, "language");
		this.languages.remove(language);
		increaseVersion();
	}

}
