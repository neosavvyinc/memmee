package com.memmee;

import com.memmee.user.dao.MemmeeUserDAO;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.db.Database;
import com.yammer.dropwizard.db.DatabaseFactory;

/**
 * Created with IntelliJ IDEA.
 * User: waparrish
 * Date: 5/2/12
 * Time: 11:49 AM
 * To change this template use File | Settings | File Templates.
 */

//test from home 3
public class MemmeeService extends Service<MemmeeConfiguration> {

    public static void main(String[] args) throws Exception
    {
        new MemmeeService().run(args);
    }

    private MemmeeService()
    {
        super("user");
    }


    @Override
    protected void initialize(MemmeeConfiguration userConfiguration, Environment environment) throws Exception {

        final DatabaseFactory factory = new DatabaseFactory(environment);
        final Database db = factory.build(userConfiguration.getDatabase(), "mysql");
        final MemmeeUserDAO dao = db.onDemand(MemmeeUserDAO.class);
        environment.addResource(new MemmeeResource(dao));
    }
}
