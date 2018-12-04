
package com.xxx.common.core.base.result;

/**
 * 响应信息枚举类
 *
 * @author xujingyang
 * @date 2018/05/28
 */
public enum Result {
    SUCCESS("200", "操作成功"),
    FAILED("444", "操作失败"),
    ERROR("500", "服务器端错误"),
    paramerror("001", "参数错误！"),
    SIGNERROR("505", "请先登录！"),

    LOGINSUCESS("200", "登录成功！"),

    LinkOutdated("014", "该链接已过期，请重新请求"),
    PassWordError("015", "密码输入错误"),

    UploadSuccess("200", "文件上传成功！"),
    FileEmpty("400", "上传文件为空"),
    LimitPictureSize("401", "图片大小必须小于2M"),
    LimitPictureType("402", "图片格式必须为'jpg'、'png'、'jpge'、'gif'、'bmp'");

    private Result(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }


}

