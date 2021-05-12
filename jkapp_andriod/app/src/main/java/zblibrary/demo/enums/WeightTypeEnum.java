package zblibrary.demo.enums;

import lombok.Getter;

/**
 * @Author : daYuCode
 * @Mail : dayucode@foxmail.com
 * @Create : 2019/2/23 16:03 星期六
 * @Description :
 */
@Getter
public enum WeightTypeEnum implements CodeEnum {

    LOW(0, "偏轻"),

    NORMAL(1, "健康"),

    HIGH(2, "超重"),

    SERIOUS(3, "肥胖");
    private Integer code;
    private String msg;

    WeightTypeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
