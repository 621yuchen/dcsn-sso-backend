package dcsn.sso.entity.qiyu;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResultVo {


    @JsonProperty("error")
    private String error;
    @JsonProperty("result")
    private String result;
    @JsonProperty("message")
    private String message;
    @JsonProperty("data")
    private String data;
}
