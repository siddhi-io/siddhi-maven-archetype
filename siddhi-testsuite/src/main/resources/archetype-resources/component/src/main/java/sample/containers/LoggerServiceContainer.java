package sample.containers;

import io.siddhi.distribution.test.framework.util.HTTPClient;
import org.testcontainers.containers.ContainerLaunchException;
import org.testcontainers.containers.GenericContainer;

import java.io.IOException;
import java.net.Socket;
import java.net.URI;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.rnorth.ducttape.unreliables.Unreliables.retryUntilSuccess;

/**
 * Siddhi Logger Service container class.
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


    void setStartupTimeoutSeconds(int startupTimeoutSeconds) {
        this.startupTimeoutSeconds = startupTimeoutSeconds;
    }

    @Override
    protected void waitUntilContainerStarted() {
        logger().info("Waiting for Logger Service Container to start...");
        retryUntilSuccess(getStartupTimeoutSeconds(), TimeUnit.SECONDS, () -> {
            if (!isRunning()) {
                throw new ContainerLaunchException("Logger Service  Container failed to start");
            }
            try (Socket s = new Socket("0.0.0.0", this.getMappedPort(DEFAULT_LOGSERVICE_PORT))) {
                return null;
            }
        });
    }

    private HTTPClient.HTTPResponseMessage callHealthAPI() {
        try {
            URI baseURI = URI.create(String.format("http://%s:%d", "0.0.0.0",
                    this.getMappedPort(DEFAULT_LOGSERVICE_PORT)));

            HTTPClient healthRequest = new HTTPClient(baseURI, "/logger", false, false,
                    "GET", (String) null, "admin", "admin");
            return healthRequest.getResponse();
        } catch (IOException var2) {
            return new HTTPClient.HTTPResponseMessage();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        LoggerServiceContainer that = (LoggerServiceContainer) o;
        return Objects.equals(IMAGE_NAME, that.IMAGE_NAME) &&
                Objects.equals(startupTimeoutSeconds, that.startupTimeoutSeconds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), IMAGE_NAME, startupTimeoutSeconds);
    }

}
