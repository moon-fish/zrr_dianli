package org.jeecg.modules.dw.service.impl;

import org.jeecg.modules.dw.entity.Account;
import org.jeecg.modules.dw.mapper.AccountMapper;
import org.jeecg.modules.dw.service.IAccountService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 户号管理
 * @Author: jeecg-boot
 * @Date:   2020-09-22
 * @Version: V1.0
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements IAccountService {

}
