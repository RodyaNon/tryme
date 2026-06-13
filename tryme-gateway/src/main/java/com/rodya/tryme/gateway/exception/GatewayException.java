package com.rodya.tryme.gateway.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 网关异常类
 */
@Data
@NoArgsConstructor
public class GatewayException extends RuntimeException {
    
    /**
     * 错误码
     */
    private String errorCode;
    
    /**
     * 错误消息
     */
    private String message;
    
    /**
     * HTTP状态码
     */
    private int httpStatus;
    
    public GatewayException(String message, int httpStatus) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
    }
    
    public GatewayException(String errorCode, String message, int httpStatus) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
