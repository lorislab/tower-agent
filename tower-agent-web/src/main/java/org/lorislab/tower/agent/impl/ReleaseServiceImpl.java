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
package org.lorislab.tower.agent.impl;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.jar.Manifest;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jboss.as.server.CurrentServiceContainer;
import org.jboss.as.server.deployment.AbstractDeploymentUnitService;
import org.jboss.as.server.deployment.Attachments;
import org.jboss.as.server.deployment.DeploymentUnit;
import org.jboss.as.server.deployment.Services;
import org.jboss.as.server.deployment.module.ResourceRoot;
import org.jboss.msc.service.ServiceContainer;
import org.jboss.msc.service.ServiceController;
import org.jboss.msc.service.ServiceName;
import org.kohsuke.MetaInfServices;
import org.lorislab.tower.base.loader.PrmLoader;
import org.lorislab.tower.base.loader.ManifestLoader;
import org.lorislab.tower.base.model.Prm;
import org.lorislab.tower.agent.model.SearchCriteria;
import org.lorislab.tower.agent.model.SearchResultItem;
import org.lorislab.tower.agent.service.ReleaseService;

/**
 * The release service implementation.
 *
 * @author Andrej Petras
 */
@MetaInfServices
public class ReleaseServiceImpl implements ReleaseService {

    /**
     * {@inheritDoc}
     */
    @Override
    public SearchResultItem getAgentRelease(boolean manifest) {
        SearchResultItem result = new SearchResultItem();

        // load arm model
        Prm bridge = PrmLoader.load(ReleaseServiceImpl.class);
        result.setPrm(bridge);

        // load manifest 
        if (manifest) {
            Map<String, String> tmp = ManifestLoader.loadManifestFromToMap(ReleaseServiceImpl.class);
            result.setManifest(tmp);
        }

        return result;
    }

    /**
     * Gets the result information from the service.
     *
     * @param criteria the criteria.
     * @param name the service name.
     * @return the search result item.
     */
    private static SearchResultItem createSearchResultItem(boolean manifest, ServiceName name) {
        SearchResultItem result = new SearchResultItem();

        ServiceContainer container = CurrentServiceContainer.getServiceContainer();
        ServiceController<?> serviceController = container.getService(name);
        AbstractDeploymentUnitService service = (AbstractDeploymentUnitService) serviceController.getService();
        DeploymentUnit unit = service.getValue();

        result.setService(name.getSimpleName());

        ResourceRoot root = unit.getAttachment(Attachments.DEPLOYMENT_ROOT);

        try {
            Prm bridge = PrmLoader.load(root.getRoot().asFileURL());
            result.setPrm(bridge);
        } catch (MalformedURLException ex) {
            Logger.getLogger(ReleaseServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (manifest) {
            Manifest man = root.getAttachment(Attachments.MANIFEST);
            Map<String, String> tmp = ManifestLoader.loadManifestToMap(man);
            result.setManifest(tmp);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SearchResultItem> getRelease(SearchCriteria criteria) {
        List<SearchResultItem> result = new ArrayList<>();
        ServiceContainer container = CurrentServiceContainer.getServiceContainer();        
        if (criteria != null) {
            boolean manifest = criteria.isManifest();
            if (criteria.getService() != null && !criteria.getService().isEmpty()) {
                ServiceName name = Services.JBOSS_DEPLOYMENT_UNIT.append(criteria.getService());
                SearchResultItem item = createSearchResultItem(manifest, name);
                if (item != null) {
                    result.add(item);
                }
            } else {
                List<ServiceName> names = container.getServiceNames();
                for (ServiceName name : names) {
                    if (Services.JBOSS_DEPLOYMENT_UNIT.getSimpleName().equals(name.getParent().getSimpleName())) {
                        SearchResultItem item = createSearchResultItem(manifest, name);
                        if (item != null) {
                            result.add(item);
                        }
                    }
                }
            }
        }
        return result;
    }

}
