package cn.com.open.apptoolservice.app.service.impl;

import cn.com.open.apptoolservice.app.common.ExceptionEnum;
import cn.com.open.apptoolservice.app.common.Result;
import cn.com.open.apptoolservice.app.common.ServiceProviderEnum;
import cn.com.open.apptoolservice.app.entity.ApptoolRecordInfo;
import cn.com.open.apptoolservice.app.entity.ApptoolTradeChannel;
import cn.com.open.apptoolservice.app.log.ThirdPartyCallAssistant;
import cn.com.open.apptoolservice.app.log.support.AliyunResponseBean;
import cn.com.open.apptoolservice.app.service.ApptoolRecordInfoService;
import cn.com.open.apptoolservice.app.service.ApptoolTradeChannelService;
import cn.com.open.apptoolservice.app.service.MobileVerifyService;
import cn.com.open.apptoolservice.app.vo.MobileVerifyVo;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.api.gateway.demo.constant.HttpMethod;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class AliyunVerifyServiceImpl implements MobileVerifyService {

    @Autowired
    private ApptoolTradeChannelService apptoolTradeChannelService;
    @Autowired
    private ThirdPartyCallAssistant thirdPartyCallAssistant;
    @Autowired
    private ApptoolRecordInfoService apptoolRecordInfoService;

    @Override
    public Result attribution(MobileVerifyVo mobileVerifyVo) throws Exception {
        ApptoolTradeChannel apptoolTradeChannel = apptoolTradeChannelService.findByChannelName(ServiceProviderEnum.AliyunMobileVerify.getName());
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "APPCODE " + apptoolTradeChannel.getKeyValue());
        headers.put("Content-Type", "application/json; charset=UTF-8");
        Map<String, String> querys = new HashMap<>();
        querys.put("idCard", mobileVerifyVo.getIdCard());
        querys.put("mobile", mobileVerifyVo.getNumber());
        querys.put("realName", mobileVerifyVo.getRealName());
        AliyunResponseBean aliyunResponseBean = thirdPartyCallAssistant.verify(apptoolTradeChannel.getRequestUrl(), headers, HttpMethod.GET, querys);
        String text = aliyunResponseBean.getJson();

        ApptoolRecordInfo apptoolRecordInfo = apptoolRecordInfoService.findApptoolRecordInfoById(mobileVerifyVo.getId());
        apptoolRecordInfo.setLogId(aliyunResponseBean.getLogId());

        Map<String, Object> ignore = new HashMap<>();
        ignore.put("logId", aliyunResponseBean.getLogId());

        Map<String, Object> payload = new HashMap<>();
        String message = "";

        if (StringUtils.isNotEmpty(text)) {
            JSONObject jsonObject = JSONObject.parseObject(text);
            Integer resCode = jsonObject.getInteger("error_code");
            if (resCode.equals(0)) { //调用成功
                JSONObject resBody = jsonObject.getJSONObject("result");
                if (resBody.getInteger("VerificationResult").equals(1)) { //成功返回结果
                    apptoolRecordInfo.setStatus(1);
                    apptoolRecordInfo.setChannelValue(Integer.valueOf(ServiceProviderEnum.AliyunMobileVerify.getValue()));
                    apptoolRecordInfo.setVerifyResult(1);

                    payload.put("verificationResult", 1);
                    payload.put("verifycationMsg", "手机号实名制信息匹配一致");
                    message = "认证成功";
                } else { //系统无记录
                    apptoolRecordInfo.setStatus(1);
                    apptoolRecordInfo.setChannelValue(Integer.valueOf(ServiceProviderEnum.AliyunMobileVerify.getValue()));
                    apptoolRecordInfo.setVerifyResult(-1);

                    payload.put("verificationResult", -1);
                    payload.put("verifycationMsg", "手机号实名制信息匹配不一致");
                    message = "认证失败";
                }
            } else if (resCode == 10025) {
                apptoolRecordInfo.setStatus(1);
                apptoolRecordInfo.setChannelValue(Integer.valueOf(ServiceProviderEnum.AliyunMobileVerify.getValue()));
                apptoolRecordInfo.setVerifyResult(0);

                payload.put("verificationResult", 0);
                payload.put("verifycationMsg", "没有查询到结果");
                message = " 没有查询到结果";
            } else { // 失败
                apptoolRecordInfo.setStatus(2);
                apptoolRecordInfo.setChannelValue(Integer.valueOf(ServiceProviderEnum.AliyunMobileVerify.getValue()));
                apptoolRecordInfoService.updateApptoolRecordInfo(apptoolRecordInfo);
                Result result = new Result(Result.ERROR, ExceptionEnum.ThirdBodyError.getMessage(), ExceptionEnum.ThirdBodyError.getCode(), null);
                result.setIgnore(ignore);
                return result;
            }
            apptoolRecordInfoService.updateApptoolRecordInfo(apptoolRecordInfo);
            Result result = new Result(Result.SUCCESS, message, null, payload);
            result.setIgnore(ignore);
            return result;
        }

        apptoolRecordInfo.setStatus(2);
        apptoolRecordInfo.setChannelValue(Integer.valueOf(ServiceProviderEnum.AliyunMobileVerify.getValue()));
        apptoolRecordInfoService.updateApptoolRecordInfo(apptoolRecordInfo);
        Result result = new Result(Result.ERROR, ExceptionEnum.ThirdBodyError.getMessage(), ExceptionEnum.ThirdBodyError.getCode(), null);
        result.setIgnore(ignore);
        return result;
    }

}
