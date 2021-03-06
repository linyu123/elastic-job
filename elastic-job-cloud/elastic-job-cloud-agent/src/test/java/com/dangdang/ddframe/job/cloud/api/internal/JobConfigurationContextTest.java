/*
 * Copyright 1999-2015 dangdang.com.
 * <p>
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
 * </p>
 */

package com.dangdang.ddframe.job.cloud.api.internal;

import com.dangdang.ddframe.job.api.exception.JobExecutionEnvironmentException;
import com.dangdang.ddframe.job.api.type.JobType;
import com.dangdang.ddframe.job.api.type.dataflow.api.DataflowJobConfiguration;
import com.dangdang.ddframe.job.api.type.script.api.ScriptJobConfiguration;
import com.dangdang.ddframe.job.api.type.simple.api.SimpleJobConfiguration;
import com.dangdang.ddframe.job.cloud.api.fixture.TestJob;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertTrue;

public class JobConfigurationContextTest {
    
    @Test
    public void assertSimpleJobConfigurationContext() throws JobExecutionEnvironmentException {
        assertTrue(new JobConfigurationContext(buildJobConfigurationContextMap(JobType.SIMPLE)).getTypeConfig() instanceof SimpleJobConfiguration); 
    }
    
    @Test
    public void assertDataflowJobConfigurationContext() throws JobExecutionEnvironmentException {
        assertTrue(new JobConfigurationContext(buildJobConfigurationContextMap(JobType.DATAFLOW)).getTypeConfig() instanceof DataflowJobConfiguration);
    }
    
    @Test
    public void assertScriptJobConfigurationContext() throws JobExecutionEnvironmentException {
        assertTrue(new JobConfigurationContext(buildJobConfigurationContextMap(JobType.SCRIPT)).getTypeConfig() instanceof ScriptJobConfiguration);
    }
    
    private Map<String, String> buildJobConfigurationContextMap(final JobType jobType) {
        Map<String, String> result = new HashMap<>(6, 1);
        result.put("jobName", "configuration_map_job");
        result.put("jobClass", TestJob.class.getCanonicalName());
        result.put("jobType", jobType.name());
        if (jobType == JobType.DATAFLOW) {
            result.put("dataflowType", DataflowJobConfiguration.DataflowType.SEQUENCE.name());
            result.put("streamingProcess", Boolean.TRUE.toString());
            result.put("concurrentDataProcessThreadCount", "8");
        } else if (jobType == JobType.SCRIPT) {
            result.put("scriptCommandLine", "echo test");
        }
        return result;
    }
}
