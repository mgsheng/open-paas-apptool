package cn.com.open.apptoolservice.app.service;

import cn.com.open.apptoolservice.app.common.Result;
import cn.com.open.apptoolservice.app.entity.ApptoolRecordInfo;
import cn.com.open.apptoolservice.app.vo.MobileVerifyVo;

import javax.servlet.http.HttpServletRequest;

public interface ApptoolRecordInfoService {

	boolean insert(ApptoolRecordInfo apptoolRecordInfo);

	void saveImageRecordInfo(HttpServletRequest request, Result result);
	boolean saveApptoolRecordInfo(HttpServletRequest request,MobileVerifyVo mobileVerifyVo);
	ApptoolRecordInfo findApptoolRecordInfoById(String id);
	boolean  updateApptoolRecordInfo(ApptoolRecordInfo apptoolRecordInfo);
}
