package com.memmee.util;

import com.cribbstechnologies.clients.mandrill.exception.RequestFailedException;
import com.cribbstechnologies.clients.mandrill.model.MandrillHtmlMessage;
import com.cribbstechnologies.clients.mandrill.model.MandrillMessageRequest;
import com.cribbstechnologies.clients.mandrill.model.MandrillRecipient;
import com.cribbstechnologies.clients.mandrill.model.response.message.SendMessageResponse;
import com.cribbstechnologies.clients.mandrill.request.MandrillMessagesRequest;
import com.cribbstechnologies.clients.mandrill.request.MandrillRESTRequest;
import com.cribbstechnologies.clients.mandrill.util.MandrillConfiguration;
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

    @Override
    public void sendConfirmationEmail(User user) {
        loadMandrill();

        MandrillMessageRequest mmr = new MandrillMessageRequest();
        MandrillHtmlMessage message = new MandrillHtmlMessage();
        Map<String, String> headers = new HashMap<String, String>();
        message.setFrom_email("aparrish@memmee.com");
        message.setFrom_name("Adam Parrish");
        message.setHeaders(headers);
        message.setHtml("<html><body><h1>Welcome to Memmee.com!</h1>Please click here to confirm your account and fill " +
                "out your profile.<a href=\"http://local.memmee.com/#/profile?apiKey="
                + user.getApiKey() + "\">Confirm your account</a></body></html>");
        message.setSubject("This is the subject");
        MandrillRecipient[] recipients = new MandrillRecipient[]{new MandrillRecipient("New Memmee User!", user.getEmail())};
        message.setTo(recipients);
        message.setTrack_clicks(true);
        message.setTrack_opens(true);
        String[] tags = new String[]{"tag1", "tag2", "tag3"};
        message.setTags(tags);
        mmr.setMessage(message);

        try {
            SendMessageResponse response = messagesRequest.sendMessage(mmr);
        } catch (RequestFailedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendForgotPasswordEmail(User user) {
        loadMandrill();

        MandrillMessageRequest mmr = new MandrillMessageRequest();
        MandrillHtmlMessage message = new MandrillHtmlMessage();
        Map<String, String> headers = new HashMap<String, String>();
        message.setFrom_email("aparrish@memmee.com");
        message.setFrom_name("Adam Parrish");
        message.setHeaders(headers);
        message.setHtml(String.format("<html><body><h1>Forgotten Password</h1>Your password is %s. " +
                "Login at <a href=\"http://local.memmee.com/#\">Memmee</a> to see your profile now.",
                user.getPassword()));

        message.setSubject("Memmee - Forgotten Password");
        MandrillRecipient[] recipients = new MandrillRecipient[]{new MandrillRecipient("New Memmee User!", user.getEmail())};
        message.setTo(recipients);
        message.setTrack_clicks(true);
        message.setTrack_opens(true);
        String[] tags = new String[]{"tag1", "tag2", "tag3"};
        message.setTags(tags);
        mmr.setMessage(message);

        try {
            SendMessageResponse response = messagesRequest.sendMessage(mmr);
        } catch (RequestFailedException e) {
            e.printStackTrace();
        }
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