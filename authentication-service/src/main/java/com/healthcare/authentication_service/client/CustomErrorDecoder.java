package com.healthcare.authentication_service.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthcare.authentication_service.exception.GenericErrorResponse;
import com.healthcare.authentication_service.exception.ValidationException;
import feign.Response;
import com.fasterxml.jackson.core.type.TypeReference;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;
import java.util.Map;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.InputStream;


public class CustomErrorDecoder implements ErrorDecoder {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Exception decode(String methodKey, Response response) {
        try (InputStream body = response.body().asInputStream()) {
            Map<String, String> errors = mapper.readValue(
                IOUtils.toString(body, StandardCharsets.UTF_8),
                new TypeReference<Map<String, String>>() {});
            if (response.status() == 400) {
                return ValidationException.builder()
                        .validationErrors(errors).build();
            } else
                return GenericErrorResponse
                        .builder()
                        .httpStatus(HttpStatus.valueOf(response.status()))
                        .message(errors.get("error"))
                        .build();

        } catch (IOException exception) {
            throw GenericErrorResponse.builder()
                    .httpStatus(HttpStatus.valueOf(response.status()))
                    .message(exception.getMessage())
                    .build();
        }
    }
}