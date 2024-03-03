package com.fatec.quintosemestre.projetomarketing.service.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ApiResponse<T> {

    private T body;
    private String message;
    private String uri;

    public ApiResponse() {

    }

    public ApiResponse(T body) {
        this.body = body;
    }

    public ApiResponse(String message) {
        this.message = message;
    }
    
    public ApiResponse(String message, String uri) {
        this.message = message;
        this.uri = uri;
    }

    public ApiResponse(T body, String message) {
        this.body = body;
        this.message = message;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

}
