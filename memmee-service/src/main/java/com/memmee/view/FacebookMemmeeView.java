package com.memmee.view;

import com.memmee.domain.memmees.dto.Memmee;
import com.yammer.dropwizard.views.View;

/**
 * Created with IntelliJ IDEA.
 * User: waparrish
 * Date: 1/23/13
 * Time: 7:25 PM
 */
public class FacebookMemmeeView extends View {

    private final Memmee memmee;

    public FacebookMemmeeView(Memmee memmee) {
        super("facebookView.ftl");
        this.memmee = memmee;
    }

    public Memmee getMemmee() {
        return memmee;
    }
}
