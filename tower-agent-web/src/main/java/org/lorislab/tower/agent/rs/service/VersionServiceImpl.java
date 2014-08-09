/*
 * Copyright 2013 lorislab.org.
 *
 * Licensed under the Apache License, AgentVersion 2.0 (the "License");
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
package org.lorislab.tower.agent.rs.service;

import java.util.ArrayList;
import java.util.List;
import org.lorislab.tower.base.dto.converter.ObjectConverter;
import org.lorislab.tower.base.dto.model.Version;
import org.lorislab.tower.agent.factory.ReleaseServiceFactory;
import org.lorislab.tower.agent.model.SearchCriteria;
import org.lorislab.tower.agent.model.SearchResultItem;
import org.lorislab.tower.agent.dto.mapper.ObjectMapper;
import org.lorislab.tower.agent.dto.model.AgentRequest;
import org.lorislab.tower.agent.service.ReleaseService;

/**
 * The version service implementation.
 *
 * @author Andrej Petras
 */
public class VersionServiceImpl implements VersionService {

    /**
     * {@inheritDoc}
     */
    @Override
    public Version getAgentVersion(boolean manifest) throws Exception {                
        Version result = ObjectConverter.create();
        
        ReleaseService service = ReleaseServiceFactory.createService();
        if (service != null) {
            SearchResultItem release = service.getAgentRelease(manifest);
            if (release != null) {
                result = ObjectMapper.update(result, release);
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Version> getVersion(AgentRequest request) throws Exception {
        List<Version> result = new ArrayList<>();        
        ReleaseService service = ReleaseServiceFactory.createService();
        if (service != null) {
            SearchCriteria criteria = ObjectMapper.createCriteria(request);
            List<SearchResultItem> releases = service.getRelease(criteria);
            if (releases != null) {
                for (SearchResultItem item : releases) {
                    Version tmp = ObjectMapper.createVersion(request, item);
                    if (tmp != null) {
                        result.add(tmp);
                    }
                }
            }
        }
        return result;
    }

}
