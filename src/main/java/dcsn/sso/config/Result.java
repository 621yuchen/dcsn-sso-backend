package dcsn.sso.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import dcsn.sso.config.exception.ResultConstants;
import lombok.Data;

/**
 * @author UASD-Zhangjiuwei
 * @className Result
 * @description  返回结果集
 * @date 2024/3/11
 */
@Data
public class Result {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String code;

    private Object data;

    public Result(String code, Object data) {
        this.code = code;
        this.data = data;
    }
    public Result(Object data) {
        this.data = data;
    }
    public static Object ok(Object data) {
        return data;
    }

    public static Result error(String code,Object data) {
        return new Result(code,data);
    }

    public static Result exception() {
        return new Result(ResultConstants.ERR999999,"未知异常，请联系管理员");
    }

}
