package com.sunjianlin.sunspringboot.manager;

import com.sunjianlin.sunspringboot.entity.UserEntity;

/**
 * Created by sunjianlin
 * 2018年03月09日 16:38:04
 */
public interface IUserManager {

    void addUser(UserEntity userEntity);

    void deleteUser(Long id);

    int updateUser(UserEntity userEntity);

    UserEntity getById(Long id);

    UserEntity getByLoginName(String loginName);

}
