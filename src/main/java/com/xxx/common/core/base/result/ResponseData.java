package com.xxx.common.core.base.result;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;

/**
 * 响应结果数据集封装
 *
 * @author xujingyang
 * @date 2018/05/25
 */
public class ResponseData<T> extends ResponseMsg {

    @ApiModelProperty(value = "返回数据obj")
    @JSONField(ordinal = 3)
    private T resultData;

    public ResponseData(T resultData) {
        this.resultData = resultData;
    }

    public ResponseData(Result msg) {
        super(msg);
    }

    public ResponseData(String rspCode, String rspMsg) {
        super(rspCode, rspMsg);
    }

    public ResponseData(String rspCode, String rspMsg, T resultData) {
        super(rspCode, rspMsg);
        this.resultData = resultData;
    }

    public ResponseData(Result msg, T resultData) {
        super(msg);
        this.resultData = resultData;
    }

    public T getResultData() {
        return resultData;
    }

    public void setResultData(T resultData) {
        this.resultData = resultData;
    }

    @Override
    public String toString() {
        return "ResponseData{" +
                "data=" + resultData +
                "} " + super.toString();
    }
}
