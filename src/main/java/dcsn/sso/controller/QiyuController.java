package dcsn.sso.controller;


import com.alibaba.fastjson.JSONObject;
import dcsn.sso.entity.qiyu.AjaxResult;
import dcsn.sso.entity.qiyu.IncidentVo;
import dcsn.sso.service.QiyuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author qiyu
 */
@RestController
@RequestMapping("/qiyu")
public class QiyuController {
    @Autowired
    private QiyuService qiyuService;


    @GetMapping("/userProfile/{session}")
    public Object getUserProfile(@PathVariable String session) {
        return qiyuService.getUserProfile(session);
    }
    @GetMapping("/queryCrmInfo/{userId}")
    public Object getQueryCrmInfo(@PathVariable String userId) {
        return qiyuService.getQueryCrmInfo(userId);
    }


    @GetMapping("/incident/{callerId}")
    public Object getIncident(@PathVariable String callerId) {
        return qiyuService.getIncident(callerId);
    }
    @PostMapping("/incidentInfo")
    public Object getIncidentInfo(@RequestBody IncidentVo incidentVo) {
        return qiyuService.getIncidentInfo(incidentVo);
    }

    @GetMapping("/getSysUser/{email}")
    public Object getSysUser(@PathVariable String email) {
        return qiyuService.getSysUser(email);
    }

    @GetMapping("/getGroup")
    public Object getGroup() {
        return qiyuService.getGroup();
    }


    @GetMapping("/serviceOffering")
    public Object serviceOffering() {
        return qiyuService.serviceOffering();
    }

    @GetMapping("/getTO/{sysId}")
    public Object getTo(@PathVariable String sysId) {
        return qiyuService.getTo(sysId);
    }

    @PostMapping("/incident")
    public Object addIncident(@RequestBody JSONObject json) {
        qiyuService.addIncident(json);
        return AjaxResult.success();
    }
}
