package zblibrary.demo.enums;

import lombok.Getter;

/**
 * @Author : daYuCode
 * @Mail : dayucode@foxmail.com
 * @Create : 2019/2/23 16:03 星期六
 * @Description :
 */
@Getter
public enum SugarTypeEnum implements CodeEnum {
    LOW(0, "偏低"),

    NORMAL(1, "正常"),

    HIGH(2, "偏高"),

    SERIOUS(3, "重度");
    private Integer code;
    private String msg;

    SugarTypeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
