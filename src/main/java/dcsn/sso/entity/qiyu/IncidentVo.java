package dcsn.sso.entity.qiyu;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class IncidentVo {

    private int id;

    @JsonProperty("number")
    private String number;

    @JsonProperty("caller_id")
    private Map<String, String> callerId;

    @JsonProperty("caller")
    private String caller;

    @JsonProperty("u_on_behalf_of")
    private Map<String, String> uOnBehalfOf;

    @JsonProperty("on_behalf_of")
    private String onBehalfOf;

    @JsonProperty("email")
    private String email;

    @JsonProperty("impact")
    private String impact;

    @JsonProperty("urgency")
    private String urgency;

    @JsonProperty("priority")
    private String priority;

    @JsonProperty("opened_at")
    private String openedAt;
    @JsonProperty("opened_by")
    private Map<String, String> openedBy;
    @JsonProperty("opened_by_string")
    private String openedByString;
    @JsonProperty("channel")
    private String channel;
    @JsonProperty("state")
    private String state;
    @JsonProperty("assignment_group")
    private Map<String, String> assignmentGroup;
    @JsonProperty("assignment_group_string")
    private String assignmentGroupString;
    @JsonProperty("assigned_to")
    private Map<String, String> assignedTo;
    @JsonProperty("assigned_to_string")
    private String assignedToString;
    @JsonProperty("short_description")
    private String shortDescription;
    @JsonProperty("description")
    private String description;
    @JsonProperty("sys_created_on")
    private String sysCreatedOn;
    @JsonProperty("contact_type")
    private String contactType;
    @JsonProperty("sys_id")
    private String sysId;
}
