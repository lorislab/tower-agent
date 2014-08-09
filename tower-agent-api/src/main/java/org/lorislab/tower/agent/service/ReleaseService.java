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
package org.lorislab.tower.agent.service;

import java.util.List;
import org.lorislab.tower.agent.model.SearchResultItem;
import org.lorislab.tower.agent.model.SearchCriteria;

/**
 * The release service.
 *
 * @author Andrej Petras
 */
public interface ReleaseService {

    /**
     * Gets the release information of the agent.
     *
     * @param manifest the manifest flag.
     * 
     * @return the release information of the agent.
     */
    SearchResultItem getAgentRelease(boolean manifest);

    /**
     * Gets the release information of the application or all.
     *
     * @param criteria the criteria.
     * 
     * @return the list of release information of the applications.
     */
    List<SearchResultItem> getRelease(SearchCriteria criteria);       
}
