package cn.com.open.apptoolservice.app.mapper;

import org.apache.ibatis.annotations.Select;

public interface AppMapper {

    @Select("select app_secret from app where app_key = #{appKey}")
    String findAppSecretByAppkey(String appKey);

}
