package cn.com.open.apptoolservice.app.mapper;

import org.apache.ibatis.annotations.Insert;

import cn.com.open.apptoolservice.app.common.BaseMapper;
import cn.com.open.apptoolservice.app.entity.ApptoolRecordInfo;

public interface ApptoolRecordInfoMapper<T> extends BaseMapper<T> {
	@Insert("insert into apptool_record_info (id,status,app_key,service_name,parmenter,merchant_id,source_username,source_uid,create_time) values (#{id},#{status},#{appKey},#{serviceName},#{parmenter},#{merchantId},#{sourceUsername},#{sourceUid},now())")
	void insertApptoolRecordInfo(ApptoolRecordInfo apptoolRecordInfo);
	
	
}
