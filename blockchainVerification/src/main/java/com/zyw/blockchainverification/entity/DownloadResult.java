package com.zyw.blockchainverification.entity;

import lombok.Getter;
import lombok.Setter;

//@Getter
//@Setter
public class DownloadResult<T> {
    private int code;    // 状态码（例如200成功，500异常）
    private String msg;  // 提示信息
    private T data;      // 携带的数据（可以是对象、集合等）

    // 成功静态方法（带数据）
    public static <T> DownloadResult<T> success(T data) {
        return new DownloadResult<>(200, "操作成功", data);
    }

    // 成功静态方法（不带数据）
    public static DownloadResult<?> success() {
        return success(null);
    }

    // 错误静态方法
    public static DownloadResult<?> error(int code, String msg) {
        return new DownloadResult<>(code, msg, null);
    }

    // 构造方法
    public DownloadResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    // Getter和Setter（必须有，否则Spring无法序列化）
    public int getCode() { return code; }
    public void setCode(int code) { this.code = code; }
    public String getMsg() { return msg; }
    public void setMsg(String msg) { this.msg = msg; }
    public T getData() { return data; }
    public void setData(T data) { this.data = data; }


}
