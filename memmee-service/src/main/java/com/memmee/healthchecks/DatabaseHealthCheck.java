package com.memmee.healthchecks;

import com.yammer.dropwizard.db.Database;
import com.yammer.metrics.core.HealthCheck;

/**
 * Created with IntelliJ IDEA.
 * User: waparrish
 * Date: 10/30/12
 * Time: 4:51 PM
 */
public class DatabaseHealthCheck extends HealthCheck {

    private final Database database;

    public DatabaseHealthCheck(Database database) {
        super("usercount");
        this.database = database;
    }

    @Override
    protected Result check() throws Exception {
        return Result.healthy();
    }
}
