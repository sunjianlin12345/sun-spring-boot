package com.sunjianlin.springboot.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by sunjianlin
 * 2018年05月28日 16:12:30
 */
@Data
@EqualsAndHashCode(callSuper=true)//让其生成的方法中调用父类的方法
public class VisitorEntity extends BaseEntity implements Serializable{

private static final long serialVersionUID = 1L;

    /**
      * 访问者编号
      */
    private String visitorCode;

    /**
      * 姓名
      */
    private String name;

    /**
      * 邮箱
      */
    private String email;

    /**
      * 手机
      */
    private String phone;

    /**
      * 留言
      */
    private String message;

    /**
      * ip地址
      */
    private String ipAddr;

}