package com.anbit.fashionist.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;


public class ResponseHandler {
    private static final Logger logger = LoggerFactory.getLogger(ResponseHandler.class);
    private static final String loggerLine = "---------------------------------------";
    
    public static ResponseEntity<Object> generateSuccessResponse(HttpStatus status, String message, Object data){
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("http_status", status);
        responseMap.put("success", true);
        responseMap.put("message", message);
        responseMap.put("accessed_time", ZonedDateTime.now());
        responseMap.put("data", data);
        logger.info(loggerLine);
        if (data != null) {
            logger.info(data.toString());
        }else{
            logger.info(message);
        }
        logger.info(loggerLine);
        return new ResponseEntity<Object>(responseMap, status);
    }
    
    public static ResponseEntity<Object> generateSuccessResponseWithMeta(HttpStatus status, String message, Object data, Object metaData){
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("http_status", status);
        responseMap.put("success", true);
        responseMap.put("message", message);
        responseMap.put("accessed_time", ZonedDateTime.now());
        responseMap.put("meta_data", metaData);
        responseMap.put("data", data);
        logger.info(loggerLine);
        if (data != null) {
            logger.info(data.toString());
        }else{
            logger.info(message);
        }
        logger.info(loggerLine);
        return new ResponseEntity<Object>(responseMap, status);
    }

    public static ResponseEntity<Object> generateSuccessResponseWithPagination(HttpStatus status, String message, Object data, Object pagination){
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("http_status", status);
        responseMap.put("success", true);
        responseMap.put("message", message);
        responseMap.put("accessed_time", ZonedDateTime.now());
        responseMap.put("pagination", pagination);
        responseMap.put("data", data);
        if (data != null) {
            logger.info(data.toString());
        }else{
            logger.info(message);
        }
        logger.info(loggerLine);
        return new ResponseEntity<Object>(responseMap, status);
    }

    public static ResponseEntity<Object> generateErrorResponse(HttpStatus status, String errorMessage, int errorCode, String errorDescription){
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("hhtp_status", status);
        responseMap.put("success", false);
        responseMap.put("accessed_time", ZonedDateTime.now());
        responseMap.put("error_code", errorCode);
        responseMap.put("error_message", errorMessage);
        responseMap.put("error_description", errorDescription);
        logger.error(loggerLine);
        logger.info(errorMessage);
        logger.info(loggerLine);
        return new ResponseEntity<Object>(responseMap, status);
    }
}
