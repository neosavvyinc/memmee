package base;

import com.memmee.MemmeeResource;
import com.memmee.domain.attachment.AttachmentDAOTest;
import com.memmee.domain.inspiration.InspirationDAOTest;
import com.memmee.domain.inspirationcategories.InspirationCategoryDAOTest;
import com.memmee.domain.password.PasswordDAOTest;
import com.memmee.domain.user.UserDAOTest;
import com.memmee.theme.ThemeDAOTest;
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
import static org.mockito.Mockito.when;

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
            handle.createCall(UserDAOTest.DROP_TABLE_STATEMENT).invoke();
            handle.createCall(PasswordDAOTest.DROP_TABLE_STATEMENT).invoke();
            handle.createCall(BaseMemmeeDAOTest.DROP_TABLE_STATEMENT).invoke();
            handle.createCall(AttachmentDAOTest.DROP_TABLE_STATEMENT).invoke();
            handle.createCall(ThemeDAOTest.DROP_TABLE_STATEMENT).invoke();
            handle.createCall(InspirationDAOTest.DROP_TABLE_STATEMENT).invoke();
            handle.createCall(InspirationCategoryDAOTest.DROP_TABLE_STATEMENT).invoke();

            handle.createCall(UserDAOTest.TABLE_DEFINITION).invoke();
            handle.createCall(PasswordDAOTest.TABLE_DEFINITION).invoke();
            handle.createCall(BaseMemmeeDAOTest.TABLE_DEFINITION).invoke();
            handle.createCall(AttachmentDAOTest.TABLE_DEFINITION).invoke();
            handle.createCall(ThemeDAOTest.TABLE_DEFINITION).invoke();
            handle.createCall(InspirationDAOTest.TABLE_DEFINITION).invoke();
            handle.createCall(InspirationCategoryDAOTest.TABLE_DEFINITION).invoke();

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
