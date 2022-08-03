package com.anbit.fashionist.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;


public class ResponseHandler {
    public static ResponseEntity<Object> generateSuccessResponse(HttpStatus status, HttpHeaders headers, ZonedDateTime accessedTime, String message, Object response){
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("http_status", status);
        responseMap.put("success", true);
        responseMap.put("message", message);
        responseMap.put("accessed_time", accessedTime);
        responseMap.put("data", response);
        return new ResponseEntity<Object>(responseMap, status);
    }

    public static ResponseEntity<Object> generateSuccessResponseWithMeta(HttpStatus status, HttpHeaders headers, ZonedDateTime accessedTime, String message, Object response, Object metaData){
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("http_status", status);
        responseMap.put("success", true);
        responseMap.put("message", message);
        responseMap.put("accessed_time", accessedTime);
        responseMap.put("meta_data", metaData);
        responseMap.put("data", response);
        return new ResponseEntity<Object>(responseMap, status);
    }

    public static ResponseEntity<Object> generateSuccessResponseWithPagination(HttpStatus status, HttpHeaders headers, ZonedDateTime accessedTime, String message, Object response, Object pagination){
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("http_status", status);
        responseMap.put("success", true);
        responseMap.put("message", message);
        responseMap.put("accessed_time", accessedTime);
        responseMap.put("pagination", pagination);
        responseMap.put("data", response);
        return new ResponseEntity<Object>(responseMap, status);
    }

    public static ResponseEntity<Object> generateErrorResponse(String message, HttpStatus status, HttpHeaders headers, ZonedDateTime accessedTime, Object response, Object metaData){
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("hhtp_status", status);
        responseMap.put("success", false);
        responseMap.put("accessed_time", accessedTime);
        responseMap.put("error_code", response);
        responseMap.put("error_message", message);
        return new ResponseEntity<Object>(responseMap, status);
    }
}
