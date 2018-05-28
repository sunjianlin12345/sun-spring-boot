package com.sunjianlin.springboot.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunjianlin
 * 2018年03月09日 16:07:51
 */
public class Page<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private static int DEFAULT_PAGE_SIZE = 20;	// 默认每页记录数为20条
    private long pageSize = DEFAULT_PAGE_SIZE; 	// 每页的记录数
    private long start; 						// 当前页第一条数据在数据集中的索引位置,从0开始
    private List<T> data; 						// 当前页中存放的记录,类型一般为List
    private long totalCount; 					// 总记录数

    /**
     * 默认构造方法
     * 只构造空页.
     */
    public Page() {
        this(0, 0, DEFAULT_PAGE_SIZE, new ArrayList<T>());
    }

    /**
     * 通过参数构造分页对象
     *
     * @param start 本页数据在数据库中的起始位置
     * @param totalSize 数据库中总记录条数
     * @param pageSize 本页容量
     * @param data 本页包含的数据
     */
    public Page(long start, long totalSize, long pageSize, List<T> data) {
        this.pageSize = pageSize;
        this.start = start;
        this.totalCount = totalSize;
        this.data = data;
    }

    /**
     * 取总记录数
     *
     * @return 返回总记录数
     */
    public long getTotalCount() {
        return this.totalCount;
    }

    /**
     * 取总页数
     *
     * @return 返回总页数
     */
    public long getTotalPageCount() {
        if (totalCount % pageSize == 0)
            return totalCount / pageSize;
        else
            return totalCount / pageSize + 1;
    }

    /**
     * 取每页数据容量
     *
     * @return 返回每页数据容量
     */
    public long getPageSize() {
        return pageSize;
    }

    /**
     * 取当前页中的记录
     *
     * @return 返回当前页的记录数据
     */
    public List<T> getData() {
        return data;
    }

    /**
     * 设置分页数据
     * @param data
     */
    public void setData(List<T> data) {
        this.data = data;
    }

    /**
     * 取当前页码
     * 页码从1开始.
     *
     * @return 返回当前页码
     */
    public long getCurrentPageNo() {
        return start / pageSize + 1;
    }

    /**
     * 该页是否有下一页
     *
     * @return 返回是否有下一页
     */
    public boolean isHasNextPage() {
        return this.getCurrentPageNo() < this.getTotalPageCount();
    }

    /**
     * 该页是否有上一页
     *
     * @return 返回是否有上一页
     */
    public boolean isHasPreviousPage() {
        return this.getCurrentPageNo() > 1;
    }

    /**
     * 获取任一页第一条数据在数据集的位置
     * 每页条数使用默认值
     *
     * @return 返回任一页第一条数据在数据集的位置
     * @see #getStartOfPage(int,int)
     */
    protected static int getStartOfPage(int pageNo) {
        return getStartOfPage(pageNo, DEFAULT_PAGE_SIZE);
    }

    /**
     * 获取任一页第一条数据在数据集的位置
     *
     * @param pageNo 从1开始的页号
     * @param pageSize 每页记录条数
     * @return 该页第一条数据
     */
    public static int getStartOfPage(int pageNo, int pageSize) {
        return (pageNo - 1) * pageSize;
    }

    public long getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * 是否存在数据
     * @return
     */
    public boolean hasData() {
        if (null != this.getData() && this.getData().size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取当前页数据Size
     * @return
     */
    public int getDataSizeOfCurrentPage() {
        if (null != this.getData() && this.getData().size() > 0) {
            return this.getData().size();
        } else {
            return 0;
        }
    }

}
