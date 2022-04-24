package healthCheck;

import com.codahale.metrics.health.HealthCheck;
import repository.UserRepository;

public class DownloadFileAppHealthCheck extends HealthCheck {
    private static final String HEALTHY = "The Dropwizard blog Service is healthy for read and write";
    private static final String UNHEALTHY = "The Dropwizard blog Service is not healthy. ";
    private static final String MESSAGE_PLACEHOLDER = "{}";

    private final UserRepository userRepository;

    public DownloadFileAppHealthCheck(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Result check() throws Exception {
        String mySqlHealthStatus = userRepository.performHealthCheck();

        if (mySqlHealthStatus == null) {
            return Result.healthy(HEALTHY);
        } else {
            return Result.unhealthy(UNHEALTHY + MESSAGE_PLACEHOLDER, mySqlHealthStatus);
        }
    }
}