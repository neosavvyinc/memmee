package com.memmee.util;

import com.cribbstechnologies.clients.mandrill.exception.RequestFailedException;
import com.cribbstechnologies.clients.mandrill.model.MandrillHtmlMessage;
import com.cribbstechnologies.clients.mandrill.model.MandrillMessageRequest;
import com.cribbstechnologies.clients.mandrill.model.MandrillRecipient;
import com.cribbstechnologies.clients.mandrill.model.response.message.SendMessageResponse;
import com.cribbstechnologies.clients.mandrill.request.MandrillMessagesRequest;
import com.cribbstechnologies.clients.mandrill.request.MandrillRESTRequest;
import com.cribbstechnologies.clients.mandrill.util.MandrillConfiguration;
import com.memmee.MemmeeUrlConfiguration;
import com.memmee.domain.user.dto.User;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class MemmeeMailSenderImpl implements MemmeeMailSender {

    private static MandrillRESTRequest request = new MandrillRESTRequest();
    private static MandrillConfiguration config = new MandrillConfiguration();
    private static MandrillMessagesRequest messagesRequest = new MandrillMessagesRequest();
    private static HttpClient client;
    private static ObjectMapper mapper = new ObjectMapper();
    private MemmeeUrlConfiguration memmeeUrlConfiguration;

    @Override
    public void sendConfirmationEmail(User user) {
        loadMandrill();

        MandrillMessageRequest mmr = new MandrillMessageRequest();
        MandrillHtmlMessage message = new MandrillHtmlMessage();
        Map<String, String> headers = new HashMap<String, String>();
        message.setFrom_email(memmeeUrlConfiguration.getActiveEmailAddress());
        message.setFrom_name("memmee");
        message.setHeaders(headers);
        message.setHtml("<html><body>we're so excited for you to join memmee!<br><br>you should receive a welcome email within 24 hours. for help, questions, or to say hi, email us at <a href=\"mailto:hello@memmee.com\">hello@memmee.com</a>.</html></body>");
        message.setSubject("welcome to memmee!");
        MandrillRecipient[] recipients = new MandrillRecipient[]{new MandrillRecipient("new memmee user!", user.getEmail())};
        message.setTo(recipients);
        message.setTrack_clicks(true);
        message.setTrack_opens(true);
        String[] tags = new String[]{"newUserEmail"};
        message.setTags(tags);
        mmr.setMessage(message);

        try {
            SendMessageResponse response = messagesRequest.sendMessage(mmr);
        } catch (RequestFailedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendForgotPasswordEmail(User user, String temporaryPassword) {
        loadMandrill();

        MandrillMessageRequest mmr = new MandrillMessageRequest();
        MandrillHtmlMessage message = new MandrillHtmlMessage();
        Map<String, String> headers = new HashMap<String, String>();
        message.setFrom_email(memmeeUrlConfiguration.getActiveEmailAddress());
        message.setFrom_name("memmee");
        message.setHeaders(headers);

        String formattedText = String.format("please login at <a href='http://" + memmeeUrlConfiguration.getActiveUrl() +
                "/#'>memmee</a> using this temporary password and update your " +
                "profile with a new password. thank you for being part of memmee. temporary password: %s",
                temporaryPassword);

        message.setHtml(formattedText);

        message.setSubject("memmee - forgotten password");
        MandrillRecipient[] recipients = new MandrillRecipient[]{new MandrillRecipient("New Memmee User!", user.getEmail())};
        message.setTo(recipients);
        message.setTrack_clicks(true);
        message.setTrack_opens(true);
        String[] tags = new String[]{"forgotPasswordEmail"};
        message.setTags(tags);
        mmr.setMessage(message);

        try {
            SendMessageResponse response = messagesRequest.sendMessage(mmr);
        } catch (RequestFailedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendInvalidEmailInvitation(String email) {
        loadMandrill();

        MandrillMessageRequest mmr = new MandrillMessageRequest();
        MandrillHtmlMessage message = new MandrillHtmlMessage();
        Map<String, String> headers = new HashMap<String, String>();
        message.setFrom_email(memmeeUrlConfiguration.getActiveEmailAddress());
        message.setFrom_name("memmee");
        message.setHeaders(headers);

        message.setHtml("<html><body>You know, we got an email from you, but we don't have your email address on record<br>" +
                "<br>is there another email associated with your memmee account?  Do you want to create an account?<br>" +
                "<br>Just let us know... we'll be glad to help");
        message.setSubject("Oops! memmee doesn't know who you are!  Help us out?");

        MandrillRecipient[] recipients = new MandrillRecipient[]{new MandrillRecipient("new memmee user!", email)};
        message.setTo(recipients);
        message.setTrack_clicks(true);
        message.setTrack_opens(true);
        String[] tags = new String[]{"invalidEmailInvitation"};
        message.setTags(tags);
        mmr.setMessage(message);

        try {
            SendMessageResponse response = messagesRequest.sendMessage(mmr);
        } catch (RequestFailedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void sendMemmeeReceivedConfirmationMail(User user) {
        loadMandrill();

        MandrillMessageRequest mmr = new MandrillMessageRequest();
        MandrillHtmlMessage message = new MandrillHtmlMessage();
        Map<String, String> headers = new HashMap<String, String>();
        message.setFrom_email(memmeeUrlConfiguration.getActiveEmailAddress());
        message.setFrom_name("memmee");
        message.setHeaders(headers);

        message.setHtml("<html><body>your memmee on the go has been received, it's safe with us. you'll see it next time you login to memmee. email <a href=\"mailto:hello@memmee.com\">hello@memmee.com</a> for problems.</body></html>");
        message.setSubject("we totally got your memmee moment!");

        MandrillRecipient[] recipients = new MandrillRecipient[]{new MandrillRecipient("memmee on the go!", user.getEmail())};
        message.setTo(recipients);
        message.setTrack_clicks(true);
        message.setTrack_opens(true);
        String[] tags = new String[]{"memmeeOnTheGo"};
        message.setTags(tags);
        mmr.setMessage(message);

        try {
            SendMessageResponse response = messagesRequest.sendMessage(mmr);
        } catch (RequestFailedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setUrlConfiguration(MemmeeUrlConfiguration memmeeUrlConfiguration) {
        this.memmeeUrlConfiguration = memmeeUrlConfiguration;
    }

    protected void loadMandrill() {
        if (client == null) {
            //Config
            config.setApiKey("d39a51fc-7146-467f-9c05-b4aa2dd35b21");
            config.setApiVersion("1.0");
            config.setBaseURL("https://mandrillapp.com/api");

            //Client
            client = new DefaultHttpClient();

            //Request
            request.setConfig(config);
            request.setObjectMapper(mapper);
            request.setHttpClient(client);

            //MessageRequest
            messagesRequest.setRequest(request);
        }
    }
}