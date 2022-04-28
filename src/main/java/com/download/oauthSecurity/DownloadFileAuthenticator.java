package com.download.oauthSecurity;

import java.util.Optional;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import com.download.models.User;
import com.download.repository.UserRepository;

public class DownloadFileAuthenticator implements Authenticator<BasicCredentials, User> {
      private final UserRepository userRepository;

    public DownloadFileAuthenticator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {
          User userToCheck = userRepository.findByUsername(credentials.getUsername());
        if (userToCheck != null) {
             if (userToCheck.getPassword().equals(credentials.getPassword())){
                  return Optional.of(userToCheck);
             }
        }
        return Optional.empty();
    }
}