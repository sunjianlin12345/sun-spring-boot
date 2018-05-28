package com.sunjianlin.springboot.common;

/**
 * 页面响应结果集
 * Created by sunjianlin
 * 2018年03月09日 16:11:06
 */
public class Result {

    private static final long serialVersionUID = 1L;

    /**
     * 响应状态值：成功 200 失败 -1
     */
    private Integer code = 200;

    /**
     * 是否成功
     */
    private boolean success = true;

    /**
     * 响应信息值
     */
    private String msg = "success";

    /**
     * 结果集
     */
    private Object data = null;

    /**
     * 页码
     */
    private Integer pageSize = 0;
    /**
     * 当前页码
     */
    private Integer pageIndex = 0;
    /**
     * 总数
     */
    private Long totalCount = 0L;
    /**
     * 总页数
     */
    private Long totalPageSize = 0L;

    public Result() {

    }

    public Result(Integer code, boolean success, String msg) {
        this.code = code;
        this.success = success;
        this.msg = msg;
    }

    public static Result error() {
        return error(-1, "response is error");
    }

    public static Result error(String msg) {
        return error(-1, msg);
    }

    public static Result error(Integer code, String msg) {
        return new Result(code, false, msg);
    }

    public static Result success() {
        return new Result();
    }

    public Integer getCode() {
        return code;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public Long getTotalPageSize() {
        return totalPageSize;
    }

    public void setTotalPageSize(Long totalPageSize) {
        this.totalPageSize = totalPageSize;
    }
}
