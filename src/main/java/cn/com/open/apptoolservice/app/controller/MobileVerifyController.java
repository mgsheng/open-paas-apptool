package cn.com.open.apptoolservice.app.controller;

import cn.com.open.apptoolservice.app.common.ExceptionEnum;
import cn.com.open.apptoolservice.app.common.Result;
import cn.com.open.apptoolservice.app.entity.ApptoolRecordInfo;
import cn.com.open.apptoolservice.app.service.ApptoolRecordInfoService;
import cn.com.open.apptoolservice.app.service.MobileVerifyService;
import cn.com.open.apptoolservice.app.vo.MobileVerifyVo;
import cn.com.open.apptoolservice.app.zxpt.zx.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

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
    private MobileVerifyService mobileVerifyService;

    /**
     * 个人三大运营商手机号码验证
     *
     * @param response response
     */
    @RequestMapping(value = "/verify", method = RequestMethod.POST)
    public void verify( HttpServletResponse response,HttpServletRequest request,MobileVerifyVo mobileVerifyVo) {
    	
   	     if (!paraMandatoryCheck(Arrays.asList(mobileVerifyVo.getIdCard(),mobileVerifyVo.getNumber(),mobileVerifyVo.getRealName(),mobileVerifyVo.getMerchantId()))) {
   	    	responseErrorJason(response, ExceptionEnum.ParameterError.getCode(),ExceptionEnum.ParameterError.getMessage());
            return;
		 }
   	     String id=DateUtil.getCurrentDateTime();
   	     ApptoolRecordInfo apptoolRecordInfo=apptoolRecordInfoService.findApptoolRecordInfoById(id);
   	     if(apptoolRecordInfo!=null){
   	    	responseErrorJason(response, ExceptionEnum.EntiryIsNotNull.getCode(),ExceptionEnum.EntiryIsNotNull.getMessage());
   	     }else{
   	    	
   	    	try {
   	    		mobileVerifyVo.setId(id);
   	    		boolean f=apptoolRecordInfoService.saveApptoolRecordInfo(request, mobileVerifyVo);
   	   	    	if(f){
   	   	         //第三方验证
   	   	   	      Result result =mobileVerifyService.attribution(mobileVerifyVo);
   	   	   	      
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
}
