package cn.com.open.apptoolservice.app.service.impl;

import cn.com.open.apptoolservice.app.common.ExceptionEnum;
import cn.com.open.apptoolservice.app.common.Result;
import cn.com.open.apptoolservice.app.common.ServiceProviderEnum;
import cn.com.open.apptoolservice.app.controller.PhoneController;
import cn.com.open.apptoolservice.app.entity.ApptoolTradeChannel;
import cn.com.open.apptoolservice.app.record.RecordRemoteCall;
import cn.com.open.apptoolservice.app.service.ApptoolTradeChannelService;
import cn.com.open.apptoolservice.app.service.PhoneService;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.api.gateway.demo.constant.HttpMethod;
import com.aliyun.api.gateway.demo.util.HttpUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AliyunPhoneServiceImpl implements PhoneService {

    private final Logger log = LoggerFactory.getLogger(PhoneController.class);

    @Value("${phone.attribution.service.provider}")
    private String phoneAttributionServiceProvider;
    @Autowired
    private ApptoolTradeChannelService apptoolTradeChannelService;

    @RecordRemoteCall
    @Override
    public Result attribution(String number) throws Exception {
        String channelName = ServiceProviderEnum.getNameByValue(phoneAttributionServiceProvider);
        ApptoolTradeChannel apptoolTradeChannel = apptoolTradeChannelService.findByChannelName(channelName);
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "APPCODE " + apptoolTradeChannel.getKeyValue());
        headers.put("Content-Type", "application/json; charset=UTF-8");

        Map<String, String> querys = new HashMap<>();
        querys.put("num", number);
        HttpResponse resp = HttpUtils.doGet(apptoolTradeChannel.getRequestUrl(), null, HttpMethod.GET, headers, querys);

        String headRequestId = Arrays.toString(resp.getHeaders("X-Ca-Request-Id"));
        String headErrorMessage = Arrays.toString(resp.getHeaders("X-Ca-Error-Message"));
        log.info(String.format("aliyun phone attribution response header { request id: %s, errorMessage: %s } ", headRequestId, headErrorMessage));

        String text = EntityUtils.toString(resp.getEntity());
        log.info(String.format("aliyun phone attribution response text { %s } ", text));

        if (StringUtils.isNotEmpty(text)) {
            JSONObject jsonObject = JSONObject.parseObject(text);
            Integer resCode = jsonObject.getInteger("showapi_res_code");
            if (resCode.equals(0)) { //调用成功
                JSONObject resBody = jsonObject.getJSONObject("showapi_res_body");
                if (resBody.getInteger("ret_code").equals(0)) { //成功返回结果
                    Map<String, Object> map = new HashMap<>();
                    map.put("province", resBody.getString("prov"));
                    map.put("city",  resBody.getString("city"));
                    map.put("type",  resBody.getInteger("type"));
                    map.put("name",  resBody.getString("name"));
                    return new Result(Result.SUCCESS, "查询归属地成功", null, map);
                } else {
                    String errorInfo = resBody.getString("error_info");
                    return new Result(Result.ERROR, ExceptionEnum.SysException.getMessage(),
                            StringUtils.isEmpty(errorInfo) ? ExceptionEnum.SysException.getCode() : errorInfo, null);
                }
            } else   // 失败
                return new Result(Result.ERROR, ExceptionEnum.SysException.getMessage(),
                        ExceptionEnum.SysException.getCode(), null);
        } else
            return new Result(Result.ERROR, ExceptionEnum.SysException.getMessage(),  ExceptionEnum.SysException.getCode(), null);
    }

}
