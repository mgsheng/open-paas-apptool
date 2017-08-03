package cn.com.open.apptoolservice.app.service;

import cn.com.open.apptoolservice.app.common.Result;
import cn.com.open.apptoolservice.app.entity.ApptoolRecordInfo;

import javax.servlet.http.HttpServletRequest;

public interface ApptoolRecordInfoService {

	boolean insert(ApptoolRecordInfo apptoolRecordInfo);

	void saveImageRecordInfo(HttpServletRequest request, Result result);
}
