package oauthSecurity;

import java.util.Objects;

import io.dropwizard.auth.Authorizer;
import models.User;

public class DownloadFileAuthorizer implements Authorizer<User> {
    @Override
    public boolean authorize(User principal, String role) {
        return Objects.nonNull(principal);
    }
}