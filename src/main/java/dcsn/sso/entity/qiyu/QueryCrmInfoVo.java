package dcsn.sso.entity.qiyu;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class QueryCrmInfoVo {

    private String email;
    private String tags;
}
