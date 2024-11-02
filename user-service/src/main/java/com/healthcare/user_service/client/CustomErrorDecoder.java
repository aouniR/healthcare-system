package com.healthcare.user_service.client;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.healthcare.user_service.exception.GenericErrorResponse;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream body = response.body().asInputStream()) {
            Map<String, String> errors = mapper.readValue(
                    IOUtils.toString(body, StandardCharsets.UTF_8),
                    new TypeReference<Map<String, String>>() {}
            );
    
            return GenericErrorResponse.builder()
                    .httpStatus(HttpStatus.valueOf(response.status()))
                    .message(errors.get("error"))
                    .build();
    
        } catch (IOException exception) {
            return GenericErrorResponse.builder()
                    .httpStatus(HttpStatus.valueOf(response.status()))
                    .message(exception.getMessage())
                    .build();
        }
    }
}
