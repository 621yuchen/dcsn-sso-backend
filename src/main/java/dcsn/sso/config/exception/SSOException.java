package dcsn.sso.config.exception;

/**
 * @author UASD-Zhangjiuwei
 * @className SSOException
 * @description
 * @date 2024/3/13
 */
public class SSOException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private String msg;

    private String code = ResultConstants.ERR999999;

    public SSOException(String code, String msg) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public SSOException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public SSOException(String code, String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {

        this.msg = msg;
    }

    public String getCode() {

        return code;
    }

    public void setCode(String code) {

        this.code = code;
    }

}
