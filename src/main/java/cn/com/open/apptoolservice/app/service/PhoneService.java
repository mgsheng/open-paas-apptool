package cn.com.open.apptoolservice.app.service;

import cn.com.open.apptoolservice.app.common.Result;

public interface PhoneService {
    Result attribution(String number) throws Exception;
}