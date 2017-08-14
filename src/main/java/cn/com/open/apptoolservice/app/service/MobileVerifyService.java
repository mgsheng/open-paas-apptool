package cn.com.open.apptoolservice.app.service;

import cn.com.open.apptoolservice.app.common.Result;
import cn.com.open.apptoolservice.app.vo.MobileVerifyVo;

public interface MobileVerifyService {
    Result attribution(MobileVerifyVo mobileVerifyVo) throws Exception;
}