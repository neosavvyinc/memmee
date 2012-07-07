package com.memmee;

import com.memmee.user.dao.UserDAO;
import com.memmee.user.dto.User;
import com.memmee.util.MemmeeMailSender;
import com.yammer.dropwizard.testing.ResourceTest;
import org.junit.Test;

import java.util.Date;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created with IntelliJ IDEA.
 * User: waparrish
 * Date: 7/6/12
 * Time: 11:22 PM
 */
public class UserResourceTest extends ResourceTest {

    private static User user = new User();
    {
        user.setEmail("aparrish@neosavvy.com");
        user.setPassword("test");
        user.setApiDate(new Date());
        user.setCreationDate(new Date());
        user.setFirstName("Adam");
        user.setLastName("Parrish");
        user.setApiKey("testKey");
        user.setId(1L);
    }
    private UserDAO userDAO = mock(UserDAO.class);
    private MemmeeMailSender mailSender = mock(MemmeeMailSender.class);

    @Override
    protected void setUpResources() throws Exception {
        when(userDAO.getUser(anyLong())).thenReturn(user);
        addResource(new UserResource(userDAO, mailSender));
    }

    @Test
    public void testValidUser()
    {
        User testUser = new User();
        testUser.setEmail("aparrish@neosavvy.com");
        testUser.setPassword("12345678");
        try {
            User returnobject = client().resource("/memmeeuserrest/user").post(User.class, testUser);
        } catch ( Exception e )
        {
            e.toString();
        }

    }
}
