package com.joinme.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.io.IOException;

@Configuration
public class IndexConfig {
    @Value("${bootWithSwagger}")
    private boolean bootWithSwagger;

    @Value("${server.port}")
    private String port;

    @EventListener({ApplicationReadyEvent.class})
    void applicationReadyEvent() {
        if (bootWithSwagger) {
            String openUrl = "http://localhost:" + port + "/swagger-ui.html";
            String osName = System.getProperty("os.name");
            Runtime runtime = Runtime.getRuntime();
            try {
                if (osName.startsWith("Windows")) {
                    runtime.exec("cmd /c start " + openUrl);
                } else if (osName.startsWith("Mac OS")) {
                    runtime.exec("open " + openUrl);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
