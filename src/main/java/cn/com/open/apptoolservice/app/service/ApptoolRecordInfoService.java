package cn.com.open.apptoolservice.app.service;

import cn.com.open.apptoolservice.app.common.Result;
import cn.com.open.apptoolservice.app.entity.ApptoolRecordInfo;
import cn.com.open.apptoolservice.app.vo.MobileVerifyVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ApptoolRecordInfoService {

	boolean insert(ApptoolRecordInfo apptoolRecordInfo);
	boolean saveApptoolRecordInfo(HttpServletRequest request,MobileVerifyVo mobileVerifyVo);
	ApptoolRecordInfo findApptoolRecordInfoById(String id);
	boolean updateApptoolRecordInfo(ApptoolRecordInfo apptoolRecordInfo);

	ApptoolRecordInfo findByCondition(MobileVerifyVo mobileVerifyVo, Integer channelValue);

    void saveRecordInfo(HttpServletRequest request, Result result, String channelValue);

    List<ApptoolRecordInfo> findBySelective(ApptoolRecordInfo example);

}
