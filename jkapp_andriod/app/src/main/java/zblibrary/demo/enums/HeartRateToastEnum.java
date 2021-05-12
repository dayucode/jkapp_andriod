package zblibrary.demo.enums;

import lombok.Getter;

@Getter
public enum HeartRateToastEnum implements CodeEnum {
    LOW(0, "您的血压偏低，建议均衡饮食，加强锻炼，改善体质，并且密切关注血压情况"),
    NORMAL(1, "您的血压正常，请继续保持健康的生活方式，并且定期测量血压"),
    DAMAGE(2, "您的血压偏高，建议均衡饮食，加强锻炼，改善体质，并且密切关注血压情况");
    private Integer code;
    private String msg;

    HeartRateToastEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
