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

    @NotNull
    @JsonProperty
    private String devFromEmailAddress;

    @NotNull
    @JsonProperty
    private String qaFromEmailAddress;



    @NotNull
    @JsonProperty
    private String prodFromEmailAddress;

    /**
     * Active properties are the ones that should be
     * set via the configuration override based on the
     * selected environment
     */
    private String activeUrl;

    private String activeEmailAddress;

    public String getActiveEmailAddress() {
        return activeEmailAddress;
    }

    public void setActiveEmailAddress(String activeEmailAddress) {
        this.activeEmailAddress = activeEmailAddress;
    }

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

    public String getDevFromEmailAddress() {
        return devFromEmailAddress;
    }

    public void setDevFromEmailAddress(String devFromEmailAddress) {
        this.devFromEmailAddress = devFromEmailAddress;
    }

    public String getQaFromEmailAddress() {
        return qaFromEmailAddress;
    }

    public void setQaFromEmailAddress(String qaFromEmailAddress) {
        this.qaFromEmailAddress = qaFromEmailAddress;
    }

    public String getProdFromEmailAddress() {
        return prodFromEmailAddress;
    }

    public void setProdFromEmailAddress(String prodFromEmailAddress) {
        this.prodFromEmailAddress = prodFromEmailAddress;
    }

    public void setActiveEnvironmentUrl(MemmeeConfiguration memmeeConfiguration) {

        if( "development".equals(memmeeConfiguration.getMemmeeUrlConfiguration().getSelectedEnvironment() ) )
        {
            memmeeConfiguration.getMemmeeUrlConfiguration().setActiveUrl(
                    memmeeConfiguration.getMemmeeUrlConfiguration().getDevUrl()
            );

            memmeeConfiguration.getMemmeeUrlConfiguration().setActiveEmailAddress(
                    memmeeConfiguration.getMemmeeUrlConfiguration().getDevFromEmailAddress()
            );
        }
        else if( "qualityAssurance".equals(memmeeConfiguration.getMemmeeUrlConfiguration().getSelectedEnvironment() ) )
        {
            memmeeConfiguration.getMemmeeUrlConfiguration().setActiveUrl(
                    memmeeConfiguration.getMemmeeUrlConfiguration().getQaUrl()
            );

            memmeeConfiguration.getMemmeeUrlConfiguration().setActiveEmailAddress(
                    memmeeConfiguration.getMemmeeUrlConfiguration().getQaFromEmailAddress()
            );
        }
        else if( "production".equals(memmeeConfiguration.getMemmeeUrlConfiguration().getSelectedEnvironment() ) )
        {
            memmeeConfiguration.getMemmeeUrlConfiguration().setActiveUrl(
                    memmeeConfiguration.getMemmeeUrlConfiguration().getProdUrl()
            );

            memmeeConfiguration.getMemmeeUrlConfiguration().setActiveEmailAddress(
                    memmeeConfiguration.getMemmeeUrlConfiguration().getProdFromEmailAddress()
            );
        }
    }
}
