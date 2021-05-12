package zblibrary.demo.model.result;

import lombok.Getter;

@Getter
public enum ResultEnum {

    SUCCESS("5200", "成功"),
    FAIL("5000", "系统访问异常"),
    AUTH_ERROR("5401", "抱歉，您没有操作权限"),
    SERVER_ERROR("5500", "系统服务异常"),
    BUSINESS_ERROR("5700", "业务处理异常"),
    LOGIN_SESSION_MISS("5001", "会话失效"),
    LOGIN_SUCCESS("5200", "登录成功"),
    LOGIN_FAIL("5501", "登录失败"),
    LOGINOUT_FAIL("5503", "注销失败"),
    LOGIN_USER_ERR("5502", "用户名或密码错误"),
    LOGIN_USER_locked("55021", "用户帐号被锁定"),
    LOGIN_USER_disabled("55022", "用户已禁用"),
    LOGIN_USER_credentialsexpired("55023", "用户凭据已过期"),
    LOGIN_USER_expired("55024", "用户帐户已过期"),
    LOGOUT_FAIL("5503", "注销失败"),
    TOKEN_VALID_ERROR("5504", "令牌校验失败"),
    TOKEN_MISS("5505", "令牌缺失"),
    DATA_MISS("5506", "数据为空"),
    SYSTEM_ERROR("-1", "系统异常"),
    SYSTEM_BUSY("000001", "系统繁忙,请稍候再试"),
    SYSTEM_NO_PERMISSION("000002", "无权限"),
    GATEWAY_NOT_FOUND_SERVICE("010404", "服务未找到"),
    GATEWAY_ERROR("010500", "网关异常"),
    GATEWAY_CONNECT_TIME_OUT("010002", "网关超时"),
    ARGUMENT_NOT_VALID("020000", "请求参数校验不通过"),
    UPLOAD_FILE_SIZE_LIMIT("020001", "上传文件大小超过限制"),

    HTTP_CLIENT_ERROR("5600", "服务调用异常"),
    HTTP_STATUS_ERROR("5601", "服务调用异常"),
    FEIGN_ERROR("5602", "feign调用异常"),
    ZUUL_STATUS_ERROR("5800", "服务调用太频繁，请稍后重试"),

    BUSINESS_TIME_OUT("5521", "业务处理超时");

    ResultEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private String code;
    private String desc;

    public String getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }

}
