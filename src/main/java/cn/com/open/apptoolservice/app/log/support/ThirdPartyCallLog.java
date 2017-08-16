package cn.com.open.apptoolservice.app.log.support;

public class ThirdPartyCallLog {

    private String logType;
    private String createTime;
    private double executionTime;
    private String responseText;
    private String channelValue;
    private String channelName;
    private String responseHeaderParam;

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public double getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(double executionTime) {
        this.executionTime = executionTime;
    }

    public String getResponseText() {
        return responseText;
    }

    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }

    public String getChannelValue() {
        return channelValue;
    }

    public void setChannelValue(String channelValue) {
        this.channelValue = channelValue;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getResponseHeaderParam() {
        return responseHeaderParam;
    }

    public void setResponseHeaderParam(String responseHeaderParam) {
        this.responseHeaderParam = responseHeaderParam;
    }
}
