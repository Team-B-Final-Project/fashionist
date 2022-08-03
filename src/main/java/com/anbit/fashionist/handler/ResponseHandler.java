package com.anbit.fashionist.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;


public class ResponseHandler {
    public static ResponseEntity<Object> generateSuccessResponse(HttpStatus status, ZonedDateTime accessedTime, String message, Object data){
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("http_status", status);
        responseMap.put("success", true);
        responseMap.put("message", message);
        responseMap.put("accessed_time", accessedTime);
        responseMap.put("data", data);
        return new ResponseEntity<Object>(responseMap, status);
    }

    public static ResponseEntity<Object> generateSuccessResponseWithMeta(HttpStatus status, ZonedDateTime accessedTime, String message, Object data, Object metaData){
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("http_status", status);
        responseMap.put("success", true);
        responseMap.put("message", message);
        responseMap.put("accessed_time", accessedTime);
        responseMap.put("meta_data", metaData);
        responseMap.put("data", data);
        return new ResponseEntity<Object>(responseMap, status);
    }

    public static ResponseEntity<Object> generateSuccessResponseWithPagination(HttpStatus status, ZonedDateTime accessedTime, String message, Object data, Object pagination){
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("http_status", status);
        responseMap.put("success", true);
        responseMap.put("message", message);
        responseMap.put("accessed_time", accessedTime);
        responseMap.put("pagination", pagination);
        responseMap.put("data", data);
        return new ResponseEntity<Object>(responseMap, status);
    }

    public static ResponseEntity<Object> generateErrorResponse(HttpStatus status, ZonedDateTime accessedTime, String errorMessage, int errorCode){
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("hhtp_status", status);
        responseMap.put("success", false);
        responseMap.put("accessed_time", accessedTime);
        responseMap.put("error_code", errorCode);
        responseMap.put("error_message", errorMessage);
        return new ResponseEntity<Object>(responseMap, status);
    }
}