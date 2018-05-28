package com.sunjianlin.springboot.service.requestDTO;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * Created by sunjianlin
 * 2018年05月11日 16:17:30
 */
public class VisitorDTO {

    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空")
    @Length(min=2,max=10,message="姓名长度不能小于2位")
    private String name;

    /**
     * 邮箱
     */
    @NotBlank(message = "邮箱不能为空")
    @Email
    private String email;

    /**
     * 手机
     */
    @NotBlank(message = "手机不能为空")
    @Pattern(regexp="^[1][3|4|5|7|8][0-9]{9}$", message="手机格式错误")
    private String phone;

    /**
     * 留言
     */
    private String message;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
