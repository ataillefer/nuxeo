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

import java.util.Map;

import org.nuxeo.runtime.migration.MigrationDescriptor.MigrationStepDescriptor;
import org.nuxeo.runtime.migration.MigrationService.MigrationStatus;

/**
 * @since 11.1
 */
public class Migration {

    protected String id;

    protected String description;

    protected String label;

    protected MigrationStatus status;

    protected Map<String, MigrationStepDescriptor> steps;

    public Migration(String id, String description, String label, MigrationStatus status,
            Map<String, MigrationStepDescriptor> steps) {
        this.id = id;
        this.description = description;
        this.label = label;
        this.steps = steps;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getLabel() {
        return label;
    }

    public MigrationStatus getStatus() {
        return status;
    }

    public Map<String, MigrationStepDescriptor> getSteps() {
        return steps;
    }

}
