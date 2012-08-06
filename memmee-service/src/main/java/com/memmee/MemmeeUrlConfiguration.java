package com.memmee;

import org.codehaus.jackson.annotate.JsonProperty;

import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: waparrish
 * Date: 8/5/12
 * Time: 6:50 PM
 */
public class MemmeeUrlConfiguration {


    /**
     * Valid options are
     * - development (default)
     * - qualityAssurance
     * - production
     */
    @NotNull
    @JsonProperty
    private String selectedEnvironment;

    @NotNull
    @JsonProperty
    private String devUrl;

    @NotNull
    @JsonProperty
    private String qaUrl;

    @NotNull
    @JsonProperty
    private String prodUrl;

    private String activeUrl;

    public String getActiveUrl() {
        return activeUrl;
    }

    public void setActiveUrl(String activeUrl) {
        this.activeUrl = activeUrl;
    }

    public String getSelectedEnvironment() {
        return selectedEnvironment;
    }

    public void setSelectedEnvironment(String selectedEnvironment) {
        this.selectedEnvironment = selectedEnvironment;
    }

    public String getDevUrl() {
        return devUrl;
    }

    public void setDevUrl(String devUrl) {
        this.devUrl = devUrl;
    }

    public String getQaUrl() {
        return qaUrl;
    }

    public void setQaUrl(String qaUrl) {
        this.qaUrl = qaUrl;
    }

    public String getProdUrl() {
        return prodUrl;
    }

    public void setProdUrl(String prodUrl) {
        this.prodUrl = prodUrl;
    }

    public void setActiveEnvironmentUrl(MemmeeConfiguration memmeeConfiguration) {
        String environmentOverride = System.getProperty("environmentOverride");
        if( "development".equals(memmeeConfiguration.getMemmeeUrlConfiguration().getSelectedEnvironment() ) )
        {
            memmeeConfiguration.getMemmeeUrlConfiguration().setActiveUrl(
                    memmeeConfiguration.getMemmeeUrlConfiguration().getDevUrl()
            );
        }
        else if( "qualityAssurance".equals(memmeeConfiguration.getMemmeeUrlConfiguration().getSelectedEnvironment() ) )
        {
            memmeeConfiguration.getMemmeeUrlConfiguration().setActiveUrl(
                    memmeeConfiguration.getMemmeeUrlConfiguration().getQaUrl()
            );
        }
        else if( "production".equals(memmeeConfiguration.getMemmeeUrlConfiguration().getSelectedEnvironment() ) )
        {
            memmeeConfiguration.getMemmeeUrlConfiguration().setActiveUrl(
                    memmeeConfiguration.getMemmeeUrlConfiguration().getProdUrl()
            );
        }
    }
}
