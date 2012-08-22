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

package org.jowidgets.samples.kitchensink.sample2.app.service.util;

import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public final class DynamicSampleDataGeneratorStarter {

	private DynamicSampleDataGeneratorStarter() {}

	public static void main(final String[] args) throws Exception {
		final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("sample2PersistenceUnit");
		final SampleDataGenerator sampleDataGenerator = new SampleDataGenerator();
		final Random random = new Random();
		long startOffset = getMaxId(entityManagerFactory) + 1;
		while (true) {
			Thread.sleep(500);
			final int count = random.nextInt(3);
			sampleDataGenerator.createPersons(entityManagerFactory, startOffset, 1, count);
			startOffset += count;
		}
	}

	private static long getMaxId(final EntityManagerFactory entityManagerFactory) {
		final EntityManager em = entityManagerFactory.createEntityManager();
		final Query query = em.createQuery("SELECT MAX(p.id) FROM Person p");
		Object result;
		try {
			result = query.getSingleResult();
			if (result instanceof Number) {
				return ((Number) result).longValue();
			}
		}
		catch (final Exception e) {
			//CHECKSTYLE:OFF
			e.printStackTrace();
			//CHECKSTYLE:ON
		}
		return -1;
	}
}
