package base;

import com.memmee.MemmeeResource;
import com.yammer.dropwizard.bundles.DBIExceptionsBundle;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.config.LoggingFactory;
import com.yammer.dropwizard.db.Database;
import com.yammer.dropwizard.db.DatabaseConfiguration;
import com.yammer.dropwizard.db.DatabaseFactory;
import com.yammer.dropwizard.testing.ResourceTest;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.skife.jdbi.v2.Handle;

import static org.mockito.Mockito.mock;

/**
 * Created with IntelliJ IDEA.
 * User: trevorewen
 * Date: 7/9/12
 * Time: 10:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class ResourceIntegrationTest extends ResourceTest {

    protected final DatabaseConfiguration mysqlConfig = new DatabaseConfiguration();

    {
        LoggingFactory.bootstrap();
        mysqlConfig.setUrl("jdbc:mysql://localhost:3306/memmeetest");
        mysqlConfig.setUser("memmeetest");      //PLEASE CREATE THIS USER - DO NOT SWITCH BACK TO commons
        mysqlConfig.setPassword("memmeetest");
        mysqlConfig.setDriverClass("com.mysql.jdbc.Driver");
    }

    protected final Environment environment = mock(Environment.class);
    protected final DatabaseFactory factory = new DatabaseFactory(environment);
    protected static Database database;
    protected static Handle handle;
    protected static DBIExceptionsBundle dbiExceptionsBundle = new DBIExceptionsBundle();


    @Override
    protected void setUpResources() throws Exception {
        dbiExceptionsBundle.initialize(environment);
        database = factory.build(mysqlConfig, "mysql");
        handle = database.open();

        try {
            handle.createCall("DROP TABLE IF EXISTS user").invoke();
            handle.createCall("DROP TABLE IF EXISTS memmee").invoke();
            handle.createCall("DROP TABLE IF EXISTS attachment").invoke();
            handle.createCall("DROP TABLE IF EXISTS theme").invoke();

            handle.createCall(
                    "CREATE TABLE `user` (\n" +
                            "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                            "  `firstName` varchar(1024) DEFAULT NULL,\n" +
                            "  `lastName` varchar(1024) DEFAULT NULL,\n" +
                            "  `email` varchar(4096) NOT NULL,\n" +
                            "  `password` varchar(4096) NOT NULL,\n" +
                            "  `apiKey` varchar(1024) DEFAULT NULL,\n" +
                            "  `apiDate` date DEFAULT NULL,\n" +
                            "  `creationDate` date NOT NULL,\n" +
                            "  PRIMARY KEY (`id`)\n" +
                            ") ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1"
            ).invoke();
            handle.createCall(
                    "CREATE TABLE `memmee` (\n" +
                            "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                            "  `userId` int(11) NOT NULL,\n" +
                            "  `attachmentId` int(11) DEFAULT NULL,\n" +
                            "  `lastUpdateDate` date NOT NULL,\n" +
                            "  `creationDate` date NOT NULL,\n" +
                            "  `displayDate` date NOT NULL,\n" +
                            "  `text` varchar(4096) DEFAULT NULL,\n" +
                            "  `shareKey` varchar(1024) DEFAULT NULL,\n" +
                            "  `themeId` int(11) DEFAULT NULL,\n" +
                            "  PRIMARY KEY (`id`)\n" +
                            ") ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4"
            ).invoke();
            handle.createCall(
                    "CREATE TABLE `attachment` (\n" +
                            "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                            "  `memmeeId` int(11) DEFAULT NULL,\n" +
                            "  `filePath` varchar(1024) DEFAULT NULL,\n" +
                            "  `thumbFilePath` varchar(1024) DEFAULT NULL,\n" +
                            "  `type` varchar(20) DEFAULT NULL,\n" +
                            "  PRIMARY KEY (`id`)\n" +
                            ") ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3"

            ).invoke();
            handle.createCall(
                    "CREATE TABLE `theme` (\n" +
                            "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                            "  `name` varchar(100) DEFAULT NULL,\n" +
                            "  `stylePath` varchar(1024) DEFAULT NULL,\n" +
                            "  PRIMARY KEY (`id`)\n" +
                            ") ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1"
            ).invoke();

        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        handle.close();
        handle = null;
        database.stop();
        database = null;
    }

}
