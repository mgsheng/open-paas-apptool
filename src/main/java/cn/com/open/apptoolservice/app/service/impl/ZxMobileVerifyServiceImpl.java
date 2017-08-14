package cn.com.open.apptoolservice.app.service.impl;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.fastjson.JSON;

import cn.com.open.apptoolservice.app.common.ExceptionEnum;
import cn.com.open.apptoolservice.app.common.MobileResultEnum;
import cn.com.open.apptoolservice.app.common.Result;
import cn.com.open.apptoolservice.app.common.ServiceProviderEnum;
import cn.com.open.apptoolservice.app.common.ZxptEntityEnum;
import cn.com.open.apptoolservice.app.controller.PhoneController;
import cn.com.open.apptoolservice.app.entity.ApptoolRecordInfo;
import cn.com.open.apptoolservice.app.entity.ApptoolTradeChannel;
import cn.com.open.apptoolservice.app.record.RecordRemoteCall;
import cn.com.open.apptoolservice.app.service.ApptoolTradeChannelService;
import cn.com.open.apptoolservice.app.service.MobileVerifyService;
import cn.com.open.apptoolservice.app.vo.MobileVerifyVo;
import cn.com.open.apptoolservice.app.zxpt.http.HttpClientUtil;
import cn.com.open.apptoolservice.app.zxpt.rsa.RSACoderUtil;
import cn.com.open.apptoolservice.app.zxpt.sign.MD5;
import cn.com.open.apptoolservice.app.zxpt.xml.XOUtil;
import cn.com.open.apptoolservice.app.zxpt.zx.DateUtil;
import cn.com.open.apptoolservice.app.zxpt.zx.MobileVerifyRequest;
import cn.com.open.apptoolservice.app.zxpt.zx.Request;
import cn.com.open.apptoolservice.app.zxpt.zx.RequestHead;
import cn.com.open.apptoolservice.app.service.ApptoolRecordInfoService;

public class ZxMobileVerifyServiceImpl implements MobileVerifyService {

    private final Logger log = LoggerFactory.getLogger(PhoneController.class);

    @Value("${mobile.verify.service.provider}")
    private String mobileVerifyServiceProvider;
    @Autowired
    private ApptoolTradeChannelService apptoolTradeChannelService;
    @Autowired
    private ApptoolRecordInfoService apptoolRecordInfoService;

    @Override
    public Result attribution(MobileVerifyVo mobileVerifyVo) throws Exception {
        String channelName = ServiceProviderEnum.getNameByValue(mobileVerifyServiceProvider);
        log.info("请求开始："+channelName);
        ApptoolTradeChannel apptoolTradeChannel = apptoolTradeChannelService.findByChannelName(channelName);
		String paket="";
		String sign="";
		Request<MobileVerifyRequest> request=new Request<MobileVerifyRequest>();
		
		String other= apptoolTradeChannel.getOther();
		Map<String, String> others = new HashMap<String, String>();
		others=getPartner(other);
		String charset=others.get(ZxptEntityEnum.CHARSET.getCode());
		log.info("获取参数成功："+charset);
		RequestHead head=initHead(others);
		request.setHead(head);
		List<MobileVerifyRequest> list=new ArrayList<MobileVerifyRequest>();
		MobileVerifyRequest bRequest=init1201Request(mobileVerifyVo,others);
		list.add(bRequest);
		request.setBody(list);
		String requestXml=XOUtil.objectToXml(request,  Request.class, RequestHead.class, MobileVerifyRequest.class);
		paket= RSACoderUtil.encrypt(requestXml.getBytes(charset), charset, others.get(ZxptEntityEnum.PUBLICKEY.getCode()));
		sign = MD5.sign(paket, others.get(ZxptEntityEnum.MD5KEY.getCode()), charset);
		Map<String, String> params=new HashMap<String,String>();
		params.put("packet", paket);
		params.put("checkValue", sign);
		params.put("tranCode", others.get(ZxptEntityEnum.TRANCODE.getCode()));
		params.put("sender", others.get(ZxptEntityEnum.SENDER.getCode()));//sender：填写由业务分发的商户号
		String url = apptoolTradeChannel.getRequestUrl();
		String responsexml = HttpClientUtil.httpPost(url, params);
		log.info("请求结果："+responsexml);
		byte[] outdata;
		outdata = RSACoderUtil.decrypt(responsexml, others.get(ZxptEntityEnum.PRIVATEKEY.getCode()), charset);
		String decode = new String(outdata, charset);
		String decodexml = URLDecoder.decode(decode, charset);
        if (StringUtils.isNotEmpty(decodexml)) {
        	JSONObject xmlJSONObj = XML.toJSONObject(decodexml);
    		com.alibaba.fastjson.JSONObject jsonObject = null;
    		jsonObject = JSON.parseObject(xmlJSONObj.toString());
    		String zxpt=jsonObject.getString("zxpt");
    		log.info("请求结果zxpt："+zxpt);
    		com.alibaba.fastjson.JSONObject zxptjson=JSON.parseObject(zxpt);
    		String body=zxptjson.getString("body");
    		if(nullEmptyBlankJudge(body)){
    			return new Result(Result.ERROR, ExceptionEnum.ThirdBodyError.getMessage(),  ExceptionEnum.ThirdBodyError.getCode(), null);
    		}else{
    			log.info("请求结果zxpt："+body);
    			com.alibaba.fastjson.JSONObject bodyjson=JSON.parseObject(body);
        		String mobileverifyresponse=bodyjson.getString("mobileverifyresponse");
        		if(nullEmptyBlankJudge(mobileverifyresponse)){
        			return new Result(Result.ERROR, ExceptionEnum.Mobileverifyresponse.getMessage(),  ExceptionEnum.Mobileverifyresponse.getCode(), null);
        		}else{
        			com.alibaba.fastjson.JSONObject mobileverifyjson=JSON.parseObject(mobileverifyresponse);
            		String verificationResult=mobileverifyjson.getString("result");
            		String orderId=mobileverifyjson.getString("orderId");
            		String orderNo=mobileverifyjson.getString("orderNo");
            		int returnResult=0;
            		 Map<String, Object> map = new HashMap<>();
            		 log.info("请求结果zxpt："+orderId+":"+orderNo);
            		 ApptoolRecordInfo apptoolRecordInfo= apptoolRecordInfoService.findApptoolRecordInfoById(orderId);
            		 
            		  if(!nullAndEmpty(verificationResult)&&verificationResult.equals(MobileResultEnum.RESULTSUCCESS.getMessage())&&apptoolRecordInfo!=null){
            			 apptoolRecordInfo.setStatus(1);
            			 apptoolRecordInfo.setThirdOrderNo(orderNo);
            			 returnResult=1;
            		  }else if(!nullAndEmpty(verificationResult)&&verificationResult.equals(MobileResultEnum.RESULTERROR.getMessage())&&apptoolRecordInfo!=null){
            			 apptoolRecordInfo.setStatus(2);
             			 apptoolRecordInfo.setThirdOrderNo(orderNo);
             			 returnResult=-1;
            		  }else{
            			  apptoolRecordInfo.setThirdOrderNo(orderNo);
            		  }
            		  boolean f=apptoolRecordInfoService.updateApptoolRecordInfo(apptoolRecordInfo);
            		  if(f){
            			  map.put("verificationResult", returnResult);
            			  log.info("更新状态成功");
                		  return new Result(Result.SUCCESS, ExceptionEnum.MobileVerifySuccess.getMessage(), null, map);
            		  }else{
            			  return new Result(Result.ERROR, ExceptionEnum.UpdateEntityError.getMessage(),  ExceptionEnum.UpdateEntityError.getCode(), null);  
            		  }
        		}
    		}
        } else{
        	return new Result(Result.ERROR, ExceptionEnum.SysException.getMessage(),  ExceptionEnum.SysException.getCode(), null);
        }
    }
	public  RequestHead initHead( Map<String, String> others) {
		//初始化报文头
		RequestHead rHead=new RequestHead();
		rHead.setVersion(others.get(ZxptEntityEnum.VERSION.getCode()));
		rHead.setCharSet(others.get(ZxptEntityEnum.CHARSET.getCode()));
		rHead.setSource(others.get(ZxptEntityEnum.SOURCE.getCode()));
		rHead.setDes(others.get(ZxptEntityEnum.DES.getCode()));
		rHead.setApp(others.get(ZxptEntityEnum.APP.getCode()));
		rHead.setTranCode(others.get(ZxptEntityEnum.TRANCODE.getCode()));
		rHead.setTranId(DateUtil.getDateTime(new Date()));
		rHead.setTranRef(others.get(ZxptEntityEnum.TRANREF.getCode()));
		rHead.setReserve(others.get(ZxptEntityEnum.RESERVE.getCode()));
		rHead.setTranTime( DateUtil.getCurrentDate("yyyyMMdd"));
		rHead.setTimeStamp(DateUtil.getCurrentDate("yyyyMMddHHmmss"));
		return rHead;
		
	}
	
	public static MobileVerifyRequest init1201Request(MobileVerifyVo mobileVerifyVo,Map<String, String> others) {
		
		MobileVerifyRequest bRequest=new MobileVerifyRequest();
		bRequest.setOrderId(mobileVerifyVo.getId());
		bRequest.setCertNo(mobileVerifyVo.getIdCard());
		bRequest.setName(mobileVerifyVo.getRealName());
		bRequest.setMobile(mobileVerifyVo.getNumber());
		bRequest.setChannelNo(others.get(ZxptEntityEnum.CHANNELNO.getCode()));
		bRequest.setEntityAuthCode(others.get(ZxptEntityEnum.ENTITYAUTHCODE.getCode()));
		bRequest.setEntityAuthDate(DateUtil.getCurrentPrettyDateTime());
		return bRequest;
		
	}
	public  Map<String, String> getPartner(String other){
		if(other==null&&"".equals(other)){
			return null;
		}else{
		String others []=other.split("#");
		Map<String, String> sParaTemp = new HashMap<String, String>();
		for (int i=0;i<others.length;i++){
			String values []=others[i].split(":");
			   sParaTemp.put(values[0], values[1]);  
		}
		
		return sParaTemp;
		}
	}
	protected static boolean nullAndEmpty(String str){
        return null==str||str.isEmpty()||"".equals(str.trim());
    }
	 protected boolean nullEmptyBlankJudge(String str){
        return null==str||str.isEmpty()||"".equals(str.trim());
    }
}
