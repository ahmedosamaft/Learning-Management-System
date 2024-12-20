package fci.swe.advanced_software.controllers;

import fci.swe.advanced_software.utils.ResponseEntityBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class HealthCheckController {
    @Value("${SERVER_NAME:Unknown}")
    private String serverName;

    @GetMapping("/healthz")
    public ResponseEntity<?> healthCheck() {
        log.info("Health check request received from {}", serverName);
        return ResponseEntityBuilder.create().withStatus(HttpStatus.OK)
                .withMessage("OK")
                .withData("status", "UP")
                .withData("Server", serverName)
                .build();
    }

    @GetMapping
    public ResponseEntity<?> healthCheck2() {
        log.info("request received from {}", serverName);
        return ResponseEntityBuilder.create().withStatus(HttpStatus.OK)
                .withMessage("OK")
                .withData("status", "UP")
                .withData("Server", serverName)
                .build();
    }
}
