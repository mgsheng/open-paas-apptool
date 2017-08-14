package cn.com.open.apptoolservice.app.common;

import java.util.List;

public interface BaseMapper<T> {

    void insert(T t);

    void update(T t);

    void updateBySelective(T t);

    void deleteById(Object id);

    T getById(Object id);

    List<T> findBySelective(T t);

    List<T> findPageList(T t);

    int findPageCount(T t);
    
}
