package cn.com.open.apptoolservice.app.service.impl;

import cn.com.open.apptoolservice.app.common.FormEnum;
import cn.com.open.apptoolservice.app.common.ImgRecordInfoStatus;
import cn.com.open.apptoolservice.app.common.Result;
import cn.com.open.apptoolservice.app.entity.ApptoolRecordInfo;
import cn.com.open.apptoolservice.app.mapper.ApptoolRecordInfoMapper;
import cn.com.open.apptoolservice.app.service.ApptoolRecordInfoService;
import cn.com.open.apptoolservice.app.vo.MobileVerifyVo;
import cn.com.open.apptoolservice.app.zxpt.zx.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

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
    public void saveImageRecordInfo(HttpServletRequest request, Result result) {
        ApptoolRecordInfo apptoolRecordInfo = new ApptoolRecordInfo();
        apptoolRecordInfo.setId(DateUtil.getCurrentDateTime());
        apptoolRecordInfo.setAppKey(request.getHeader(FormEnum.APPKEY.getCode()));
        apptoolRecordInfo.setCreateTime(new Date());
        apptoolRecordInfo.setServiceName(request.getRequestURI().substring(request.getRequestURI().lastIndexOf('/') + 1));
        apptoolRecordInfo.setMerchantId(Integer.parseInt(request.getParameter("merchantId")));
        apptoolRecordInfo.setSourceUsername(request.getParameter("sourceUserName"));
        apptoolRecordInfo.setSourceUid(request.getParameter("sourceUid"));
        apptoolRecordInfo.setPhone(request.getParameter("number"));
        if (result == null)
            apptoolRecordInfo.setStatus(ImgRecordInfoStatus.exception.getCode()); //请求异常
        else if (Result.SUCCESS.equals(result.getStatus()))
            apptoolRecordInfo.setStatus(ImgRecordInfoStatus.success.getCode()); //请求成功
        else
            apptoolRecordInfo.setStatus(ImgRecordInfoStatus.fail.getCode()); //请求失败
        this.insert(apptoolRecordInfo);
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
}
