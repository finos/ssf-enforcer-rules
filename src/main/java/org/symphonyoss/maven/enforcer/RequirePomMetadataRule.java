/*
 *
 * Copyright 2016 The Symphony Software Foundation
 *
 * Licensed to The Symphony Software Foundation (SSF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.symphonyoss.maven.enforcer;

import org.apache.maven.enforcer.rule.api.EnforcerRule;
import org.apache.maven.enforcer.rule.api.EnforcerRuleException;
import org.apache.maven.enforcer.rule.api.EnforcerRuleHelper;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.plugin.logging.Log;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author <a href="mailto:maoo@symphony.foundation">Maurizio Pillitu</a>
 * This Rule fails if either &lt;url&gt;, &lt;description&gt;, &lt;scm&gt;, &lt;developers&gt; or &lt;licenses&gt; is not set in the root pom.xml
 *
 */
public class RequirePomMetadataRule
    implements EnforcerRule
{

    private Log log;
    
    public void execute( EnforcerRuleHelper helper )
        throws EnforcerRuleException
    {
        log = helper.getLog();

        try
        {
            String error = validatePom();
            if (error != null) {
                throw new EnforcerRuleException("RequirePomMetadataRule is failing with the following error: " + error);
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException("Cannot find root parent pom.xml", e );
        }
    }

    private Model pomToModel(String pathToPom) throws IOException, XmlPullParserException {
        BufferedReader in = new BufferedReader(new FileReader(pathToPom));
        MavenXpp3Reader reader = new MavenXpp3Reader();
        Model model = reader.read(in);
        return model;
    }

    private String validatePom() throws IOException, XmlPullParserException {
        Model pomModel = pomToModel("./pom.xml");
        if (pomModel.getDescription() == null || pomModel.getDescription().isEmpty()) {
            return "pom.xml is missing <description> element  or it's empty";
        }
        if (pomModel.getUrl() == null || pomModel.getUrl().isEmpty()) {
            return "pom.xml is missing <url> element or it's empty";
        }

        if (pomModel.getScm() == null) {
            return "pom.xml is missing <scm> element";
        }
        if (pomModel.getLicenses().size() == 0) {
            return "pom.xml is missing <licenses> element";
        }
        if (pomModel.getDevelopers().size() == 0) {
            return "pom.xml is missing <developers> element";
        }
        return null;
    }

    public boolean isCacheable() {
        return false;
    }

    // Not used if cachable=false
    public String getCacheId() {
        return ""+this.getClass().hashCode();
    }

    // Not used if cachable=false
    public boolean isResultValid( EnforcerRule arg0 ) {
        return false;
    }
}
