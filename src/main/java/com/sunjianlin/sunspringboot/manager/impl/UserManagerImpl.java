package com.sunjianlin.sunspringboot.manager.impl;

import com.sunjianlin.sunspringboot.common.entity.QueryParams;
import com.sunjianlin.sunspringboot.dao.UserMapper;
import com.sunjianlin.sunspringboot.entity.UserEntity;
import com.sunjianlin.sunspringboot.manager.IUserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by sunjianlin
 * 2018年03月09日 16:43:48
 */
@Component("userManagerImpl")
public class UserManagerImpl implements IUserManager {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void addUser(UserEntity userEntity) {
        userMapper.insert(userEntity);
    }

    @Override
    public void deleteUser(Long id) {
        userMapper.deleteById(id);
    }

    @Override
    public int updateUser(UserEntity userEntity) {
        return userMapper.update(userEntity);
    }

    @Override
    public UserEntity getById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public UserEntity getByLoginName(String loginName) {
        QueryParams queryParams = new QueryParams();
        queryParams.put("loginName", loginName);
        List<UserEntity> list  = userMapper.selectByProps(queryParams);
        return list.size() == 0 ? new UserEntity() : list.get(0);
    }
}
