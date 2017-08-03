package cn.com.open.apptoolservice.app.service.impl;

import cn.com.open.apptoolservice.app.common.ImgRecordInfoStatus;
import cn.com.open.apptoolservice.app.common.Result;
import cn.com.open.apptoolservice.app.entity.ApptoolRecordInfo;
import cn.com.open.apptoolservice.app.mapper.ApptoolRecordInfoMapper;
import cn.com.open.apptoolservice.app.service.ApptoolRecordInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;

@Service
@Transactional
public class ApptoolRecordInfoServiceImpl implements ApptoolRecordInfoService {

    @Autowired
    private ApptoolRecordInfoMapper apptoolRecordInfoMapper;

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
        apptoolRecordInfo.setId(UUID.randomUUID().toString().replace("-", ""));
        apptoolRecordInfo.setAppKey(request.getHeader("appKey"));
        apptoolRecordInfo.setCreateTime(new Date());
        apptoolRecordInfo.setServiceName(request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/") + 1));
        apptoolRecordInfo.setMerchantId(Integer.parseInt(request.getParameter("merchantId")));
        apptoolRecordInfo.setSourceUsername(request.getParameter("sourceUserName"));
        apptoolRecordInfo.setSourceUid(request.getParameter("sourceUid"));
        if (result == null)
            apptoolRecordInfo.setStatus(ImgRecordInfoStatus.exception.getCode()); //请求异常
        else if (Result.SUCCESS.equals(result.getStatus()))
            apptoolRecordInfo.setStatus(ImgRecordInfoStatus.success.getCode()); //请求成功
        else
            apptoolRecordInfo.setStatus(ImgRecordInfoStatus.fail.getCode()); //请求失败
        this.insert(apptoolRecordInfo);
    }
}
