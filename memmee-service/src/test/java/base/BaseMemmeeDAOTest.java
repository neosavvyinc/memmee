package base;

import com.memmee.domain.inspiration.InspirationDAOTest;
import com.memmee.domain.user.UserDAOTest;
import org.junit.After;
import org.junit.Before;
import org.skife.jdbi.v2.Handle;

/**
 * Created with IntelliJ IDEA.
 * User: trevorewen
 * Date: 7/24/12
 * Time: 9:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class BaseMemmeeDAOTest extends AbstractMemmeeDAOTest {

    public static final String DROP_TABLE_STATEMENT = "DROP TABLE IF EXISTS memmee";
    public static final String TABLE_DEFINITION = "CREATE TABLE `memmee` (\n" +
            "`id` int(11) NOT NULL AUTO_INCREMENT,\n" +
            "`userId` int(11) NOT NULL,\n" +
            "`attachmentId` int(11) DEFAULT NULL,\n" +
            "`inspirationId` int(11) DEFAULT NULL,\n" +
            "`lastUpdateDate` datetime NOT NULL,\n" +
            "`creationDate` datetime NOT NULL,\n" +
            "`displayDate` datetime NOT NULL,\n" +
            "`text` varchar(4096) DEFAULT NULL,\n" +
            "`shareKey` varchar(1024) DEFAULT NULL,\n" +
            "`themeId` int(11) DEFAULT NULL,\n" +
            "PRIMARY KEY (`id`)\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1";

    @Before
    public void setUp() throws Exception {
        this.database = factory.build(mysqlConfig, "mysql");
        final Handle handle = database.open();
        try {

            handle.createCall(UserDAOTest.DROP_TABLE_STATEMENT).invoke();
            handle.createCall(BaseMemmeeDAOTest.DROP_TABLE_STATEMENT).invoke();
            handle.createCall("DROP TABLE IF EXISTS attachment").invoke();
            handle.createCall("DROP TABLE IF EXISTS theme").invoke();
            handle.createCall(InspirationDAOTest.DROP_TABLE_STATEMENT).invoke();

            handle.createCall(UserDAOTest.TABLE_DEFINITION).invoke();
            handle.createCall(BaseMemmeeDAOTest.TABLE_DEFINITION).invoke();
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
            handle.createCall(InspirationDAOTest.TABLE_DEFINITION).invoke();

        } catch (Exception e) {
            System.err.println(e);

        } finally {
            handle.close();
        }
    }

    @After
    public void tearDown() throws Exception {
        database.stop();
        this.database = null;
    }
}
