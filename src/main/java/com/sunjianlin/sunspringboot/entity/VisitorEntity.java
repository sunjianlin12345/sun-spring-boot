package com.sunjianlin.sunspringboot.entity;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by sunjianlin
 * 2018年05月10日 16:33:57
 */
public class VisitorEntity extends BaseEntity implements Serializable {

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

    /**
     * 访问者编号
     */
    private Date createDate;

    /**
     * 访问者编号
     */
    private Date updateDate;

    public void setVisitorCode(String visitorCode) {
        this.visitorCode = visitorCode;
    }

    public String getVisitorCode() {
        return visitorCode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

}
