package ${package};

import org.testcontainers.containers.ContainerLaunchException;
import org.testcontainers.containers.GenericContainer;

import java.net.Socket;
import java.util.concurrent.TimeUnit;

import static org.rnorth.ducttape.unreliables.Unreliables.retryUntilSuccess;

/**
 * Base class that expose a LoggerService container.
 *
 */
public class LoggerServiceContainer extends GenericContainer<LoggerServiceContainer> {

    private static final String IMAGE_NAME = "siddhi-logservice";
    private static final String DEFAULT_LOGSERVICE_VERSION = "1.0.0";
    private static final int DEFAULT_LOGSERVICE_PORT = 8080;
    private int startupTimeoutSeconds = 120;

    public LoggerServiceContainer() {
        super(IMAGE_NAME + ":" + DEFAULT_LOGSERVICE_VERSION);
        withExposedPorts(DEFAULT_LOGSERVICE_PORT);
    }

    public String getNetworkedUrl() {
        return "http://" + getNetworkAliases().get(0) + ":" + DEFAULT_LOGSERVICE_PORT + "/logger";
    }

    public String getUrl() {
        return "http://" + getContainerIpAddress() + ":" + getMappedPort(DEFAULT_LOGSERVICE_PORT) + "/logger";
    }

    int getStartupTimeoutSeconds() {
        return startupTimeoutSeconds;
    }

    @Override
    protected void waitUntilContainerStarted() {
        logger().info("Waiting for LoggerService Streaming Container to start...");
        retryUntilSuccess(getStartupTimeoutSeconds(), TimeUnit.SECONDS, () -> {
            if (!isRunning()) {
                throw new ContainerLaunchException("LoggerService Streaming Container failed to start");
            }
            try (Socket s = new Socket("localhost", this.getMappedPort(DEFAULT_LOGSERVICE_PORT))) {
                return null;
            }
        });
    }

}
