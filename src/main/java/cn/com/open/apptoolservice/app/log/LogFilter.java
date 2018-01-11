package cn.com.open.apptoolservice.app.log;


import cn.com.open.apptoolservice.app.common.ExceptionEnum;
import cn.com.open.apptoolservice.app.common.LogTypeEnum;
import cn.com.open.apptoolservice.app.common.Result;
import cn.com.open.apptoolservice.app.log.support.HttpServletResponseCopier;
import cn.com.open.apptoolservice.app.utils.DateUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Map;

public class LogFilter implements Filter {

    @Autowired
    private ApptoolServiceLogSender serviceLogSender;
    @Value("${apptool.base.request.url}")
    private String apptoolBaseRequestUrl;
    @Value("${apptool.api.log.onOff}")
    private String apptoolApiLogOnOff;

    @Override
    public void init(FilterConfig config) throws ServletException {
        //do nothing
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        if ("on".equals(apptoolApiLogOnOff)) {
            HttpServletRequest req = (HttpServletRequest) request;

            if (response.getCharacterEncoding() == null) {
                response.setCharacterEncoding("UTF-8");
            }
            long startTime = System.currentTimeMillis(); //请求开始时间
            HttpServletResponseCopier responseCopier = new HttpServletResponseCopier((HttpServletResponse) response);

            chain.doFilter(request, responseCopier);
            responseCopier.flushBuffer();

            byte[] copy = responseCopier.getCopy();
            long endTime = System.currentTimeMillis(); //请求结束时间

            JSONObject apiServiceLog = requestParamsToJSON(req);
            apiServiceLog.put("ip", getIpAddr(req));
            apiServiceLog.put("requestURL", req.getRequestURL().toString());
            apiServiceLog.put("appKey", req.getHeader("appKey"));
            apiServiceLog.put("createTime", DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
            apiServiceLog.put("httpMethod", req.getMethod());
            apiServiceLog.put("executionTime", (double)(endTime - startTime));
            apiServiceLog.put("requestPath", req.getRequestURI().replaceFirst(apptoolBaseRequestUrl, ""));
            apiServiceLog.put("httpResponseStatus", String.valueOf(responseCopier.getStatus()));
            apiServiceLog.put("logType", LogTypeEnum.SERVICE.getCode());

            apiServiceLog.put("isUseCache", responseCopier.getHeader("isUseCache"));
            apiServiceLog.put("channelValue", responseCopier.getHeader("channelValue"));
            apiServiceLog.put("logId", responseCopier.getHeader("logId"));

            if (copy.length > 0) { //出现异常则 copy数组长度为0
                String result = new String(copy, response.getCharacterEncoding());
                JSONObject jsonObject = JSONObject.parseObject(result);

                apiServiceLog.put("invokeStatus", jsonObject.getInteger("status"));
                apiServiceLog.put("errorCode", jsonObject.getString("errorCode"));
                apiServiceLog.put("errorMessage", jsonObject.getString("message"));
                apiServiceLog.put("responsePayload", jsonObject.getJSONObject("payload") == null ? null : jsonObject.getJSONObject("payload").toJSONString());
            } else { //处理出现异常
                apiServiceLog.put("invokeStatus", Result.ERROR);
                apiServiceLog.put("errorCode", ExceptionEnum.SysException.getCode());
                apiServiceLog.put("errorMessage", ExceptionEnum.SysException.getMessage());
            }
            serviceLogSender.sendServiceLog(apiServiceLog); //记录响应日志*/
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        //do nothing
    }

    /**
     * 请求参数转json
     * @param req req
     * @return string
     */
    private JSONObject requestParamsToJSON(ServletRequest req) {
        JSONObject jsonObj = new JSONObject();
        Map<String,String[]> params = req.getParameterMap();
        if (params == null || params.isEmpty()) {
            return jsonObj;
        }
        for (Map.Entry<String,String[]> entry : params.entrySet()) {
            String[] v = entry.getValue();
            Object o = (v.length == 1) ? v[0] : v;
            jsonObj.put(entry.getKey(), o);
        }
        return jsonObj;
    }

    /**
     * 获取IP地址
     *
     * @param request request
     * @return ip
     */
    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if (ip.equals("127.0.0.1")) {
                // 根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    System.err.println(e.getMessage());
                }
                if(null != inet){
                    ip = inet.getHostAddress();
                }
            }
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ip != null && ip.length() > 15) {
            if (ip.contains(",")) {
                ip = ip.substring(0, ip.indexOf(','));
            }
        }
        return ip;
    }

}