/*
 * Copyright 2014 lorislab.org.
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
package org.lorislab.tower.agent.dto.mapper;

import java.util.UUID;
import org.lorislab.tower.base.dto.converter.ObjectConverter;
import org.lorislab.tower.base.dto.model.Version;
import org.lorislab.tower.agent.model.SearchCriteria;
import org.lorislab.tower.agent.model.SearchResultItem;
import org.lorislab.tower.agent.dto.model.AgentRequest;

/**
 * The object mapper.
 *
 * @author Andrej Petras
 */
public final class ObjectMapper {

    /**
     * The default constructor.
     */
    private ObjectMapper() {
        // empty constructor
    }

    /**
     * Creates the agent request.
     *
     * @return the agent request.
     */
    public static AgentRequest create() {
        AgentRequest request = new AgentRequest();
        request.manifest = true;
        request.uid = UUID.randomUUID().toString();
        return request;
    }

    /**
     * Creates the search criteria from the request.
     *
     * @param request the client request.
     * @return the search criteria.
     */
    public static SearchCriteria createCriteria(AgentRequest request) {
        SearchCriteria criteria = new SearchCriteria();
        criteria.setManifest(request.manifest);
        criteria.setService(request.service);
        return criteria;
    }

    /**
     * Updates the version with the search result item.
     *
     * @param version the version.
     * @param release the search result item.
     * @return the version.
     */
    public static Version update(Version version, SearchResultItem release) {
        if (version != null && release != null) {
            version.service = release.getService();
            // add arm
            version = ObjectConverter.update(version, release.getPrm());
            // add manifest
            version.manifest = release.getManifest();
        }
        return version;
    }

    /**
     * Creates the version from the release model.
     *
     * @param release the release model.
     * @param request the request.
     * @return the version model.
     */
    public static Version createVersion(AgentRequest request, SearchResultItem release) {
        Version result = ObjectConverter.create();
        if (request.uid != null) {
            result.uid = request.uid;
        }
        result = update(result, release);
        return result;
    }
}
