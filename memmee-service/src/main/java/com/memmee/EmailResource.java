package com.memmee;

import com.memmee.domain.memmees.dao.TransactionalMemmeeDAO;
import com.memmee.domain.user.dao.TransactionalUserDAO;
import com.memmee.domain.user.dto.User;
import com.memmee.error.UserResourceException;
import com.sun.mail.imap.IMAPFolder;

import javax.mail.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Date;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: jmlaudate
 * Date: 2/21/13
 * Time: 9:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class EmailResource {

    private static TransactionalUserDAO userDao = null;
    private static TransactionalMemmeeDAO memmeeDao = null;

    public EmailResource (TransactionalUserDAO userDao, TransactionalMemmeeDAO memmeeDao) {
        super();
        this.userDao = userDao;
        this.memmeeDao = memmeeDao;

    }

    public static int checkForEmail() throws MessagingException, IOException {
        final String username = "memmeetest@gmail.com";
        final String password = "m3mm33T3st";
        final String imapFolder = "imap.googlemail.com";

        int rc = 0;

        IMAPFolder folder;
        Store store;
        String subject;
        Message[] messages;

        String from;
        String[] splitFrom;

        Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", "imaps");

        Session session = Session.getDefaultInstance(props, null);

        store = session.getStore("imaps");
        store.connect(imapFolder, username, password);
        folder = (IMAPFolder) store.getFolder("inbox");

        if(!folder.isOpen()) {
            folder.open(Folder.READ_WRITE);
            messages = folder.getMessages();

            System.out.print("No of Messages : " + folder.getMessageCount());
            System.out.println("\tNo of Unread Messages : " + folder.getUnreadMessageCount());

            for (int i=0; i < messages.length;i++) {
                System.out.println("*****************************************************************************");
                System.out.println("MESSAGE " + (i + 1) + ":");
                Message msg =  messages[i];


                // Set & Print the subject
                subject = msg.getSubject();
                System.out.println("Subject: " + subject);

                // Parse email address only from "from", Set, & then print
                from = msg.getFrom()[0].toString();
                splitFrom = from.split("<");
                from = splitFrom[1];
                splitFrom = from.split(">");
                from = splitFrom[0];
                System.out.println("From: " + from);
                User user = userDao.getUserByEmail(from);
                if (user != null) {
                    Long userId = user.getId();

                    // Set & Print date received
                    Date allDates = msg.getReceivedDate();
                    System.out.println("Date: " + allDates);

                    // Parse the actual email text from "getContent", then print
                    System.out.println(msg.getContentType());
                    if (msg.getContent() instanceof Multipart) {
                        try {
                            String mpMessage = "";

                            Multipart mp = (Multipart) msg.getContent();
                            for (int b = 0; b < 1; b++) {
                                Part p = mp.getBodyPart(b);
                                InputStream is = p.getInputStream();
                                if (!(is instanceof BufferedInputStream)) {
                                    is = new BufferedInputStream(is);
                                }
                                int c;
                                final StringWriter sw = new StringWriter();
                                while ((c = is.read()) != -1) {
                                    sw.write(c);
                                }

                                if (!sw.toString().contains("<div>")) {
                                    mpMessage = sw.toString();
                                    if (mpMessage.length() > 500) {
                                        mpMessage = mpMessage.substring(0, 499);
                                    }
                                    System.out.println("memmeeMessage = " + mpMessage);
                                }
                                Long insertMemmee = memmeeDao.insert(userId, mpMessage, allDates, allDates, allDates,
                                        null, null, 1L, null);
                                rc++;
                                System.out.println("Memmee inserted!  Success!  Now deleting the email");
                                 //mark the message as deleted, only if the insert was successful
                                 msg.setFlag(Flags.Flag.DELETED, true);
                                 //TODO send email stating SUCCESS!!!
                            }
                        }
                        catch (Exception ex) {
                            System.out.println("Exception arise at get Content");
                            ex.printStackTrace();
                        }
                    }
                }
                else {
                    //throw new UserResourceException("There is no user that exists with that email");
                    System.out.println("There is no user that exists with that email");
                    //TODO send an email and redirect to the sign up page... or change the email address
                    // on record
                }
                // TODO We may need to handle other content types, e.g. "text/plain" and "text/html"
                //   but Multipart is probably the most common instance

                // TODO Check for denial of service attack
                // TODO
            }
        }
        if (folder != null && folder.isOpen()) {
            folder.close(true);
        }
        if (store != null) {
            store.close();
        }
        return rc;
    }
}
