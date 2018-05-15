package com.sunjianlin.sunspringboot.controller;

import com.sunjianlin.sunspringboot.common.Result;
import com.sunjianlin.sunspringboot.entity.UserEntity;
import com.sunjianlin.sunspringboot.entity.VisitorEntity;
import com.sunjianlin.sunspringboot.service.IVisitorService;
import com.sunjianlin.sunspringboot.service.requestDTO.VisitorDTO;
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
public class VisitorController extends AbstractController {

    @Autowired
    private IVisitorService visitorService;

    @RequestMapping(value = "/visit", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded;charset=UTF-8")
    public Result visit(@RequestBody @Valid VisitorDTO visitorDTO, BindingResult bindingResult, HttpServletRequest request) {
        String ip  = getRealIp(request);
        logger.info("用户IP："+ ip + "访问");
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

}
