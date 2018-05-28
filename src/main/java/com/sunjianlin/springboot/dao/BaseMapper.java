package com.sunjianlin.springboot.dao;

import com.sunjianlin.springboot.common.QueryParams;

import java.util.List;

/**
 * Created by sunjianlin
 * 2018年03月09日 15:59:30
 */
public interface BaseMapper <T> {

    /**
     * 查询记录总条数
     * @param queryParams 属性map
     * @return 当前实体类型记录数
     */
    int countByMap(QueryParams queryParams);

    /**
     * 新增记录
     * @param entity 新增的实体对象
     * @return 新增记录的主键ID
     */
    int insert(T entity);

    /**
     * 批量新增记录
     * @param list 新增的对象列表
     * @return 影响的记录数，正常应等于传入列表参数长度
     */
    int batchInsert(final List<T> list);

    /**
     * 更新记录
     * @param entity 需要更新的实体对象
     * @return 影响的记录数，正常应返回1
     */
    int update(T entity);

    /**
     * 批量更新记录
     * @param list 需要批量更新的实体对象列表
     * @return 影响的记录数，正常应等于传入列表参数长度
     */
    int batchUpdate(List<T> list);

    /**
     * 通过主键ID删除记录
     * @param id 主键ID
     * @return 影响的记录数，正常应返回1
     */
    int deleteById(Object id);

    /**
     * 批量删除记录
     * @param list 需要删除的实体对象列表
     * @return 影响的记录数，正常应等于传入列表参数长度
     */
    int batchDelete(List<T> list);

    /**
     * 通过主键查找记录
     * @param id 主键ID
     * @return 符合条件的实体对象
     */
    T selectById(Object id);

    /**
     * 查询符合条件的记录集
     * @param queryParams 属性map
     * @return 返回符合条件的实体对象列表
     */
    List<T> selectByProps(QueryParams queryParams);

//    /**
//     * 查询符合条件的记录集
//     * @param queryParams 属性map
//     * @param orderBy 排序列
//     * @param isAsc 排序规则，true代表升序，false代表降序
//     * @return 返回符合条件的实体对象列表
//     */
//    List<T> selectByProps(QueryParams queryParams, String orderBy, boolean isAsc);
//
//    /**
//     * 分页查询符合条件的记录集
//     * @param queryParams 属性map
//     * @param pageSize 分页大小，默认为20
//     * @param startIndex 起始数据索引，默认从0开始
//     * @return 返回符合条件的实体对象列表
//     */
//    Page<T> selectByProps(QueryParams queryParams, int pageSize, int startIndex);
//
//    /**
//     * 分页查询符合条件的记录集
//     * @param queryParams 属性map
//     * @param pageSize 分页大小，默认为20
//     * @param startIndex 起始数据索引，默认从0开始
//     * @param orderBy 排序列
//     * @param isAsc 排序规则，true代表升序，false代表降序
//     * @return 返回符合条件的实体对象列表
//     */
//    Page<T> selectByProps(QueryParams queryParams, int pageSize, int startIndex, String orderBy, boolean isAsc);

    /**
     * 获取全部记录
     * @return 所有实体对象
     */
    List<T> selectAll();

    /**
     * 根据原生Sql查询记录
     * @param sql 查询sql语句
     * @return 返回符合查询条件的实体对象列表
     */
    List<T> selectByNativeSql(String sql);

}