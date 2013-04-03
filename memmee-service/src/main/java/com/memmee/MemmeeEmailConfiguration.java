package com.memmee;

import org.codehaus.jackson.annotate.JsonProperty;
import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: jmlaudate
 * Date: 3/31/13
 * Time: 11:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class MemmeeEmailConfiguration {
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
    private String devUsername;

    @NotNull
    @JsonProperty
    private String qaUsername;

    @NotNull
    @JsonProperty
    private String prodUsername;

    @NotNull
    @JsonProperty
    private String devPassword;

    @NotNull
    @JsonProperty
    private String qaPassword;

    @NotNull
    @JsonProperty
    private String prodPassword;

    @NotNull
    @JsonProperty
    private String devServer;

    @NotNull
    @JsonProperty
    private String qaServer;

    @NotNull
    @JsonProperty
    private String prodServer;

    /**
     * Active properties are the ones that should be
     * set via the configuration override based on the
     * selected environment
     */
    private String activeUsername;

    private String activePassword;

    private String activeServer;

    public String getActiveUsername() {
        return activeUsername;
    }

    public void setActiveUsername(String activeUsername) {
        this.activeUsername = activeUsername;
    }

    public String getActivePassword() {
        return activePassword;
    }

    public void setActivePassword(String activePassword) {
        this.activePassword = activePassword;
    }

    public String getActiveServer() {
        return activeServer;
    }

    public void setActiveServer(String activeServer) {
        this.activeServer = activeServer;
    }

    public String getSelectedEnvironment() {
        return selectedEnvironment;
    }

    public void setSelectedEnvironment(String selectedEnvironment) {
        this.selectedEnvironment = selectedEnvironment;
    }

    public String getDevUsername() {
        return devUsername;
    }

    public void setDevUsername(String devUsername) {
        this.devUsername = devUsername;
    }

    public String getQaUsername() {
        return qaUsername;
    }

    public void setQaUsername(String qaUsername) {
        this.qaUsername = qaUsername;
    }

    public String getProdUsername() {
        return prodUsername;
    }

    public void setProdUsername(String prodUsername) {
        this.prodUsername = prodUsername;
    }

    public String getDevPassword() {
        return devPassword;
    }

    public void setDevPassword(String devPassword) {
        this.devPassword = devPassword;
    }

    public String getQaPassword() {
        return qaPassword;
    }

    public void setQaPassword(String qaPassword) {
        this.qaPassword = qaPassword;
    }

    public String getProdPassword() {
        return prodPassword;
    }

    public void setProdPassword(String prodPassword) {
        this.prodPassword = prodPassword;
    }

    public String getDevServer() {
        return devServer;
    }

    public void setDevServer(String devServer) {
        this.devServer = devServer;
    }

    public String getQaServer() {
        return qaServer;
    }

    public void setQaServer(String qaServer) {
        this.qaServer = qaServer;
    }

    public String getProdServer() {
        return prodServer;
    }

    public void setProdServer(String prodServer) {
        this.prodServer = prodServer;
    }


    public void setActiveEmailEnvironment(MemmeeConfiguration memmeeConfiguration) {

        if( "development".equals(memmeeConfiguration.getMemmeeEmailConfiguration().getSelectedEnvironment() ) )
        {
            memmeeConfiguration.getMemmeeEmailConfiguration().setActiveUsername(
                    memmeeConfiguration.getMemmeeEmailConfiguration().getDevUsername()
            );

            memmeeConfiguration.getMemmeeEmailConfiguration().setActivePassword(
                    memmeeConfiguration.getMemmeeEmailConfiguration().getDevPassword()
            );

            memmeeConfiguration.getMemmeeEmailConfiguration().setActiveServer(
                    memmeeConfiguration.getMemmeeEmailConfiguration().getDevServer()
            );
        }
        else if( "qualityAssurance".equals(memmeeConfiguration.getMemmeeEmailConfiguration().getSelectedEnvironment() ) )
        {
            memmeeConfiguration.getMemmeeEmailConfiguration().setActiveUsername(
                    memmeeConfiguration.getMemmeeEmailConfiguration().getQaUsername()
            );

            memmeeConfiguration.getMemmeeEmailConfiguration().setActivePassword(
                    memmeeConfiguration.getMemmeeEmailConfiguration().getQaPassword()
            );

            memmeeConfiguration.getMemmeeEmailConfiguration().setActiveServer(
                    memmeeConfiguration.getMemmeeEmailConfiguration().getQaServer()
            );
        }
        else if( "production".equals(memmeeConfiguration.getMemmeeEmailConfiguration().getSelectedEnvironment() ) )
        {
            memmeeConfiguration.getMemmeeEmailConfiguration().setActiveUsername(
                    memmeeConfiguration.getMemmeeEmailConfiguration().getProdUsername()
            );

            memmeeConfiguration.getMemmeeEmailConfiguration().setActivePassword(
                    memmeeConfiguration.getMemmeeEmailConfiguration().getProdPassword()
            );

            memmeeConfiguration.getMemmeeEmailConfiguration().setActiveServer(
                    memmeeConfiguration.getMemmeeEmailConfiguration().getProdServer()
            );
        }
    }
}
