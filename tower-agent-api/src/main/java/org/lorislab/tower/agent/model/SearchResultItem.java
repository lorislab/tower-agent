/*
 * Copyright 2013 lorislab.org.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.lorislab.tower.agent.model;

import java.util.Map;
import org.lorislab.tower.base.model.Prm;

/**
 * The release model.
 *
 * @author Andrej Petras
 */
public class SearchResultItem {

    /**
     * The service.
     */
    private String service;

    /**
     * The project release model.
     */
    private Prm prm;

    /**
     * The manifest
     */
    private Map<String, String> manifest;

    /**
     * Gets the service name.
     *
     * @return the service name.
     */
    public String getService() {
        return service;
    }

    /**
     * Sets the service.
     *
     * @param service the service name.
     */
    public void setService(String service) {
        this.service = service;
    }

    /**
     * Gets the project release model.
     *
     * @return the project release model.
     */
    public Prm getPrm() {
        return prm;
    }

    /**
     * The project release model.
     *
     * @param prm the project release model.
     */
    public void setPrm(Prm prm) {
        this.prm = prm;
    }

    /**
     * Gets the manifest.
     *
     * @return the manifest.
     */
    public Map<String, String> getManifest() {
        return manifest;
    }

    /**
     * Sets the manifest.
     *
     * @param manifest the manifest.
     */
    public void setManifest(Map<String, String> manifest) {
        this.manifest = manifest;
    }

}
