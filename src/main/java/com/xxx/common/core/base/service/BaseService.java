package com.xxx.common.core.base.service;

import org.apache.ibatis.exceptions.TooManyResultsException;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;

/**
 * Service 层 基础接口，其他Service 接口 请继承该接口
 *
 * @author xujingyang
 * @date 2018/05/28
 */
public interface BaseService<T> {


    /**
     * 持久化
     *
     * @param model
     */
    void save(T model);

    /**
     * 批量持久化
     *
     * @param models
     */
    void save(List<T> models);

    /**
     * 通过主鍵刪除
     *
     * @param id
     */
    void deleteById(String id);

    /**
     * 批量刪除 eg：ids -> “1,2,3,4”
     *
     * @param ids
     */
    void deleteByIds(String ids);

    /**
     * 更新
     *
     * @param model
     */
    void update(T model);

    /**
     * 通过ID查找
     *
     * @param id
     * @return
     */
    T findById(String id);

    /**
     * 通过Model中某个成员变量名称（非数据表中column的名称）查找,value需符合unique约束
     *
     * @param fieldName
     * @param value
     * @return
     * @throws TooManyResultsException
     */
    T findBy(String fieldName, Object value) throws TooManyResultsException;

    /**
     * 通过多个ID查找//eg：ids -> “1,2,3,4”
     *
     * @param ids
     * @return
     */
    List<T> findByIds(String ids);

    /**
     * 根据条件查找
     *
     * @param condition
     * @return
     */
    List<T> findByCondition(Condition condition);



    /**
     * 根据model条件查找,返回集合
     *
     * @param model
     * @return
     */
    List<T> findListByModel(T model);


    /**
     * 根据model条件查找,返回单个
     *
     * @param model
     * @return
     */
     T findOneByModel(T model);


    /**
     * 根据条件查找对应条数，传空对象标识查询所有数量
     *
     * @param model
     * @return
     */
    int selectCount(T model);

    /**
     * 获取所有
     *
     * @return
     */
    List<T> findAll();
}
