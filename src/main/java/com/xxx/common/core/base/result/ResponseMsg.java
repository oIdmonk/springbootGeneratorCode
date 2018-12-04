package com.xxx.common.core.base.result;


import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 响应结果信息封装
 *
 * @author xujingyang
 * @date 2018/05/25
 */
@ApiModel(value = "响应信息")
public class ResponseMsg {
    /**
     * 返回信息码
     */
    @ApiModelProperty(value = "返回信息码",example = "200")
    @JSONField(ordinal = 1)
    private String code = "200";
    /**
     * 返回信息内容
     */
    @ApiModelProperty(value = "返回信息内容",example = "请求成功")
    @JSONField(ordinal = 2)
    private String msg = "请求成功";

    public ResponseMsg() {
    }

    public ResponseMsg(Result result) {
        this.code = result.getCode();
        this.msg = result.getMsg();
    }

    public ResponseMsg(String code) {
        this.code = code;
        this.msg = "";
    }

    public ResponseMsg(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Response{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
