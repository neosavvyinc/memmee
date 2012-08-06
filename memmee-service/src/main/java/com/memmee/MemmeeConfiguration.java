package com.memmee;

import com.yammer.dropwizard.config.Configuration;
import com.yammer.dropwizard.db.DatabaseConfiguration;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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

}
