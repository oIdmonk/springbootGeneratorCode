package com.xxx.common.core.base.controller;

import com.xxx.common.Const;
import com.xxx.common.core.base.result.ResponseData;
import com.xxx.common.core.base.result.ResponseMsg;
import com.xxx.common.core.base.result.Result;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 控制层父级基础类，所有控制层需继承此类
 *
 * @author xujingyang
 * @date 2018/05/28
 */
@Controller
public class BaseController {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * 业务处理正确返回结果--基方法
     *
     * @return
     */
    protected ResponseMsg resultOk() {
        return new ResponseMsg(Result.SUCCESS);
    }


    /**
     * 服务端错误返回结果--基方法
     *
     * @return
     */
    protected ResponseMsg resultError() {
        return new ResponseMsg(Result.ERROR);
    }

    /**
     * 业务处理失败返回结果--基方法
     *
     * @return
     */
    protected ResponseMsg resultFailed() {
        return new ResponseMsg(Result.FAILED);
    }



    /**
     * 枚举结果--基方法
     *
     * @return
     */
    protected ResponseMsg result(Result msg) {
        return new ResponseMsg(msg);
    }


    /**
     * 自定义结果信息--基方法
     * 不建议直接自定义结果，建议在Result枚举中添加结果，方便统一管理
     *
     * @return
     */
    @Deprecated
    protected ResponseMsg result(String code, String msg) {
        return new ResponseMsg(code, msg);
    }


    /**
     * 业务处理正确返回数据结果集--基方法
     *
     * @return
     */
    protected ResponseData resultData(Object obj) {
        return new ResponseData(obj);
    }

    /**
     * 自定义结果集信息--基方法
     *
     * @return
     */
    protected ResponseData resultData(Result result, Object obj) {
        return new ResponseData(result, obj);
    }

    /**
     * 自定义结果数据集--基方法
     * 不建议直接自定义结果，建议建议调用resultData方法，在Result枚举中添加结果提示信息，方便统一管理
     *
     * @return
     */
    @Deprecated
    protected ResponseData resultData(String code, String msg, Object obj) {
        return new ResponseData(code, msg, obj);
    }

    /**
     * 获取request
     *
     * @return
     */
    protected HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 获取session
     *
     * @return
     */
    protected HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * 获取用户对象
     *
     * @return
     */
    protected Object getUser() {
        return (Object) getSession().getAttribute(Const.LOGIN_SESSION_KEY);
    }

    /**
     * 获取用户id
     *
     * @return
     */
    protected String getUserId() {
        String id = "";
        Object user = getUser();

       /* if (user != null) {
            id = user.getUserId();
        }*/

        return id;
    }

    /**
     * 获取用户名字
     *
     * @return
     */
    protected String getUserName() {
        String userName = "无名";
        Object user = getUser();

      /*  if (user != null) {
            userName = user.getUserName();
        }
*/
        return userName;
    }

    /**
     * 获取用户ip
     *
     * @return
     */
    protected String getUserIp() {
        String value = getRequest().getHeader("X-Real-IP");

        if (StringUtils.isNotBlank(value) && !"unknown".equalsIgnoreCase(value)) {
            return value;
        } else {
            return getRequest().getRemoteAddr();
        }
    }
}

