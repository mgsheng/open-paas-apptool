package cn.com.open.apptoolservice.app.service.impl;

import cn.com.open.apptoolservice.app.common.*;
import cn.com.open.apptoolservice.app.entity.ApptoolRecordInfo;
import cn.com.open.apptoolservice.app.mapper.ApptoolRecordInfoMapper;
import cn.com.open.apptoolservice.app.service.ApptoolRecordInfoService;
import cn.com.open.apptoolservice.app.vo.MobileVerifyVo;
import cn.com.open.apptoolservice.app.zxpt.zx.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ApptoolRecordInfoServiceImpl implements ApptoolRecordInfoService {

    @Autowired
    private ApptoolRecordInfoMapper<ApptoolRecordInfo> apptoolRecordInfoMapper;

    @Override
    public boolean insert(ApptoolRecordInfo apptoolRecordInfo) {
        try {
            apptoolRecordInfoMapper.insert(apptoolRecordInfo);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

	@Override
	public boolean saveApptoolRecordInfo(HttpServletRequest request,MobileVerifyVo mobileVerifyVo) {
		try {
			  ApptoolRecordInfo apptoolRecordInfo=new ApptoolRecordInfo();
		   	  apptoolRecordInfo.setId(mobileVerifyVo.getId());
		   	  apptoolRecordInfo.setPhone(mobileVerifyVo.getNumber());
		   	  apptoolRecordInfo.setIdCard(mobileVerifyVo.getIdCard());
		   	  apptoolRecordInfo.setRealName(mobileVerifyVo.getRealName());
		   	  apptoolRecordInfo.setAppKey(request.getHeader(FormEnum.APPKEY.getCode()));
		   	  apptoolRecordInfo.setSourceUid(mobileVerifyVo.getSourceUid());
		   	  apptoolRecordInfo.setSourceUsername(mobileVerifyVo.getSourceUserName());
		   	  apptoolRecordInfo.setMerchantId(Integer.parseInt(mobileVerifyVo.getMerchantId()));
		   	  apptoolRecordInfo.setServiceName(request.getRequestURI().substring(request.getRequestURI().lastIndexOf('/') + 1));
			  apptoolRecordInfoMapper.insert(apptoolRecordInfo);
			  return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public ApptoolRecordInfo findApptoolRecordInfoById(String id) {
		return apptoolRecordInfoMapper.getById(id);
	}

	@Override
	public boolean updateApptoolRecordInfo(ApptoolRecordInfo apptoolRecordInfo) {
		try {
			apptoolRecordInfoMapper.updateBySelective(apptoolRecordInfo);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public ApptoolRecordInfo findByCondition(MobileVerifyVo mobileVerifyVo, Integer channelValue) {
		ApptoolRecordInfo example = new ApptoolRecordInfo();
		example.setIdCard(mobileVerifyVo.getIdCard());
		example.setRealName(mobileVerifyVo.getRealName());
		example.setPhone(mobileVerifyVo.getNumber());
		example.setChannelValue(channelValue);
		List<ApptoolRecordInfo> apptoolRecordInfoList = apptoolRecordInfoMapper.findBySelective(example);
    	return CollectionUtils.isEmpty(apptoolRecordInfoList) ? null : apptoolRecordInfoList.get(0);
	}

	@Override
	public boolean updateByCondition(MobileVerifyVo mobileVerifyVo, Integer verifyResult) {
		ApptoolRecordInfo example = new ApptoolRecordInfo();
		example.setIdCard(mobileVerifyVo.getIdCard());
		example.setRealName(mobileVerifyVo.getRealName());
		example.setPhone(mobileVerifyVo.getNumber());
		example.setChannelValue(Integer.valueOf(ServiceProviderEnum.AliyunMobileVerify.getValue()));
		List<ApptoolRecordInfo> apptoolRecordInfoList = apptoolRecordInfoMapper.findBySelective(example);

		if (CollectionUtils.isEmpty(apptoolRecordInfoList)) {
			return false;
		} else {
			ApptoolRecordInfo apptoolRecordInfo = apptoolRecordInfoList.get(0);
			apptoolRecordInfo.setVerifyResult(verifyResult);
			try {
				apptoolRecordInfoMapper.updateBySelective(apptoolRecordInfo);
				return true;
			} catch (Exception e) {
				return false;
			}
		}
	}

	@Override
	public void saveRecordInfo(HttpServletRequest request, Result result, String channelValue) {
		ApptoolRecordInfo apptoolRecordInfo = new ApptoolRecordInfo();
		apptoolRecordInfo.setId(DateUtil.getCurrentDateTime());
		apptoolRecordInfo.setAppKey(request.getHeader(FormEnum.APPKEY.getCode()));
		apptoolRecordInfo.setCreateTime(new Date());
		apptoolRecordInfo.setServiceName(request.getRequestURI().substring(request.getRequestURI().lastIndexOf('/') + 1));
		apptoolRecordInfo.setMerchantId(Integer.parseInt(request.getParameter("merchantId")));
		apptoolRecordInfo.setSourceUsername(request.getParameter("sourceUserName"));
		apptoolRecordInfo.setSourceUid(request.getParameter("sourceUid"));
		apptoolRecordInfo.setPhone(request.getParameter("number"));
		apptoolRecordInfo.setChannelValue(Integer.valueOf(channelValue));
		if (result == null)
			apptoolRecordInfo.setStatus(ImgRecordInfoStatus.exception.getCode()); //请求异常
		else if (Result.SUCCESS.equals(result.getStatus()))
			apptoolRecordInfo.setStatus(ImgRecordInfoStatus.success.getCode()); //请求成功
		else
			apptoolRecordInfo.setStatus(ImgRecordInfoStatus.fail.getCode()); //请求失败
		this.insert(apptoolRecordInfo);
	}
}
