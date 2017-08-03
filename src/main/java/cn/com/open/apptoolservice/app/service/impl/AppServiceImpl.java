package cn.com.open.apptoolservice.app.service.impl;

import cn.com.open.apptoolservice.app.mapper.AppMapper;
import cn.com.open.apptoolservice.app.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AppServiceImpl implements AppService {

	@Autowired
	private AppMapper appMapper;

	@Override
	public String findAppSecretByAppkey(String appKey) {
		return appMapper.findAppSecretByAppkey(appKey);
	}

}
