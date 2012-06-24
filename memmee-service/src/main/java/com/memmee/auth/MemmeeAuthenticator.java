package com.memmee.auth;

import com.google.common.base.Optional;
import com.memmee.user.dao.UserDAO;
import com.memmee.user.dto.User;
import com.yammer.dropwizard.auth.AuthenticationException;
import com.yammer.dropwizard.auth.Authenticator;
import com.yammer.dropwizard.auth.basic.BasicCredentials;

/**
 * Created with IntelliJ IDEA.
 * User: llappin
 * Date: 6/24/12
 * Time: 10:53 AM
 * To change this template use File | Settings | File Templates.
 */
public class MemmeeAuthenticator implements Authenticator<BasicCredentials, User> {
      private final UserDAO dao;

    public MemmeeAuthenticator(UserDAO dao){
          super();
          this.dao = dao;
    }

        public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {

            return Optional.fromNullable(dao.loginUser(credentials.getUsername(),credentials.getPassword()));

        }

}
