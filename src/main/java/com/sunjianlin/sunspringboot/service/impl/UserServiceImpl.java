package com.sunjianlin.sunspringboot.service.impl;

import com.sunjianlin.sunspringboot.entity.UserEntity;
import com.sunjianlin.sunspringboot.manager.IUserManager;
import com.sunjianlin.sunspringboot.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by sunjianlin
 * 2018年03月09日 17:08:21
 */
@Service("userService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserManager userManager;

    @Override
    public void addUser(UserEntity userEntity) {
        userManager.addUser(userEntity);
    }

    @Override
    public boolean deleteUser(Long id) {
        boolean result = false;
//        if() {
//            userManager.deleteUser(id);
//            result = true;
//        }
        return result;
    }

    @Override
    public int updateUser(UserEntity userEntity) {
        return userManager.updateUser(userEntity);
    }

    @Override
    public UserEntity getById(Long id) {
        return userManager.getById(id);
    }

    @Override
    public UserEntity getByLoginName(String loginName) {
        return userManager.getByLoginName(loginName);
    }
}
