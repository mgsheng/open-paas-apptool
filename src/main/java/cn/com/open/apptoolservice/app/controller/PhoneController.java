package cn.com.open.apptoolservice.app.controller;

import cn.com.open.apptoolservice.app.common.BaseController;
import cn.com.open.apptoolservice.app.common.ExceptionEnum;
import cn.com.open.apptoolservice.app.common.Result;
import cn.com.open.apptoolservice.app.service.PhoneService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * 手机归属地查询
 *
 * Created by guxuyang on 11/07/2017.
 */
@RestController
@RequestMapping("/phone")
public class PhoneController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(PhoneController.class);

    @Autowired
    private PhoneService phoneService;

    /**
     * 手机号归属地查询
     *
     * @param number 手机号码
     * @param merchantId 商户id
     * @param response response
     */
    @RequestMapping(value = "/attribution", method = RequestMethod.POST)
    public void attribution(String number, Integer merchantId, HttpServletResponse response) {
        if (StringUtils.isEmpty(number) || merchantId == null) {
            responseErrorJason(response, ExceptionEnum.ParameterError.getCode(), "必填参数不能为空");
            return;
        }
        try {
            Result result = phoneService.attribution(number);
            responseJason(response, result);
        } catch (Exception e) {
            log.error(e.getMessage());
            responseErrorJason(response, ExceptionEnum.SysException);
        }
    }

}
