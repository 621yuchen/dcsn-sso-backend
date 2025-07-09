package dcsn.sso.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import dcsn.sso.entity.qiyu.*;
import dcsn.sso.util.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author UASD-xiong.yb
 */
@Slf4j
@Service
public class QiyuService {
    @Value("${qiyu.userProfile}")
    private String userProfile;
    @Value("${qiyu.queryCrmInfo}")
    private String queryCrmInfo;
    @Value("${qiyu.tagAllUrl}")
    private String tagAllUrl;

    @Value("${qiyu.incidentAddUrl}")
    private String incidentAddUrl;

    @Value("${qiyu.incidentUrl}")
    private String incidentUrl;
    @Value("${qiyu.sysUserUrl}")
    private String sysUserUrl;
    @Value("${qiyu.serviceOfferingUrl}")
    private String serviceOfferingUrl;
    @Value("${qiyu.groupUrl}")
    private String groupUrl;
    @Value("${qiyu.toUrl}")
    private String toUrl;
    @Value("${qiyu.userName}")
    private String userName;
    @Value("${qiyu.password}")
    private String password;


    public Object getIncident(String callerId) {
        String url = incidentUrl.replace("{caller_id}",callerId);
        Map<String, Object> headerMap = this.addAuthorization(null);
        String str = HttpClientUtil.doGet(url, headerMap);
        ResultVo resultVo = JSON.parseObject(str, ResultVo.class);
        if (resultVo.getError() == null) {
            String result = resultVo.getResult();
            List<IncidentVo> incidentVoList = JSON.parseArray(result, IncidentVo.class);
            for (int i = 0; i <incidentVoList.size(); i++) {
                IncidentVo incidentVo = incidentVoList.get(i);
                incidentVo.setId(i+1);
//                incidentVo.setCaller(this.getName(incidentVo.getCallerId().get("link"), headerMap));
//                incidentVo.setOnBehalfOf(this.getName(incidentVo.getUOnBehalfOf().get("link"), headerMap));
//                incidentVo.setAssignmentGroupString(this.getName(incidentVo.getAssignmentGroup().get("link"), headerMap));
//                incidentVo.setOpenedByString(this.getName(incidentVo.getOpenedBy().get("link"), headerMap));
                if (incidentVo.getCallerId() != null && incidentVo.getCallerId().get("display_value") != null) {
                    incidentVo.setCaller(incidentVo.getCallerId().get("display_value"));
                }
                if (incidentVo.getUOnBehalfOf() != null && incidentVo.getUOnBehalfOf().get("display_value") != null) {
                    incidentVo.setOnBehalfOf(incidentVo.getUOnBehalfOf().get("display_value"));
                }
                if(incidentVo.getAssignmentGroup()!=null){
                    incidentVo.setAssignmentGroupString(incidentVo.getAssignmentGroup().get("display_value"));
                }
                if (incidentVo.getOpenedBy() != null && incidentVo.getOpenedBy().get("display_value") != null) {
                    incidentVo.setOpenedByString(incidentVo.getOpenedBy().get("display_value"));
                }
                if (incidentVo.getAssignedTo() != null && incidentVo.getAssignedTo().get("display_value") != null) {
                    incidentVo.setAssignedToString(incidentVo.getAssignedTo().get("display_value"));
                }
            }
            //根据时间排序
            incidentVoList.sort(Comparator.comparing(IncidentVo::getSysCreatedOn).reversed());
            return AjaxResult.success(incidentVoList);
        } else {
            return AjaxResult.error();
        }
    }

    public Object getSysUser(@PathVariable String email) {

        String url = sysUserUrl + email;
        Map<String, Object> headerMap = this.addAuthorization(null);
        String str = HttpClientUtil.doGet(url, headerMap);
        ResultVo resultVo = JSON.parseObject(str, ResultVo.class);
        if (resultVo.getError() == null) {
            String result = resultVo.getResult();
            List<UserVo> list = JSON.parseArray(result, UserVo.class);
            if(list ==null||list.size() == 0){

                return AjaxResult.success();
            }
            UserVo userVo = list.get(0);
//            String company = this.getName(userVo.getCompanyUrl().get("link"), headerMap);
//            String manager = this.getName(userVo.getManagerUrl().get("link"), headerMap);
            if (userVo.getCompanyUrl() != null && !userVo.getCompanyUrl().isEmpty()) {
                String company = userVo.getCompanyUrl().get("display_value");
                userVo.setCompany(company);
            }
            if (userVo.getManagerUrl() != null && !userVo.getManagerUrl().isEmpty()) {
                String manager = userVo.getManagerUrl().get("display_value");
                userVo.setManager(manager);
            }
            if (userVo.getDepartment() != null && !userVo.getDepartment().isEmpty()) {
                String department = userVo.getDepartment().get("display_value");
                userVo.setDepartmentString(department);
            }
            if (userVo.getLocation() != null && !userVo.getLocation().isEmpty()) {
                String location = userVo.getLocation().get("display_value");
                userVo.setLocationString(location);
            }
            return AjaxResult.success(userVo);
        } else {
            return AjaxResult.error();
        }
    }

    private Map<String, Object> addAuthorization(Map<String, Object> headerMap) {
        if (headerMap == null) {
            headerMap = new HashMap<>();
        }
        // 创建 Basic Auth 头
        String auth = userName + ":" + password;
        byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
        String authHeader = "Basic " + new String(encodedAuth);
        headerMap.put("Authorization", authHeader);
        return headerMap;
    }

    private String getName(String link, Map<String, Object> headerMap) {
        try {
            String str = HttpClientUtil.doGet(link, headerMap);
            Map<String, Map<String, String>> str2Map = JSON.parseObject(str, Map.class);
            return str2Map.get("result").get("name");
        } catch (Exception e) {
            log.error(e.getMessage());
            return "";
        }
    }


    public Object getIncidentInfo(IncidentVo incidentVo) {
        Map<String, Object> headerMap = this.addAuthorization(null);
        incidentVo.setCaller(this.getName(incidentVo.getCallerId().get("link"), headerMap));
        incidentVo.setOnBehalfOf(this.getName(incidentVo.getUOnBehalfOf().get("link"), headerMap));
        incidentVo.setAssignmentGroupString(this.getName(incidentVo.getAssignmentGroup().get("link"), headerMap));
        incidentVo.setOpenedByString(this.getName(incidentVo.getOpenedBy().get("link"), headerMap));
        return AjaxResult.success(incidentVo);
    }


        public static void main(String[] args) {
        String input = "{\"foreignId\": \"woYKOBDwAAnyxcJzND7AgA19_sbPFKdg\"}";
        String md5 = md5Encrypt(input).toLowerCase();
        long currentTimestamp = Instant.now().getEpochSecond();

        String aa = encode("454F80B0B91B4B3CB9EB1F6F341B9987", md5, currentTimestamp);
        System.out.println(currentTimestamp);
        System.out.println(aa);
    }
    // Compute MD5 hash for a JSON string
    public static String md5Encrypt(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString().toLowerCase();
        } catch (Exception e) {
            throw new RuntimeException("Error while encrypting with MD5", e);
        }
    }
    public static String encode(String appSecret, String md5, long time) {
        String content = appSecret + md5 + time;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("sha1");
            messageDigest.update(content.getBytes());
            return getFormattedText(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static String getFormattedText(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        for (int j = 0; j < len; j++) {
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }

    public Object getGroup() {
        String url = groupUrl;
        Map<String, Object> headerMap = this.addAuthorization(null);
        String str = HttpClientUtil.doGet(url, headerMap);
        ResultVo resultVo = JSON.parseObject(str, ResultVo.class);
        if (resultVo.getError() == null) {
            String result = resultVo.getResult();
            List<Map> list = JSON.parseArray(result, Map.class);
            return AjaxResult.success(list);
        } else {
            return AjaxResult.error();
        }
    }
    public Object getTo( String sysId) {
        String url = toUrl.replace("{sysId}",sysId);
        Map<String, Object> headerMap = this.addAuthorization(null);
        String str = HttpClientUtil.doGet(url, headerMap);
        ResultVo resultVo = JSON.parseObject(str, ResultVo.class);
        if (resultVo.getError() == null) {
            String result = resultVo.getResult();
            List<Map> list = JSON.parseArray(result, Map.class);
            return AjaxResult.success(list);
        } else {
            return AjaxResult.error();
        }
    }

    public Object getUserProfile(String sessionId) {
        try{
            JSONObject json =new JSONObject();
            json.put("sessionId",sessionId);
            String md5 = md5Encrypt(json.toString()).toLowerCase();
            long time = Instant.now().getEpochSecond();
            String checksum = encode("454F80B0B91B4B3CB9EB1F6F341B9987", md5, time);
            System.out.println(time);
            System.out.println(checksum);

            String url = userProfile.replace("{time}",time+"").replace("{checksum}",checksum);
            String str = HttpClientUtil.doPost(url, json.toString());
            ResultVo resultVo = JSON.parseObject(str, ResultVo.class);
            if (resultVo.getError() == null) {
                String result = resultVo.getMessage();
                JSONObject jsonObject = JSONObject.parseObject(result);
                String email = jsonObject.getString("email");
                return AjaxResult.success(email);
            } else {
                return AjaxResult.error();
            }
        }catch (Exception e){
            return AjaxResult.success("");
        }

    }

    public void addIncident(JSONObject json) {
        String url = incidentAddUrl;
        Map<String, Object> headerMap = this.addAuthorization(null);
        String str = HttpClientUtil.doPost(url, json.toString(), headerMap);
    }

    public Object getQueryCrmInfo(String userId) {
        try{
            JSONObject json =new JSONObject();
            json.put("foreignId",userId);
            String md5 = md5Encrypt(json.toString()).toLowerCase();
            long time = Instant.now().getEpochSecond();
            String checksum = encode("454F80B0B91B4B3CB9EB1F6F341B9987", md5, time);
            System.out.println(time);
            System.out.println(checksum);

            String url = queryCrmInfo.replace("{time}",time+"").replace("{checksum}",checksum);
            String str = HttpClientUtil.doPost(url, json.toString());
            ResultVo resultVo = JSON.parseObject(str, ResultVo.class);
            if (resultVo.getError() == null) {
                String result = resultVo.getData();
                JSONObject jsonObject = JSONObject.parseObject(result);
                String email = jsonObject.getString("email");
                String tags = jsonObject.getString("tags");
                QueryCrmInfoVo vo= new QueryCrmInfoVo();
                vo.setEmail(email);
                List<Map<String, String>> tagAllList = getTagAll();

                List<Integer> tagIds = JSONArray.parseArray(tags, Integer.class);
                // Create a mapping of id to name
                Map<Integer, String> idToNameMap = tagAllList.stream()
                        .collect(Collectors.toMap(
                                tag -> Integer.parseInt(String.valueOf(tag.get("id"))), // Convert "id" to String and parse as Integer
                                tag -> tag.get("name") // Directly retrieve the "name" value
                        ));

                // Replace tags with names
                List<String> tagNames = tagIds.stream()
                        .map(idToNameMap::get)
                        .toList();



                vo.setTags(JSONObject.toJSONString(tagNames));
                return AjaxResult.success(vo);
            } else {
                return AjaxResult.error();
            }
        }catch (Exception e){
            return AjaxResult.success("");
        }

    }

    public List<Map<String ,String>> getTagAll() {
        try{
            JSONObject json =new JSONObject();
            json.put("","");
            String md5 = md5Encrypt(json.toString()).toLowerCase();
            long time = Instant.now().getEpochSecond();
            String checksum = encode("454F80B0B91B4B3CB9EB1F6F341B9987", md5, time);
            System.out.println(time);
            System.out.println(checksum);

            String url = tagAllUrl.replace("{time}",time+"").replace("{checksum}",checksum);
            String str = HttpClientUtil.doPost(url, json.toString());
            ResultVo resultVo = JSON.parseObject(str, ResultVo.class);
            if (resultVo.getError() == null) {
                String result = resultVo.getResult();
                List<Map<String ,String>> list = JSONArray.parseObject(result, List.class);
                return list;
            } else {
                return null;
            }
        }catch (Exception e){
            return null;
        }

    }



    public Object serviceOffering() {
        String url = serviceOfferingUrl;
        Map<String, Object> headerMap = this.addAuthorization(null);
        String str = HttpClientUtil.doGet(url, headerMap);
        ResultVo resultVo = JSON.parseObject(str, ResultVo.class);
        if (resultVo.getError() == null) {
            String result = resultVo.getResult();
            List<Map> list = JSON.parseArray(result, Map.class);
            return AjaxResult.success(list);
        } else {
            return AjaxResult.error();
        }
    }
}
