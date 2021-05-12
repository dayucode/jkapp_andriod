package zblibrary.demo.enums;

import lombok.Getter;

@Getter
public enum EyeStatusToastEnum implements CodeEnum {
    LOW(0, "您的视力良好，请继续保持"),
    NORMAL(1, "您的视力正常，请继续保持"),
    LIGHT(2, "轻度近视，请注意保护眼睛"),
    MODERATE(3, "中度近视，请注意保护眼睛"),
    SERIOUS(4, "重度近视，请注意保护眼睛");
    private Integer code;
    private String msg;

    EyeStatusToastEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
