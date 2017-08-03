package cn.com.open.apptoolservice.app.service.impl;

import cn.com.open.apptoolservice.app.entity.ApptoolTradeChannel;
import cn.com.open.apptoolservice.app.mapper.ApptoolTradeChannelMapper;
import cn.com.open.apptoolservice.app.service.ApptoolTradeChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ApptoolTradeChannelServiceImpl implements ApptoolTradeChannelService {

	@Autowired
    private ApptoolTradeChannelMapper apptoolTradeChannelMapper;

    @Override
    public ApptoolTradeChannel findByChannelName(String channelName) {
        return apptoolTradeChannelMapper.findByChannelName(channelName);
    }
}
