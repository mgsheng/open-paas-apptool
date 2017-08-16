package cn.com.open.apptoolservice.app.log.support;

import java.util.Map;

public class AliyunResponseBean {

    private String json;
    private Map<String, String> heards;

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public Map<String, String> getHeards() {
        return heards;
    }

    public void setHeards(Map<String, String> heards) {
        this.heards = heards;
    }
}
