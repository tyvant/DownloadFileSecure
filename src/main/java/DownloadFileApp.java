import configuration.AppConfiguration;
import healthCheck.DownloadFileAppHealthCheck;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.setup.Environment;
import models.User;
import oauthSecurity.DownloadFileAuthenticator;
import oauthSecurity.DownloadFileAuthorizer;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.jdbi.v3.core.Jdbi;
import repository.UserRepository;
import controllers.UsersControllers;

public class DownloadFileApp extends Application<AppConfiguration> {
    private static final String SQL = "sql";
    private static final String DOWNLOADFILE_SERVICE = "Download service";

    public static void main(String[] args) throws Exception {
        new DownloadFileApp().run(args);
    }


    @Override
    public void run(AppConfiguration configuration, Environment environment) {
        // jdbi connexion
        JdbiFactory jdbiFactory = new JdbiFactory();
        final Jdbi jdbi = jdbiFactory.build(environment, configuration.getDataSourceFactory(), "database");

        UserRepository userRepository  = new UserRepository(jdbi);
        final UsersControllers usersControllers = new UsersControllers(userRepository);
        environment.jersey().register(usersControllers);
        // Register Health Check
        DownloadFileAppHealthCheck healthCheck =
                new DownloadFileAppHealthCheck(jdbi.onDemand(UserRepository.class));
        environment.healthChecks().register(DOWNLOADFILE_SERVICE, healthCheck);


        // Register OAuth authentication
        environment.jersey()
                .register(new AuthDynamicFeature(new BasicCredentialAuthFilter.Builder<User>()
                        .setAuthenticator(new DownloadFileAuthenticator(userRepository))
                        .setAuthorizer(new DownloadFileAuthorizer()).setRealm("BASIC-AUTH-REALM").buildAuthFilter()));
        environment.jersey().register(RolesAllowedDynamicFeature.class);
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));

    }
}
