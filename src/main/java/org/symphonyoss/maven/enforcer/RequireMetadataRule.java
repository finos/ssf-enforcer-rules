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
 * This Rule fails if the metadata of the current project pom.xml doesn't exist; if <expectedStringValue> is specified,
 * it will also fail if values don't match.
 *
 */
public class RequireMetadataRule
    implements EnforcerRule
{
    /**
     * The name of the pom.xml metadata (XML) element
     */
    private String metadataName;

    /**
     * The expectedStringValue to enforce
     */
    private String expectedStringValue;

    private Log log;
    
    public void execute( EnforcerRuleHelper helper )
        throws EnforcerRuleException
    {
        log = helper.getLog();

        try
        {
            Object value = helper.evaluate( "${project."+this.metadataName+"}" );
            log.debug( "Retrieved metadata element '"+this.metadataName+"' with value: " + value );

            String error = validateValue(this.metadataName,this.expectedStringValue,value);
            if (error != null) {
                throw new EnforcerRuleException("RequireMetadataRule is failing with the following error: "+error);
            }
        } catch ( ExpressionEvaluationException e ) {
            throw new EnforcerRuleException( "RequireMetadataRule is unable to lookup an expression " + e.getLocalizedMessage(), e );
        }
    }

    private String validateValue(String metadataName, String expectedStringValue, Object currentValue) {
        if (currentValue == null) {
            return "pom.xml metadata '"+metadataName+"' is null";
        }
        if (currentValue.getClass().isArray()) {
            Object[] list = (Object[]) currentValue;
            if (list.length == 0) {
                return "pom.xml metadata '" + metadataName + "' have no items specified";
            }
        }
        if (expectedStringValue != null) {
            if (!currentValue.equals(expectedStringValue)) {
                    return "pom.xml metadata '" + metadataName + "' value '" + currentValue + "' does not match with expected value '" + expectedStringValue + "'";
            }
        } else {
            log.debug("RequireMetadataRule won't match metadata "+this.metadataName+" value, since <expectedStringValue> is not set");
        }
        return null;
    }

    public boolean isCacheable() {
        return false;
    }

    // Not used if cachable=false
    public String getCacheId() {
        return ""+this.metadataName+this.expectedStringValue;
    }

    // Not used if cachable=false
    public boolean isResultValid( EnforcerRule arg0 ) {
        return false;
    }
}
