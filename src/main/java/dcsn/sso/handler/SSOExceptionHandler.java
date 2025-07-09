package dcsn.sso.handler;


import dcsn.sso.config.Result;
import dcsn.sso.config.exception.SSOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author UASD-CUIHONGDA
 * @className ExceptionHandler
 * @description
 * @date 2024/3/13
 */
@Slf4j
@RestControllerAdvice
public class SSOExceptionHandler {

    @ExceptionHandler(SSOException.class)
    public Result handleSSOException(SSOException e) {
        return Result.error(e.getCode(),e.getMsg());
    }
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        e.printStackTrace();
        return Result.exception();
    }

}
