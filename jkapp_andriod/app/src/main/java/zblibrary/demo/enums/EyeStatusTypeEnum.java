package zblibrary.demo.enums;

import lombok.Getter;

@Getter
public enum EyeStatusTypeEnum implements CodeEnum {
    LOW(0, "良好"),
    NORMAL(1, "正常"),
    LIGHT(2, "轻度"),
    MODERATE(3, "中度"),
    SERIOUS(4, "重度");
    private Integer code;
    private String msg;

    EyeStatusTypeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
