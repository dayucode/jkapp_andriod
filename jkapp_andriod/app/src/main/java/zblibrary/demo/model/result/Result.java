package zblibrary.demo.model.result;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Result<T> implements Serializable {

    private String code;
    private String message;
    private boolean success;
    private T data;


    //不带返回数据的成功
    public static <T> Result<T> success() {
        return new Result<T>(ResultEnum.SUCCESS.getCode(), true, ResultEnum.SUCCESS.getDesc());
    }

    //带返回数据的成功
    public static <T> Result<T> success(T t) {
        return new Result<T>(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getDesc(), true, t);
    }

    //带返回参数
    public static <T> Result<T> success(ResultEnum resultEnum, T t) {
        return new Result<T>(resultEnum.getCode(), resultEnum.getDesc(), true, t);
    }

    //不带具体错误信息的错误
    public static <T> Result<T> fail() {
        return new Result<T>(ResultEnum.FAIL.getCode(), false, ResultEnum.FAIL.getDesc());
    }

    //带返回信息的错误
    public static <T> Result<T> fail(ResultEnum resultEnum) {
        return new Result<T>(resultEnum.getCode(), false, resultEnum.getDesc());
    }

    //带数据的错误
    public static <T> Result<T> fail(String message) {
        return new Result<>(ResultEnum.FAIL.getCode(), message, false, null);
    }

    public static <T> Result<T> fail(String code, String message) {
        return new Result<>(code, message, false, null);
    }

    private Result(ResultEnum resultEnum) {
        this.code = resultEnum.getCode();
        this.message = resultEnum.getDesc();
    }

    private Result(String code, String desc, boolean success, T data) {
        this.code = code;
        this.message = desc;
        this.success = success;
        this.data = data;
    }

    private Result(String code, boolean success, String message) {
        this.code = code;
        this.message = message;
        this.success = success;
    }

    private Result(ResultEnum resultEnum, T data) {
        this.code = resultEnum.getCode();
        this.message = resultEnum.getDesc();
        this.data = data;
    }
}
