package com.sunjianlin.springboot.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by sunjianlin
 * 2018年03月09日 15:36:01
 */
@Data
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 所有Entity都必须具有Long类型的主键
     */
    private Long id;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 修改时间
     */
    private Date updateDate;

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Date getCreateDate() {
//        return createDate;
//    }
//
////    public void setCreateDate(Date createDate) {
////        this.createDate = createDate;
////    }
//
//    public Date getUpdateDate() {
//        return updateDate;
//    }
//
//    public void setUpdateDate(Date updateDate) {
//        this.updateDate = updateDate;
//    }
//
//    public int hashCode() {
//        return id == null ? System.identityHashCode(this) : id.hashCode();
//    }
//
//    public boolean equals(Object obj) {
//        if (this == obj) {
//            return true;
//        }
//        if (obj == null) {
//            return false;
//        }
//        if (getClass().getPackage() != obj.getClass().getPackage()) {
//            return false;
//        }
//        if (BaseEntity.class.isAssignableFrom((obj.getClass()))) {
//            final BaseEntity other = (BaseEntity) obj;
//            if (id == null) {
//                if (other.getId() != null) {
//                    return false;
//                }
//            } else if (!id.equals(other.getId())) {
//                return false;
//            }
//            return true;
//        }
//        return false;
//    }
//
//    public int compareTo(BaseEntity o) {
//        if (null == o) {
//            return 0;
//        }
//        return (o.equals(this) ? 0 : 1);
//    }
}
