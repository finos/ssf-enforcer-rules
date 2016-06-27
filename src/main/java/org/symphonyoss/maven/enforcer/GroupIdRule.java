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
import org.apache.maven.plugin.logging.Log;
import org.codehaus.plexus.component.configurator.expression.ExpressionEvaluationException;

/**
 * @author <a href="mailto:maoo@symphony.foundation">Maurizio Pillitu</a>
 * This Rule fails if the groupId of the current project doesn't match with groupIdPrefix (or groupIdPrefix.)
 */
public class GroupIdRule
    implements EnforcerRule
{
    /**
     * The groupId prefix to enforce
     */
    private String groupIdPrefix;

    public void execute( EnforcerRuleHelper helper )
        throws EnforcerRuleException
    {
        Log log = helper.getLog();

        try
        {
            String groupId = (String) helper.evaluate( "${project.groupId}" );
            log.debug( "Retrieved groupId: " + groupId );

            if (!groupId.equals(this.groupIdPrefix) && !groupId.startsWith(this.groupIdPrefix+".")) {
              throw new EnforcerRuleException( "Failing because "+groupId+" should start with "+groupIdPrefix);
            }
        } catch ( ExpressionEvaluationException e ) {
            throw new EnforcerRuleException( "Unable to lookup an expression " + e.getLocalizedMessage(), e );
        }
    }

    public boolean isCacheable() {
        return false;
    }

    // Not used if cachable=false
    public String getCacheId() {
        return ""+this.groupIdPrefix;
    }

    // Not used if cachable=false
    public boolean isResultValid( EnforcerRule arg0 ) {
        return false;
    }
}
