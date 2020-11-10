package org.jeecg.modules.dw.service.impl;

import org.jeecg.modules.dw.entity.UserInfoExport;
import org.jeecg.modules.dw.mapper.UserInfoExportMapper;
import org.jeecg.modules.dw.service.IUserInfoExportService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 用户信息导出包含分月电量初始信息
 * @Author: jeecg-boot
 * @Date:   2020-07-15
 * @Version: V1.0
 */
@Service
public class UserInfoExportServiceImpl extends ServiceImpl<UserInfoExportMapper, UserInfoExport> implements IUserInfoExportService {

}
