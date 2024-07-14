package tech.yump.jobportal;

import org.slf4j.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final Marker marker = MarkerFactory.getMarker("MAIN");

    public static void main(String[] args) {
        MDC.put("logFileName", "${APP_NAME}");
        logger.info(marker, "Job Portal Application Started");

        SpringApplication.run(Main.class, args);
    }

}
