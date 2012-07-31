package com.memmee;

import com.memmee.auth.MemmeeAuthenticator;
import com.memmee.domain.inspirations.dao.TransactionalInspirationDAO;
import com.memmee.domain.user.dto.User;
import com.memmee.util.MemmeeMailSenderImpl;
import com.yammer.dropwizard.auth.basic.BasicAuthProvider;
import com.yammer.dropwizard.bundles.DBIExceptionsBundle;

import com.memmee.domain.attachment.dao.TransactionalAttachmentDAO;
import com.memmee.domain.memmees.dao.TransactionalMemmeeDAO;
import com.memmee.domain.user.dao.UserDAO;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.db.Database;
import com.yammer.dropwizard.db.DatabaseFactory;

import java.util.TimeZone;

public class MemmeeService extends Service<MemmeeConfiguration> {

    public static void main(String[] args) throws Exception {
        new MemmeeService().run(args);
    }

    public MemmeeService() {
        super("user");
    }


    @Override
    protected void initialize(MemmeeConfiguration userConfiguration, Environment environment) throws Exception {

        //Monitors Database Exceptions From the DAOS
        addBundle(new DBIExceptionsBundle());

        //Set Timezone
        userConfiguration.getLoggingConfiguration().getConsoleConfiguration().setTimeZone(TimeZone.getDefault());

        final DatabaseFactory factory = new DatabaseFactory(environment);
        final Database db = factory.build(userConfiguration.getDatabase(), "mysql");
        final UserDAO userDao = db.onDemand(UserDAO.class);
        final TransactionalMemmeeDAO memmeeDao = db.onDemand(TransactionalMemmeeDAO.class);
        final TransactionalAttachmentDAO attachmentDao = db.onDemand(TransactionalAttachmentDAO.class);
        final TransactionalInspirationDAO inspirationDao = db.onDemand(TransactionalInspirationDAO.class);

        environment.addProvider(new BasicAuthProvider<User>(new MemmeeAuthenticator(userDao),
                "MEMMEE AUTHENTICATION"));
        environment.addResource(new UserResource(userDao, new MemmeeMailSenderImpl()));
        environment.addResource(new MemmeeResource(userDao, memmeeDao, attachmentDao, inspirationDao));
        environment.addResource(new InspirationResource(userDao, inspirationDao));
    }
}
