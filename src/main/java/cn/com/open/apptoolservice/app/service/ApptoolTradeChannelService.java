package cn.com.open.apptoolservice.app.service;

import cn.com.open.apptoolservice.app.entity.ApptoolTradeChannel;

public interface ApptoolTradeChannelService {

    ApptoolTradeChannel findByChannelName(String channelName);
}
