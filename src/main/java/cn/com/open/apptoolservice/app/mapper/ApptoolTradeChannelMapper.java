package cn.com.open.apptoolservice.app.mapper;

import cn.com.open.apptoolservice.app.entity.ApptoolTradeChannel;
import org.apache.ibatis.annotations.Select;

public interface ApptoolTradeChannelMapper {

    @Select("select id,channel_name as channelName,channel_status as channelStatus,priority,create_date as createDate," +
            "memo,key_value as keyValue,notify_url as notifyUrl,other,backurl,type,sign_type as signType,input_charset as inputCharset," +
            "request_url as requestUrl from apptool_trade_channel where channel_name = #{channelName}")
    ApptoolTradeChannel findByChannelName(String channelName);
}
