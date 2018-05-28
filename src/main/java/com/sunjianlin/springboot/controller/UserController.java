package com.sunjianlin.springboot.controller;

import com.sunjianlin.springboot.entity.UserEntity;
import com.sunjianlin.springboot.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by sunjianlin
 * 2018年03月09日 17:10:09
 */
@RestController
@RequestMapping("/user")
public class UserController extends AbstractController {

    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public boolean addUser( UserEntity userEntity) {
        System.out.println("开始新增...");
        userService.addUser(userEntity);
        return userService.getByLoginName(userEntity.getLoginName()) != null ? true : false;
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.PUT)
    public int updateUser( UserEntity userEntity) {
        System.out.println("开始更新...");
        return userService.updateUser(userEntity);
    }

    @RequestMapping(value = "/deleteUser", method = RequestMethod.DELETE)
    public boolean delete(@RequestParam(value = "userId", required = true) long userId) {
        System.out.println("开始删除...");
        return userService.deleteUser(userId);
    }

    @RequestMapping(value = "/getByLoginName", method = RequestMethod.GET)
    public UserEntity getByLoginName(@RequestParam(value = "loginName", required = true) String loginName) {
        System.out.println("开始查询...");
        UserEntity userEntity = userService.getByLoginName(loginName);
        return  userEntity;
    }

}
