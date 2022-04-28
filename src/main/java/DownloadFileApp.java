import com.download.configuration.AppConfiguration;
import com.download.controllers.FileDownloadController;
import com.download.controllers.FileUploadController;
import com.download.repository.FileRepository;
import io.dropwizard.Application;
import io.dropwizard.forms.MultiPartBundle;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.jdbi.v3.core.Jdbi;

public class DownloadFileApp extends Application<AppConfiguration> {
    @Override
    public void initialize(Bootstrap<AppConfiguration> bootstrap) {
        bootstrap.addBundle(new MultiPartBundle());
    }
    public static void main(String[] args) throws Exception {
        new DownloadFileApp().run(args);
    }


    @Override
    public void run(AppConfiguration configuration, Environment environment) {
        // jdbi connexion
        JdbiFactory jdbiFactory = new JdbiFactory();
        final Jdbi jdbi = jdbiFactory.build(environment, configuration.getDataSourceFactory(), "database");

        FileRepository fileRepository  = new FileRepository(jdbi);
        final FileUploadController fileUploadController = new FileUploadController(fileRepository);
        final FileDownloadController fileDownloadController = new FileDownloadController(fileRepository);
        environment.jersey().register(fileUploadController);
        environment.jersey().register(fileDownloadController);

    }
}
