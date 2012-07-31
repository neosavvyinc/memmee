package com.memmee.builder;

import com.memmee.MemmeeResource;
import com.memmee.util.ListUtil;
import com.memmee.util.StringUtil;
import org.skife.jdbi.v2.util.BooleanMapper;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: trevorewen
 * Date: 7/14/12
 * Time: 11:57 AM
 * To change this template use File | Settings | File Templates.
 */
public class MemmeeURLBuilder {

    private String baseURL = MemmeeResource.BASE_URL;
    private String methodURL;
    private List<String> appendURLS;
    private String apiKeyParam;
    private String shareKeyParam;
    private String idParam;
    private Map<String, String> params;

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

    public MemmeeURLBuilder setShareKeyParam(String shareKeyParam) {
        this.shareKeyParam = shareKeyParam;
        return this;
    }

    public MemmeeURLBuilder setParam(String key, String value) {
        if (params == null)
            params = new HashMap<String, String>();
        params.put(key, value);
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

        if(!StringUtil.nullOrEmpty(shareKeyParam)) {
            url += appendParam(firstParam, "shareKey", shareKeyParam);
            firstParam = false;
        }

        if (params != null && params.size() != 0) {
            Iterator i = params.entrySet().iterator();
            while(i.hasNext()) {
                Map.Entry<String, String> pair = (Map.Entry<String, String>) i.next();
                url += appendParam(firstParam, pair.getKey(), pair.getValue());
                firstParam = false;
            }
        }

        return url;
    }

    private String appendParam(Boolean first, String key, String value) {
        return String.format("%s%s=%s", first ? "?" : "&", key, value);
    }
}
