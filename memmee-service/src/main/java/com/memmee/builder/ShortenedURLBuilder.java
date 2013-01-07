package com.memmee.builder;

import com.rosaloves.bitlyj.*;
import static com.rosaloves.bitlyj.Bitly.*;

public class ShortenedURLBuilder {

    private final String USER_NAME = "memmee";
    private final String API_KEY = "R_062f7741bc427044e5a7c9a900e7bb0d";
    private String url;

    public ShortenedURLBuilder setUrl(String url) {
        this.url = url;
        return this;
    }

    public String build() {
        //Bitly API Call
        Url myUrl = as(USER_NAME, API_KEY).call(shorten(this.url));

        //Returns shortened url from hash object
        return myUrl.getShortUrl();
    }

}
