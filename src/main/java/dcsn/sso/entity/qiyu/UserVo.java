package dcsn.sso.entity.qiyu;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class UserVo {

    @JsonProperty("user_name")
    private String userName;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("title")
    private String title;
    @JsonProperty("u_region")
    private String region;
    @JsonProperty("department")
    private Map<String, String> department;
    @JsonProperty("departmentString")
    private String departmentString;
    @JsonProperty("company")
    private Map<String, String> companyUrl;
    @JsonProperty("manager")
    private Map<String, String> managerUrl;
    @JsonProperty("companyString")
    private String company;
    @JsonProperty("managerString")
    private String manager;
    @JsonProperty("email")
    private String email;
    @JsonProperty("location")
    private Map<String, String> location;
    @JsonProperty("locationString")
    private String locationString;
    @JsonProperty("country")
    private String country;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("mobile_phone")
    private String mobilePhone;
    @JsonProperty("vip")
    private String vip;
    @JsonProperty("sys_id")
    private String sys_id;
}
