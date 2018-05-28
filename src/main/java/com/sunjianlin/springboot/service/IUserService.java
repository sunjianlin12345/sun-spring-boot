package com.sunjianlin.springboot.service;

import com.sunjianlin.springboot.entity.UserEntity;

/**
 * Created by sunjianlin
 * 2018年03月09日 17:05:32
 */
public interface IUserService {

    void addUser(UserEntity userEntity);

    boolean deleteUser(Long id);

    int updateUser(UserEntity userEntity);

    UserEntity getById(Long id);

    UserEntity getByLoginName(String loginName);

}
