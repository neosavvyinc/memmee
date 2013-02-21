package com.memmee;

import com.yammer.dropwizard.config.Configuration;
import com.yammer.dropwizard.db.DatabaseConfiguration;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class MemmeeConfiguration extends Configuration {

    @Valid
    @NotNull
    @JsonProperty
    private DatabaseConfiguration database = new DatabaseConfiguration();

    @Valid
    @NotNull
    @JsonProperty
    private MemmeeUrlConfiguration memmeeUrlConfiguration = new MemmeeUrlConfiguration();

    public DatabaseConfiguration getDatabase() {
        return database;
    }

    public MemmeeUrlConfiguration getMemmeeUrlConfiguration() {
        return memmeeUrlConfiguration;
    }

    public void setMemmeeUrlConfiguration(MemmeeUrlConfiguration memmeeUrlConfiguration) {
        this.memmeeUrlConfiguration = memmeeUrlConfiguration;
    }


    @Valid
    @JsonProperty
    private Map<String,String> quartzSettings = new HashMap<String, String>();

    public Properties getSchedulerFactoryProperties(){
        Properties sfProps = new Properties();
        // Fixed Quartz settings. They could easily be added to the YAML config file
        sfProps.setProperty("org.quartz.plugin.jobInitializer.class", "org.quartz.plugins.xml.XMLSchedulingDataProcessorPlugin");
        sfProps.setProperty("org.quartz.plugin.jobInitializer.failOnFileNotFound", "true");
        // Job XML files can be changed without restarting the service by being check at regular intervals
        sfProps.setProperty("org.quartz.plugin.jobInitializer.scanInterval", "0");
        sfProps.setProperty("org.quartz.plugin.jobInitializer.wrapInUserTransaction", "false");
        // Quartz checks for updates. This should be turned off for production systems.
        sfProps.setProperty("org.quartz.scheduler.skipUpdateCheck","true");

        // Quartz settings configured in YML file
        sfProps.setProperty("org.quartz.scheduler.instanceName", quartzSettings.get("instanceName"));
        sfProps.setProperty("org.quartz.threadPool.class", quartzSettings.get("threadPoolClass"));
        sfProps.setProperty("org.quartz.threadPool.threadCount", quartzSettings.get("threadCount"));
        sfProps.setProperty("org.quartz.threadPool.threadPriority", quartzSettings.get("threadPriority"));
        sfProps.setProperty("org.quartz.jobStore.class", quartzSettings.get("jobStoreClass"));
        sfProps.setProperty("org.quartz.plugin.jobInitializer.fileNames", quartzSettings.get("jobFiles"));

        return sfProps;
    }
}
