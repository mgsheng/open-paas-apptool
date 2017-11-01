package cn.com.open.apptoolservice.app.controller;

import cn.com.open.apptoolservice.app.common.*;
import cn.com.open.apptoolservice.app.entity.ApptoolRecordInfo;
import cn.com.open.apptoolservice.app.service.ApptoolRecordInfoService;
import cn.com.open.apptoolservice.app.service.MobileVerifyService;
import cn.com.open.apptoolservice.app.service.impl.AliyunVerifyServiceImpl;
import cn.com.open.apptoolservice.app.service.impl.ZxMobileVerifyServiceImpl;
import cn.com.open.apptoolservice.app.vo.MobileVerifyVo;
import cn.com.open.apptoolservice.app.zxpt.zx.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 个人三大运营商手机号码验证
 *
 * Created by guxuyang on 11/07/2017.
 */
@RestController
@RequestMapping("/mobile")
public class MobileVerifyController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(PhoneController.class);

    @Autowired
    private ApptoolRecordInfoService apptoolRecordInfoService;
    @Autowired
	private ZxMobileVerifyServiceImpl zxptMobileVerifyServiceImpl;
    @Autowired
	private AliyunVerifyServiceImpl aliyunVerifyServiceImpl;


    /**
     * 个人三大运营商手机号码验证
     *
     * @param response response
     */
    @RequestMapping(value = "/verify", method = RequestMethod.POST)
    public void verify( HttpServletResponse response,HttpServletRequest request,
						MobileVerifyVo mobileVerifyVo,
						@RequestParam(defaultValue = "tcl") String type) {
    	if (StringUtils.isEmpty(type) || MobileVerifyType.getByCode(type) == null) {
			responseErrorJason(response, ExceptionEnum.ParameterError.getCode(),ExceptionEnum.ParameterError.getMessage());
			return;
		}
		if (!paraMandatoryCheck(Arrays.asList(mobileVerifyVo.getIdCard(),mobileVerifyVo.getNumber(),mobileVerifyVo.getRealName(),mobileVerifyVo.getMerchantId()))) {
			responseErrorJason(response, ExceptionEnum.ParameterError.getCode(),ExceptionEnum.ParameterError.getMessage());
			return;
		}
		MobileVerifyService mobileVerifyService = getMobileVerifyService(type);

		if (type.equals(MobileVerifyType.ALIYUN.getCode())) {
			ApptoolRecordInfo  apptoolRecordInfo = apptoolRecordInfoService.findByCondition(mobileVerifyVo, Integer.valueOf(ServiceProviderEnum.AliyunMobileVerify.getValue()));
			if (apptoolRecordInfo != null) {
				if (1 == apptoolRecordInfo.getStatus()) { //是向第三方发送成功状态   直接将原来的结果返回
					Map<String, Object> payload = new HashMap<>();
					if (-1 == apptoolRecordInfo.getVerifyResult()) { //认证失败
						payload.put("verificationResult", -1);
						payload.put("verifycationMsg", "手机号实名制信息匹配不一致");
						responseJason(response, new Result(Result.SUCCESS, ExceptionEnum.MobileVerifySuccess.getMessage(), null, payload));
						return;
					} else if (1 == apptoolRecordInfo.getVerifyResult()) { // 认证成功
						payload.put("verificationResult", 1);
						payload.put("verifycationMsg", "手机号实名制信息匹配一致");
						responseJason(response, new Result(Result.SUCCESS, ExceptionEnum.MobileVerifySuccess.getMessage(), null, payload));
						return;
					} else if (0 == apptoolRecordInfo.getVerifyResult()) {
						payload.put("verificationResult", 0);
						payload.put("verifycationMsg", "没有查询到结果");
						responseJason(response, new Result(Result.SUCCESS, ExceptionEnum.MobileVerifySuccess.getMessage(), null, payload));
						return;
					}
				} else{
					mobileVerifyVo.setId(apptoolRecordInfo.getId());
					try {
						Result result = mobileVerifyService.attribution(mobileVerifyVo);
						responseJason(response, result);
						return;
					} catch (Exception e) {
						log.error(e.getMessage());
						responseErrorJason(response, ExceptionEnum.SysException);
						return;
					}
				}
			}
		}

		String id=DateUtil.getCurrentDateTime();
		ApptoolRecordInfo apptoolRecordInfo = apptoolRecordInfoService.findApptoolRecordInfoById(id);
   	    if(apptoolRecordInfo!=null){
   	    	responseErrorJason(response, ExceptionEnum.EntiryIsNotNull.getCode(),ExceptionEnum.EntiryIsNotNull.getMessage());
   	    }else{
   	    	try {
   	    		mobileVerifyVo.setId(id);
   	    		boolean f = apptoolRecordInfoService.saveApptoolRecordInfo(request, mobileVerifyVo);
   	   	    	if(f){
   	   	        	//第三方验证
   	   	   	    	Result result = mobileVerifyService.attribution(mobileVerifyVo);
   	   	   	    	responseJason(response, result);
   	   	    	}else{
   	   	    		responseErrorJason(response, ExceptionEnum.AddEntityError);
   	   	    	}
   	        } catch (Exception e) {
   	    		log.error(e.getMessage());
   	            responseErrorJason(response, ExceptionEnum.SysException);
   	        }
   	    
   	    }
   	    
    }

	/**
	 * 修改
	 * @param verifyResult
	 */
	@RequestMapping(value = "/modifyVerifyResult", method = RequestMethod.POST)
	public void modifyVerifyResult(MobileVerifyVo mobileVerifyVo, Integer verifyResult, HttpServletResponse response) {
		if (!paraMandatoryCheck(Arrays.asList(mobileVerifyVo.getIdCard(), mobileVerifyVo.getNumber(), mobileVerifyVo.getRealName(), mobileVerifyVo.getMerchantId()))) {
			responseErrorJason(response, ExceptionEnum.ParameterError.getCode(),ExceptionEnum.ParameterError.getMessage());
			return;
		}
		if (!(verifyResult == 1 || verifyResult == -1)) {
			responseErrorJason(response, ExceptionEnum.ParameterError.getCode(),ExceptionEnum.ParameterError.getMessage());
			return;
		}

		ApptoolRecordInfo example = new ApptoolRecordInfo();
		example.setIdCard(mobileVerifyVo.getIdCard());
		example.setRealName(mobileVerifyVo.getRealName());
		example.setPhone(mobileVerifyVo.getNumber());
		example.setChannelValue(Integer.valueOf(ServiceProviderEnum.AliyunMobileVerify.getValue()));
		List<ApptoolRecordInfo> apptoolRecordInfoList = apptoolRecordInfoService.findBySelective(example);

		if (CollectionUtils.isEmpty(apptoolRecordInfoList)) {
			responseErrorJason(response, "2", "用户不存在", null);
		} else {
			ApptoolRecordInfo apptoolRecordInfo = apptoolRecordInfoList.get(0);
			apptoolRecordInfo.setVerifyResult(verifyResult);

			boolean flag = apptoolRecordInfoService.updateApptoolRecordInfo(apptoolRecordInfo);
			if (flag) {
				responseJason(response, new Result(Result.SUCCESS, "修改成功", "1", null));
			} else {
				responseErrorJason(response, ExceptionEnum.SysException);
			}
		}
	}



	/**
	 * 根据type 获取对应的三网认证实现类
	 * @param type 类型
	 * @return 三网认证实现类
	 */
	private MobileVerifyService getMobileVerifyService(String type) {
    	if (type.equals(MobileVerifyType.TCL.getCode())) {
    		return zxptMobileVerifyServiceImpl;
		} else {
    		return aliyunVerifyServiceImpl;
		}
	}
}
