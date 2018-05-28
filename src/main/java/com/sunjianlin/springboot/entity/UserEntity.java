package com.sunjianlin.springboot.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Created by sunjianlin
 * 2018年03月09日 15:44:30
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class UserEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户编号
     */
    private String userCode;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 登录名
     */
    private String loginName;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机
     */
    private String phone;

    /**
     * 用户状态
     */
    private byte status;

    /**
     * 备注
     */
    private String mark;

}
