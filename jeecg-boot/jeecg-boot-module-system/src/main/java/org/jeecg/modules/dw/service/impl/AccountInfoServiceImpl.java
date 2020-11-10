package org.jeecg.modules.dw.service.impl;

import java.util.Collection;

import org.jeecg.modules.dw.entity.AccountInfo;
import org.jeecg.modules.dw.mapper.AccountInfoMapper;
import org.jeecg.modules.dw.service.IAccountInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 户号信息
 * @Author: jeecg-boot
 * @Date:   2020-09-22
 * @Version: V1.0
 */
@Service
public class AccountInfoServiceImpl extends ServiceImpl<AccountInfoMapper, AccountInfo> implements IAccountInfoService {

//	@Autowired
//	private AccountInfoMapper accountInfoMapper;
//	
//	@Override
//	public boolean updateBatch(Collection<AccountInfo> entityList) {
//		// TODO Auto-generated method stub
//		accountInfoMapper.updateBatch(entityList);
//		return true;
//	}

}
