package com.xxx.common.core.base.exception;

/**
 * 服务（业务）异常如“ 账号或密码错误 ”，业务上错误请抛此异常即可
 *
 * @author xujingyang
 * @date 2018/05/28
 */
public class ServiceException extends RuntimeException {
    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
