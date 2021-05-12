package zblibrary.demo.enums;

import lombok.Getter;

@Getter
public enum HeartRateTypeEnum implements CodeEnum {
    LOW(0, "过低"),
    NORMAL(1, "正常"),
    DAMAGE(2, "过高");
    private Integer code;
    private String msg;

    HeartRateTypeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
