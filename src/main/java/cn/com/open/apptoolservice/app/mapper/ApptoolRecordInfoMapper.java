package cn.com.open.apptoolservice.app.mapper;

import cn.com.open.apptoolservice.app.entity.ApptoolRecordInfo;
import org.apache.ibatis.annotations.Insert;

public interface ApptoolRecordInfoMapper {

	@Insert("insert into apptool_record_info (id,status,app_key,service_name,parmenter,merchant_id,source_username,source_uid,create_time) values (#{id},#{status},#{appKey},#{serviceName},#{parmenter},#{merchantId},#{sourceUsername},#{sourceUid},now())")
	void insert(ApptoolRecordInfo apptoolRecordInfo);

}
