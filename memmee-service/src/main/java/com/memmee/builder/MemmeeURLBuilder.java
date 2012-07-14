package com.memmee.builder;

import com.memmee.util.ListUtil;
import com.memmee.util.StringUtil;
import org.skife.jdbi.v2.util.BooleanMapper;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: trevorewen
 * Date: 7/14/12
 * Time: 11:57 AM
 * To change this template use File | Settings | File Templates.
 */
public class MemmeeURLBuilder {

    private String baseURL = "memmeerest";
    private String methodURL;
    private List<String> appendURLS;
    private String apiKeyParam;
    private String idParam;

    public MemmeeURLBuilder setBaseURL(String baseURL) {
        this.baseURL = baseURL;
        return this;
    }

    public MemmeeURLBuilder setMethodURL(String methodURL) {
        this.methodURL = methodURL;
        return this;
    }

    public MemmeeURLBuilder setAppendURLS(List<String> appendURLS) {
        this.appendURLS = appendURLS;
        return this;
    }

    public MemmeeURLBuilder setApiKeyParam(String apiKeyParam) {
        this.apiKeyParam = apiKeyParam;
        return this;
    }

    public MemmeeURLBuilder setIdParam(Long idParam) {
        this.idParam = Long.toString(idParam);
        return this;
    }

    public String build() {
        if (baseURL == null || methodURL == null)
            throw new RuntimeException("You must specify a baseURL and methodURL or each call");

        String url = String.format("/%s/%s", baseURL, methodURL);

        if (!ListUtil.nullOrEmpty(appendURLS))
            for (String appendURL : appendURLS)
                url += String.format("/%s", appendURL);

        Boolean firstParam = true;

        if (!StringUtil.nullOrEmpty(apiKeyParam)) {
            url += appendParam(firstParam, "apiKey", apiKeyParam);
            firstParam = false;
        }

        if (!StringUtil.nullOrEmpty(idParam)) {
            url += appendParam(firstParam, "id", idParam);
            firstParam = false;
        }

        return url;
    }

    private String appendParam(Boolean first, String key, String value) {
        return String.format("%s%s=%s", first ? "?" : "&", key, value);
    }
}
