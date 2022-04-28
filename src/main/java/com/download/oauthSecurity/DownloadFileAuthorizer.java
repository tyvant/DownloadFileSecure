package com.download.oauthSecurity;

import java.util.Objects;

import io.dropwizard.auth.Authorizer;
import com.download.models.User;

public class DownloadFileAuthorizer implements Authorizer<User> {
    @Override
    public boolean authorize(User principal, String role) {
        return Objects.nonNull(principal);
    }
}