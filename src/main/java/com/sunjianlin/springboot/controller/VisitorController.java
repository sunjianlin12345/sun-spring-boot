package com.sunjianlin.springboot.controller;


import com.sunjianlin.springboot.common.Result;
import com.sunjianlin.springboot.entity.VisitorEntity;
import com.sunjianlin.springboot.service.IVisitorService;
import com.sunjianlin.springboot.service.requestDTO.VisitorDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

/**
 * Created by sunjianlin
 * 2018年05月04日 18:12:06
 */
@RestController
@RequestMapping("/visitor")
@Slf4j
public class VisitorController extends AbstractController {

    @Autowired
    private IVisitorService visitorService;

    @RequestMapping(value = "/visit", method = RequestMethod.POST)
    public Result visit(@RequestBody @Valid VisitorDTO visitorDTO, BindingResult bindingResult, HttpServletRequest request) {
        String ip  = this.getRealIp(request);
        log.info("用户IP："+ ip + "访问");
        if(bindingResult.hasErrors()) {
            return  Result.error(bindingResult.getFieldError().getDefaultMessage());
        }
        VisitorEntity visitor = new VisitorEntity();
        visitor.setName(visitorDTO.getName());
        visitor.setPhone(visitorDTO.getPhone());
        visitor.setEmail(visitorDTO.getEmail());
        visitor.setMessage(visitorDTO.getMessage());
        if(visitorService.add(visitor) != 1) {
            return Result.error("提交信息失败！");
        }

        return  Result.success();
    }

    private String getRealIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if(index != -1){
                return ip.substring(0,index);
            }else{
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
            return ip;
        }
        return request.getRemoteAddr();
    }

}
