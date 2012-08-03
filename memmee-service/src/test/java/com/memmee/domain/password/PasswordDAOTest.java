package com.memmee.domain.password;


import base.AbstractMemmeeDAOTest;
import org.junit.After;
import org.junit.Before;
import org.skife.jdbi.v2.Handle;

public class PasswordDAOTest extends AbstractMemmeeDAOTest {

    @Before
    public void setUp() throws Exception {
        this.database = factory.build(mysqlConfig, "mysql");
        final Handle handle = database.open();
        try {
            handle.createCall("DROP TABLE IF EXISTS password").invoke();

            handle.createCall("CREATE TABLE `password` (\n" +
                    "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                    "  `value` varchar(1000) DEFAULT NULL,\n" +
                    "  `temp` tinyint(4) DEFAULT NULL,\n" +
                    "  PRIMARY KEY (`id`)\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1"
            ).invoke();

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
