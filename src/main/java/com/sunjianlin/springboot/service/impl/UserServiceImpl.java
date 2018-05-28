package com.sunjianlin.springboot.service.impl;

import com.sunjianlin.springboot.entity.UserEntity;
import com.sunjianlin.springboot.common.QueryParams;
import com.sunjianlin.springboot.dao.UserMapper;
import com.sunjianlin.springboot.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by sunjianlin
 * 2018年03月09日 17:08:21
 */
@Service("userService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void addUser(UserEntity userEntity) {
        userMapper.insert(userEntity);
    }

    @Override
    public boolean deleteUser(Long id) {
        boolean result = false;
//        if() {
//            userMapper.deleteById(id);
//            result = true;
//        }
        return result;
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
        return list.size() == 0 ? null : list.get(0);
    }
}
