package base;

import com.memmee.domain.attachment.AttachmentDAOTest;
import com.memmee.domain.inspiration.InspirationDAOTest;
import com.memmee.domain.user.UserDAOTest;
import com.memmee.theme.ThemeDAOTest;
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
    public static final String TABLE_DEFINITION = "CREATE TABLE IF NOT EXISTS `memmee` (\n" +
            "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
            "  `userId` int(11) NOT NULL,\n" +
            "  `attachmentId` int(11) DEFAULT NULL,\n" +
            "  `inspirationId` int(11) DEFAULT NULL,\n" +
            "  `lastUpdateDate` datetime NOT NULL,\n" +
            "  `creationDate` datetime NOT NULL,\n" +
            "  `displayDate` datetime NOT NULL,\n" +
            "  `text` varchar(4096) DEFAULT NULL,\n" +
            "  `shareKey` varchar(1024) DEFAULT NULL,\n" +
            "  `shortenedUrl` varchar(200) DEFAULT NULL,\n" +
            "  `themeId` int(11) DEFAULT NULL,\n" +
            "  PRIMARY KEY (`id`)\n" +
            ") ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2";

    @Before
    public void setUp() throws Exception {
        this.database = factory.build(mysqlConfig, "mysql");
        final Handle handle = database.open();
        try {

            handle.createCall(UserDAOTest.DROP_TABLE_STATEMENT).invoke();
            handle.createCall(BaseMemmeeDAOTest.DROP_TABLE_STATEMENT).invoke();
            handle.createCall(AttachmentDAOTest.DROP_TABLE_STATEMENT).invoke();
            handle.createCall(ThemeDAOTest.DROP_TABLE_STATEMENT).invoke();
            handle.createCall(InspirationDAOTest.DROP_TABLE_STATEMENT).invoke();

            handle.createCall(UserDAOTest.TABLE_DEFINITION).invoke();
            handle.createCall(BaseMemmeeDAOTest.TABLE_DEFINITION).invoke();
            handle.createCall(AttachmentDAOTest.TABLE_DEFINITION).invoke();
            handle.createCall(ThemeDAOTest.TABLE_DEFINITION).invoke();
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
