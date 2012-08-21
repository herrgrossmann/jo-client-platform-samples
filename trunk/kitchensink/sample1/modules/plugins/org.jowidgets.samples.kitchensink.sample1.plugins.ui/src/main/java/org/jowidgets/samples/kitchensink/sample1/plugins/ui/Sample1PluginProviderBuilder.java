/*
 * Copyright (c) 2011 innoSysTec (R) GmbH, Germany. All rights reserved.
 * Original Author: grossmann
 * Creation Date: 18.10.2011
 */

package org.jowidgets.samples.kitchensink.sample1.plugins.ui;

import org.jowidgets.cap.ui.api.plugin.IBeanTableMenuContributionPlugin;
import org.jowidgets.cap.ui.api.plugin.IBeanTableMenuInterceptorPlugin;
import org.jowidgets.plugin.tools.PluginProviderBuilder;
import org.jowidgets.samples.kitchensink.sample1.common.entity.EntityIds;
import org.jowidgets.samples.kitchensink.sample1.common.entity.IUser;
import org.jowidgets.samples.kitchensink.sample1.plugins.ui.table.UserMenuContributionPlugin;
import org.jowidgets.samples.kitchensink.sample1.plugins.ui.table.UserMenuInterceptorPlugin;

public final class Sample1PluginProviderBuilder extends PluginProviderBuilder {

	public Sample1PluginProviderBuilder() {
		addPlugin(
				IBeanTableMenuInterceptorPlugin.ID,
				new UserMenuInterceptorPlugin(),
				IBeanTableMenuInterceptorPlugin.ENTITIY_ID_PROPERTY_KEY,
				IUser.class,
				EntityIds.VIRTUAL_USERS_OF_ROLES);

		addPlugin(
				IBeanTableMenuContributionPlugin.ID,
				new UserMenuContributionPlugin(),
				IBeanTableMenuContributionPlugin.ENTITIY_ID_PROPERTY_KEY,
				IUser.class,
				EntityIds.VIRTUAL_USERS_OF_ROLES);
	}

}
