package zblibrary.demo.enums;

import lombok.Getter;

/**
 * @Author : 涂元坤
 * @Mail : 766564616@qq.com
 * @Create : 2019/2/23 16:03 星期六
 * @Description :
 */
@Getter
public enum PressureToastEnum implements CodeEnum {
    LOW(0, "您的血压偏低，请采取健康的生活方式，戒烟限酒，加强锻炼，密切关注血压情况。"),
    NORMAL(1, "您的血压正常，请继续保持健康的生活方式，并且定期测量血压"),
    HIGH(2, "您的血压偏高，请采取健康的生活方式，戒烟限酒，加强锻炼，密切关注血压情况"),
    LIGHT(3, "您的血压轻度偏高，请采取健康的生活方式，戒烟限酒，加强锻炼，密切关注血压情况"),
    MODERATE(4, "您的血压中度偏高，请采取健康的生活方式，戒烟限酒，加强锻炼，密切关注血压情况"),
    SERIOUS(5, "您的血压重度偏高，请采取健康的生活方式，戒烟限酒，加强锻炼，密切关注血压情况");
    private Integer code;
    private String msg;

    PressureToastEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
