/*
 * (C) Copyright 2020 Nuxeo (http://nuxeo.com/) and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 *     Nour AL KOTOB
 */

package org.nuxeo.runtime.migration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.nuxeo.runtime.test.runner.Deploy;
import org.nuxeo.runtime.test.runner.Features;
import org.nuxeo.runtime.test.runner.FeaturesRunner;
import org.nuxeo.runtime.test.runner.RuntimeFeature;
import org.nuxeo.runtime.test.runner.TransactionalFeature;

/**
 * @since 11.1
 */
@RunWith(FeaturesRunner.class)
@Features({ RuntimeFeature.class, TransactionalFeature.class })
@Deploy("org.nuxeo.runtime.kv")
@Deploy("org.nuxeo.runtime.cluster")
@Deploy("org.nuxeo.runtime.migration:OSGI-INF/migration-service.xml")
@Deploy("org.nuxeo.runtime.migration.tests:OSGI-INF/dummy-migration.xml")
public class TestMigrationService {

    @Inject
    protected TransactionalFeature txFeature;

    @Inject
    protected MigrationService migrationService;

    @Test
    public void testGetMigrations() {
        var migrations = migrationService.getMigrations();
        assertFalse(migrations.isEmpty());
    }

    @Test
    public void testGetMigration() {
        Migration migration = migrationService.getMigration("dummy-migration");
        assertEquals("dummy-migration", migration.getId());
        assertEquals("Dummy Migration", migration.getDescription());
        assertEquals("migration.dummy", migration.getLabel());
        assertEquals("before", migration.getStatus().getState());
        assertEquals("Migrate dummy state from before to after",
                migration.getSteps().get("before-to-after").getDescription());

    }
    @Test
    public void testRunMigration() throws InterruptedException {
        String dummy = "dummy-migration";
        assertEquals("before", migrationService.getMigration(dummy).getStatus().getState());
        migrationService.probeAndRun(dummy);
        Thread.sleep(100);
        assertEquals("after", migrationService.getMigration(dummy).getStatus().getState());
        try {
            migrationService.probeAndRun(dummy);
            fail("should fail");
        } catch (java.lang.IllegalStateException e) {
            assertEquals("Migration dummy-migration needs to have exactly one step to run", e.getMessage());
        }
    }

}
