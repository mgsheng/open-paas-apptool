package cn.com.open.apptoolservice.app.config;

import cn.com.open.apptoolservice.app.common.ServiceProviderEnum;
import cn.com.open.apptoolservice.app.interceptor.VerifySignatureInterceptor;
import cn.com.open.apptoolservice.app.service.PhoneService;
import cn.com.open.apptoolservice.app.service.impl.AliyunPhoneServiceImpl;
import cn.com.open.apptoolservice.app.service.impl.ZxMobileVerifyServiceImpl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 系统配置
 * Created by guxuyang on 05/07/2017.
 */
@Configuration
@EnableTransactionManagement
@MapperScan("cn.com.open.apptoolservice.app.mapper")
public class SystemConfig extends WebMvcConfigurerAdapter {

    @Value("${phone.attribution.service.provider}")
    private String phoneAttributionServiceProvider;
    @Value("${mobile.verify.service.provider}")
    private String mobileVerifyServiceProvider;

    @Bean
    public VerifySignatureInterceptor verifySignatureInterceptor() {
        return new VerifySignatureInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(verifySignatureInterceptor()).excludePathPatterns("/dnotdelet/mom.html");
        super.addInterceptors(registry);
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public PhoneService getPhoneService() {
        if (phoneAttributionServiceProvider.equals(ServiceProviderEnum.AliyunPhoneAttribution.getValue())) return new AliyunPhoneServiceImpl();
        return null;
    }

    @Bean
    public ZxMobileVerifyServiceImpl getMobileVerifyService() {
        if (mobileVerifyServiceProvider.equals(ServiceProviderEnum.TztMobileVerifyAttribution.getValue())) return new ZxMobileVerifyServiceImpl();
        return null;
    }

}


