package com.sunjianlin.springboot.dao;

import com.sunjianlin.springboot.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by sunjianlin
 * 2018年03月09日 16:36:29
 */
@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {


}
