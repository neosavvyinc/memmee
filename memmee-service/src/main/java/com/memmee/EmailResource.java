package com.memmee;

import com.memmee.domain.attachment.dao.TransactionalAttachmentDAO;
import com.memmee.domain.memmees.dao.TransactionalMemmeeDAO;
import com.memmee.domain.user.dao.TransactionalUserDAO;
import com.memmee.domain.user.dto.User;
import com.memmee.util.MemmeeMailSender;
import com.memmee.util.OsUtil;

import com.sun.mail.imap.IMAPFolder;
import com.yammer.dropwizard.logging.Log;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;

import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.io.*;
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
    public static String username = "";
    public static String password = "";
    public static String server = "";

    private static TransactionalUserDAO userDao = null;
    private static TransactionalMemmeeDAO memmeeDao = null;
    private static TransactionalAttachmentDAO attachmentDao = null;
    private static final Log LOG = Log.forClass(MemmeeResource.class);
    private static MemmeeMailSender memmeeMailSender;
    private MemmeeUrlConfiguration memmeeUrlConfiguration;
    private MemmeeEmailConfiguration memmeeEmailConfiguration;

    private static boolean textIsHtml = false;

    public EmailResource(
            TransactionalUserDAO userDAO,
            TransactionalMemmeeDAO memmeeDAO,
            TransactionalAttachmentDAO attachmentDAO,
            MemmeeMailSender memmeeMail,
            MemmeeUrlConfiguration memmeeUrlConfiguration,
            MemmeeEmailConfiguration memmeeEmailConfiguration
            ) {
        super();
        this.userDao = userDAO;
        this.memmeeDao = memmeeDAO;
        this.attachmentDao = attachmentDAO;
        this.memmeeMailSender = memmeeMail;
        this.memmeeUrlConfiguration = memmeeUrlConfiguration;
        this.memmeeEmailConfiguration = memmeeEmailConfiguration;

        username = memmeeEmailConfiguration.getActiveUsername();
        password = memmeeEmailConfiguration.getActivePassword();
        server = memmeeEmailConfiguration.getActiveServer();

        this.memmeeMailSender.setUrlConfiguration(memmeeUrlConfiguration);
    }

    public static int checkForEmail(String mailbox) throws MessagingException, IOException {


        int rc = 0;

        IMAPFolder folder;
        Store store;
        String subject;
        Message[] messages;

        String from;
        String[] splitFrom;
        String[] splitText;

        Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", "imaps");

        Session session = Session.getDefaultInstance(props, null);
        LOG.info("username = " + username);
        //LOG.info("password = " + password);
        LOG.info("server = " + server);

        store = session.getStore("imaps");
        store.connect(server, username, password);
        folder = (IMAPFolder) store.getFolder(mailbox);

        User user = null;

        if(!folder.isOpen()) {
            folder.open(Folder.READ_WRITE);
            messages = folder.getMessages();

            LOG.info("No of Messages : " + folder.getMessageCount());
            LOG.info("\tNo of Unread Messages : " + folder.getUnreadMessageCount());

            for (int i=0; i < messages.length;i++) {
                LOG.info("*****************************************************************************");
                LOG.info("MESSAGE " + (i + 1) + ":");
                Message msg =  messages[i];


                // Set & Print the subject
                subject = msg.getSubject();
                LOG.info("Subject: " + subject);

                // Parse email address only from "from", Set, & then print
                from = msg.getFrom()[0].toString();
                LOG.info("from = " + from);

                splitFrom = from.split("<");
                from = splitFrom[1];
                splitFrom = from.split(">");
                from = splitFrom[0];

                if (from.endsWith("@txt.voice.google.com")) {
                    LOG.info("from = " + from);
                    splitText = from.split("\\.");
                    from = splitText[1];
                    from = from.replaceAll("\\D", "");
                    LOG.info("from after split and regexp: " + from);

                    user = userDao.getUserByPhone(from);
                    LOG.info("from = " + from);
                }
                else {
                    user = userDao.getUserByEmail(from);
                }

                LOG.info("From: " + from);
                LOG.info("Content Type = " + msg.getContentType());
                LOG.info("Content = " + msg.getContent());
                if (user != null) {
                    Long userId = user.getId();

                    // Set & Print date received
                    Date allDates = msg.getReceivedDate();
                    LOG.info("Date: " + allDates);

                    // Parse the actual email text from "getContent", then print
                    LOG.info(msg.getContentType());
                    if (msg.getContent() instanceof Multipart) {
                        try {
                            String mpMessage = "";
                            String baseFileDirectory = "";

                            InputStream is;
                            Long attachmentId = Long.parseLong("-1");
                            Multipart mp = (Multipart) msg.getContent();
                            boolean gotAttachment = false;

                            if (msg.isMimeType("MULTIPART/MIXED")) {

                                for( int j = 0; j < mp.getCount(); j++ ) {
                                MimeBodyPart part = (MimeBodyPart) mp.getBodyPart(j);
                                is = part.getInputStream();

                                    String disposition = part.getDisposition();
                                    LOG.info("disposition " + j + " = " + disposition);
                                    if (disposition != null
                                            && ( disposition.equalsIgnoreCase( Part.ATTACHMENT )
                                            || ( disposition.equalsIgnoreCase( Part.INLINE )) ) && !gotAttachment ) {
                                        // do something with part
                                        LOG.info("Got the part " + part.getFileName());

                                        if (part.getFileName().endsWith(".jpg") || part.getFileName().endsWith(".jpeg")
                                                || part.getFileName().endsWith(".png") || part.getFileName().endsWith(".JPG")
                                                || part.getFileName().endsWith(".PNG") || part.getFileName().endsWith(".JPEG")) {
                                            if (OsUtil.isWindows()) {
                                                baseFileDirectory = "c://memmee/temp/" + user.getId() + "/";
                                            } else if (OsUtil.isMac()) {
                                                baseFileDirectory = "/memmee/" + user.getId() + "/";
                                            } else if (OsUtil.isUnix()) {
                                                baseFileDirectory = "/memmee/" + user.getId() + "/";
                                            }
                                            LOG.info("baseFileDirectory = " + baseFileDirectory);
                                            ensureParentDirectory(baseFileDirectory);
                                            String uploadedFileLocationToWrite = baseFileDirectory + part.getFileName().toLowerCase();
                                            writeToFile(is, uploadedFileLocationToWrite);
                                            String uploadedThumbFileLocation = writeThumbnailImage(uploadedFileLocationToWrite);
                                            attachmentId = attachmentDao.insert(uploadedFileLocationToWrite, uploadedThumbFileLocation, "Image");
                                            gotAttachment = true;
                                        }

                                    }
                                    else {
                                        if (mpMessage.length() == 0) {
                                            mpMessage = getText(part);
                                            if (mpMessage.length() > 500) {
                                                mpMessage = mpMessage.substring(0, 499);
                                            }
                                        }

                                        LOG.info("finalMessage = " + mpMessage);
                                   }
                                }
                            }
                            else if (msg.isMimeType("MULTIPART/ALTERNATIVE")) {
                                for (int b = 0; b < 1; b++) {
                                    Part p = mp.getBodyPart(b);
                                    is = p.getInputStream();
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
                                        LOG.info("memmeeMessage = " + mpMessage);
                                    }
                                }
                            }


                            if (attachmentId != Long.parseLong("-1")) {
                                Long insertMemmee = memmeeDao.insert(userId, mpMessage, allDates, allDates, allDates,
                                        null, attachmentId, 1L, null);
                            }
                            else {
                                Long insertMemmee = memmeeDao.insert(userId, mpMessage, allDates, allDates, allDates,
                                        null, null, 1L, null);
                            }
                            rc++;
                            //mark the message as deleted, only if the insert was successful
                            if (mailbox.equalsIgnoreCase("inbox")) {
                                LOG.info("Memmee inserted!  Success!  Now deleting the email");
                                msg.setFlag(Flags.Flag.DELETED, true);
                            }
                            else {
                                LOG.info("Memmee inserted!  Success!");
                            }

                        }
                        catch (Exception ex) {
                            LOG.error(ex, "Exception arise at get Content");
                        }
                    }
                    else {
                        String mpMessage = msg.getContent().toString();
                        LOG.info("mpMessage = " + mpMessage);
                        Long insertMemmee = memmeeDao.insert(userId, mpMessage, allDates, allDates, allDates,
                                null, null, 1L, null);
                        rc++;
                        //mark the message as deleted, only if the insert was successful
                        if (mailbox.equalsIgnoreCase("inbox")) {
                            LOG.info("Memmee inserted!  Success!  Now deleting the text");
                            msg.setFlag(Flags.Flag.DELETED, true);
                        }
                        else {
                            LOG.info("Memmee inserted!  Success!");
                        }

                    }
                }
                else {
                    LOG.info("There is no user that exists with that email " + from);
                    memmeeMailSender.sendInvalidEmailInvitation(from);
                    if (mailbox.equalsIgnoreCase("inbox")) {
                        msg.setFlag(Flags.Flag.DELETED, true);
                    }
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

    private static void ensureParentDirectory(String parentDirectory) {
        File parentDir;
        if (parentDirectory != null) {
            parentDir = new File(parentDirectory);
            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }
        } else {
            throw new WebApplicationException(Response.Status.PRECONDITION_FAILED);
        }
    }

    // save uploaded file to new location
    private static void writeToFile(InputStream uploadedInputStream,
                            String uploadedFileLocation) {
        try {
            OutputStream out;
            int read = 0;
            byte[] bytes = new byte[1024];

            out = new FileOutputStream(new File(uploadedFileLocation));
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String writeThumbnailImage(String fileName) {
        String imPath = "/opt/local/bin:/usr/bin:/usr/local/bin";
        ConvertCmd
                cmd = new ConvertCmd();
        cmd.setSearchPath(imPath);

        // create the operation, add images and operators/options
        IMOperation op = new IMOperation();
        String sourceImage = fileName;
        String destinationImage;
        if (sourceImage.toLowerCase().indexOf(".jpg") > -1) {
            destinationImage = sourceImage.replaceFirst(".jpg", "-thumb.jpg");
        } else if (sourceImage.toLowerCase().indexOf(".jpeg") > -1) {
            destinationImage = sourceImage.replaceFirst(".png", "-thumb.jpeg");
        } else if (sourceImage.toLowerCase().indexOf(".png") > -1) {
            destinationImage = sourceImage.replaceFirst(".png", "-thumb.png");
        } else {
            WebApplicationException unsupportedMediaException = new WebApplicationException(Response.Status.UNSUPPORTED_MEDIA_TYPE);
            LOG.error("Attempting to save a file named " + sourceImage.toLowerCase());
            throw unsupportedMediaException;
        }


        op.addImage(sourceImage);
        op.resize(200, 300);
        op.addImage(destinationImage);

        try {
            cmd.run(op);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IM4JavaException e) {
            e.printStackTrace();
        }

        return destinationImage;
    }

    /**
     * Return the primary text content of the message.
     */
    private static String getText(Part p) throws
            MessagingException, IOException {
        if (p.isMimeType("text/*")) {
            String s = (String)p.getContent();
            textIsHtml = p.isMimeType("text/html");
            return s;
        }

        if (p.isMimeType("multipart/alternative")) {
            // prefer html text over plain text
            Multipart mp = (Multipart)p.getContent();
            String text = null;
            for (int i = 0; i < mp.getCount(); i++) {
                Part bp = mp.getBodyPart(i);
                if (bp.isMimeType("text/plain")) {
                    if (text == null)
                        text = getText(bp);
                    continue;
                } else if (bp.isMimeType("text/html")) {
                    String s = getText(bp);
                    if (s != null)
                        return s;
                } else {
                    return getText(bp);
                }
            }
            return text;
        } else if (p.isMimeType("multipart/*")) {
            Multipart mp = (Multipart)p.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                String s = getText(mp.getBodyPart(i));
                if (s != null)
                    return s;
            }
        }

        return null;
    }


}
