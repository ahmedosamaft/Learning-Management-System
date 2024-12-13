package fci.swe.advanced_software.utils;

import fci.swe.advanced_software.dtos.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseEntityBuilder {
    private HttpStatus status;
    private String message;
    private Object data;
    private HttpHeaders headers;
    private String locationUrl;

    private ResponseEntityBuilder() {
        this.headers = new HttpHeaders();
        this.status = HttpStatus.OK;
    }

    public static ResponseEntityBuilder create() {
        return new ResponseEntityBuilder();
    }

    public ResponseEntityBuilder withStatus(HttpStatus status) {
        this.status = status;
        return this;
    }

    public ResponseEntityBuilder withMessage(String message) {
        this.message = message;
        return this;
    }

    public ResponseEntityBuilder withData(Object data) {
        this.data = data;
        return this;
    }

    public ResponseEntityBuilder withHeaders(HttpHeaders headers) {
        this.headers = headers;
        return this;
    }

    public ResponseEntityBuilder withLocation(String locationUrl) {
        this.locationUrl = locationUrl;
        return this;
    }

    public ResponseEntity<Response> build() {
        Response response = Response.builder()
                .status(status.getReasonPhrase())
                .message(message)
                .data(data)
                .build();

        if (locationUrl != null && !locationUrl.isEmpty()) {
            headers.add("Location", locationUrl);
        }

        return new ResponseEntity<>(response, headers, status);
    }
}
