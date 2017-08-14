package cn.com.open.apptoolservice.app.zxpt.test;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomUtils;

import cn.com.open.apptoolservice.app.zxpt.http.HttpClientUtil;
import cn.com.open.apptoolservice.app.zxpt.rsa.RSACoderUtil;
import cn.com.open.apptoolservice.app.zxpt.sign.MD5;
import cn.com.open.apptoolservice.app.zxpt.xml.XOUtil;
import cn.com.open.apptoolservice.app.zxpt.zx.DateUtil;
import cn.com.open.apptoolservice.app.zxpt.zx.MobileVerifyRequest;
import cn.com.open.apptoolservice.app.zxpt.zx.Request;
import cn.com.open.apptoolservice.app.zxpt.zx.RequestHead;


/**
 * 维氏盾三大运营商手机号码验证
 */
public class T1201 {
	
	    //字符集
		private static String charset = "utf-8";
		//征信平台公钥
		private static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDLfb0CjHdrU3ihTADP6FtgFyvYsIs+u36aYr80+BBCSa3JnJSGw7s6Mdusmyjm98T6RFq5WeJjzRQXHyOcUD/n8abpIbxfhhd9hODJfpYd1i0w3A0uNnbQubhThcb11sHQI1k70IIrJiwpcacuf8Y15F3FXCJBH5710YS9l94MgQIDAQAB";
		//商户私钥
		private static String key = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMCtreCQkwGo68pyqJ5t6NY4cSHeQFBiGyA8wjeXDVVSXxAdxMfJO9D6xzqE0fPlxhZVEcAQUXsGYw8Zr2Y0zPTfy+om79rCAyNkbP/igb9jYqysCNa0XKl3R/Z1zVx00VWojeUD1+6qA0+QZ//90uQhJlUfU9QNKz51x2766VXnAgMBAAECgYBehVUlMwdK6ykw2WqqvdRZMrsdGECIrngMKoJEbw+VaaFE8LWWJLv5WuzdYkb01SWF0xmwFNFD/vAdekY3Z3ObWj/OrNL297MT8aTjmH0H79OnZfeL+wChoxKk1FE1pA8iUJB0awLInv72GQ7JEJ+GnkbJb0QsSowyo9B9MwAQoQJBAPRcQhCQ5iVgofZej0Xk8T9bHk5jFU8Duu/jR8AfP/S0vbJuxfw9FU5F++rghK2oyS5yOSXCZV8WjOuN5jwzFUkCQQDJ2zWD8LCnMWQCkdBscwOEA83Mq+cur8yoc7uR2QfNpWOuTtuK1gvoPH91A3m5rl8P9pFshTibklna2IZYJIGvAkAxxtFWWo3nM0YKz7xTuo1CIKeNxDVFATeFQkENa9A1YtP5kwMVnMPITA1DDTU5wtYodfAaNv07X3aZTTCHNsixAkBH0GlVq4fts7C1CVNxgem6SfAp5O62uWzCcYpF9UTFcRXpqbyJxGUwFnXyF25zFQpVD4/lX/AnyQWWynnhWfuZAkEAvn7KG3umWpJA+RzNhUXFGpVTtn8JZdfDLQXtl7K7BEN7+xBsZ9zAjFs++QD1vUqLokqqKu4uUFNGkUlFB71dPg==";		
		
	public static String test(MobileVerifyRequest mobileVerifyRequest) throws Exception {
		String paket="";
		String sign="";
		Request<MobileVerifyRequest> request=new Request<MobileVerifyRequest>();
		RequestHead head=initHead("1201", DateUtil.getDateTime(new Date()));
		request.setHead(head);
		List<MobileVerifyRequest> list=new ArrayList<MobileVerifyRequest>();
		MobileVerifyRequest bRequest=init1201Request(mobileVerifyRequest);
		list.add(bRequest);
		request.setBody(list);
		String requestXml=XOUtil.objectToXml(request,  Request.class, RequestHead.class, MobileVerifyRequest.class);
		paket= RSACoderUtil.encrypt(requestXml.getBytes(charset), charset, publicKey);
		sign = MD5.sign(paket, "123456", "utf-8");
		Map<String, String> params=new HashMap<String,String>();
		params.put("packet", paket);
		params.put("checkValue", sign);
		params.put("tranCode", "1201");
		params.put("sender", "800000000003");//sender：填写由业务分发的商户号
		String url = "http://183.63.22.178:8888/cr-web/gateway.do";
		String responsexml = HttpClientUtil.httpPost(url, params);
		byte[] outdata;
		outdata = RSACoderUtil.decrypt(responsexml, key, charset);
		String decode = new String(outdata, charset);
		String decodexml = URLDecoder.decode(decode, charset);
		return decodexml;
		
	}
	
	public static RequestHead initHead(String tranCode,String tranId) {
		RequestHead rHead=new RequestHead();
		SimpleDateFormat dFormat=new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat dFormat2=new SimpleDateFormat("yyyyMMdd");
		//初始化报文头
		rHead.setVersion("V1.0");
		rHead.setCharSet("utf-8");
		rHead.setSource("TCL系统");
		rHead.setDes("zxpt");
		rHead.setApp("App");
		rHead.setTranCode(tranCode);
		rHead.setTranId(tranId);
		rHead.setTranRef("商户");
		rHead.setReserve("商户测试");
		rHead.setTranTime( DateUtil.getCurrentDate("yyyyMMdd"));
		rHead.setTimeStamp(DateUtil.getCurrentDate("yyyyMMddHHmmss"));
		return rHead;
		
	}
	
	public static MobileVerifyRequest init1201Request(MobileVerifyRequest mobileVerifyRequest) {
		
		MobileVerifyRequest bRequest=new MobileVerifyRequest();
		bRequest.setOrderId(DateUtil.getCurrentDateTime());
		bRequest.setCertNo(mobileVerifyRequest.getCertNo());
		bRequest.setName(mobileVerifyRequest.getName());
		bRequest.setMobile(mobileVerifyRequest.getMobile());
		bRequest.setChannelNo("1");
		bRequest.setEntityAuthCode("02aa19bc");
		bRequest.setEntityAuthDate(DateUtil.getCurrentPrettyDateTime());
		return bRequest;
		
	}
	
	private static List<MobileVerifyRequest> initData(){
		
		String[] strArr1 = getDataList1();
		//String[] strArr1 = getDataList1();
		
		List<MobileVerifyRequest> datas = new ArrayList<MobileVerifyRequest>();
		
		for(int i = 0 ; i < strArr1.length ; i++){
			String str = strArr1[i];
			String[] arr = str.split("@");
			
			MobileVerifyRequest request = new MobileVerifyRequest();
			
			request.setName(arr[0]);
			request.setCertNo(arr[1]);
			request.setMobile(arr[2]);
			
			datas.add(request);
		}
		
		return datas;
		
	}
	
	public static void main(String[] args) throws Exception {
		
		List<MobileVerifyRequest> testdata = initData();
		
		System.out.println("数据量：" + testdata.size());
		
		for(int i = 0 ; i<testdata.size() ; i++){
			
			String t1201 = test(testdata.get(i));
			System.out.println("=====t1201=====执行了"+i+"条;");
			System.out.println(t1201);

		}
	}
	
	private static String[] getDataList1() {
		
		String[] strArr1 = new String[]{
			"陈芊百@513701199203150016@18280332327",
			"施瑞琪@321302198906028845@18360029686"  //支持多个个人信息验证
		};
		
		return strArr1;
	}
}
