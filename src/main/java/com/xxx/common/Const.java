package com.xxx.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 项目常量
 *
 * @author xujingyang
 * @date 2018/05/28
 */
@Component
public class Const {

    public static String LOGIN_URL;

    public static String BASE_PATH;

    public static String LOGIN_SESSION_KEY;

    @Autowired
    public void setLoginUrl(@Value("${jifenzhi_zx.loginUrl}") String loginUrl) {
        Const.LOGIN_URL = loginUrl;
    }

    @Autowired
    public void setBasePath(@Value("${jifenzhi_zx.basePath}") String basePath) {
        Const.BASE_PATH = basePath;
    }

    @Autowired
    public void setLoginSessionKey(@Value("${jifenzhi_zx.loginSessionKey}") String loginSessionKey) {
        Const.LOGIN_SESSION_KEY = loginSessionKey;
    }
}
