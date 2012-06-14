package com.memmee;

import org.skife.jdbi.v2.Handle;

import com.memmee.attachment.dao.TransactionalAttachmentDAO;
import com.memmee.memmees.dao.MemmeeDAO;
import com.memmee.memmees.dao.TransactionalMemmeeDAO;
import com.memmee.theme.dao.ThemeDAO;
import com.memmee.user.dao.TransactionalUserDAO;
import com.memmee.user.dao.UserDAO;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Environment;  
import com.yammer.dropwizard.db.Database;
import com.yammer.dropwizard.db.DatabaseFactory;

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
        final UserDAO userDao = db.onDemand(UserDAO.class);
        final TransactionalMemmeeDAO memmeeDao = db.onDemand(TransactionalMemmeeDAO.class);
        final ThemeDAO themeDao = db.onDemand(ThemeDAO.class);
        environment.addResource(new UserResource(userDao));
        environment.addResource(new MemmeeResource(userDao,memmeeDao));
    }
}
