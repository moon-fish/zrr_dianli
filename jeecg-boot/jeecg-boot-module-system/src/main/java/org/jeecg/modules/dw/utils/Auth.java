package org.jeecg.modules.dw.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.dw.entity.UserInfoB;
import org.jeecg.modules.dw.service.IUserInfoBService;
import org.jeecg.modules.system.entity.SysRole;
import org.jeecg.modules.system.service.ISysRoleService;
import org.jeecg.modules.system.service.ISysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

@Component
public class Auth {
	@Autowired
	private ISysRoleService sysRoleService;
	@Autowired
	private ISysUserRoleService sysUserRoleService;
	@Autowired
	private IUserInfoBService userInfoBService;
	
	/**
	 * 根据登录的用户id判断其角色，再根据角色设置其权限  --权限分配
	 * param
	 * return role_code
	 */

	public String queryRoleCode() {
		//获取登录人信息
		LoginUser sysUser = (LoginUser)SecurityUtils.getSubject().getPrincipal();
		//登录人id
		String id = sysUser.getId();
		//登录人角色id
		String roleId = sysUserRoleService.queryByUserId(id);
		//登录人   角色信息
		SysRole sysrole = sysRoleService.getById(roleId);
		//登录人 角色code
		String roleCode = sysrole.getRoleCode();
		return roleCode;
	}
	
	public List<String> queryCompanys() {
		//权限设置 
		//根据登录人的信息查出区域内的公司名称，再根据公司名称过滤数据
		//获取登录人信息
		LoginUser sysUser = (LoginUser)SecurityUtils.getSubject().getPrincipal();
		//登录人id
		String id = sysUser.getId();
		//登录人角色id
		String roleId = sysUserRoleService.queryByUserId(id);
		//登录人   角色信息
		SysRole sysrole = sysRoleService.getById(roleId);
		//登录人 角色code
		String roleCode = sysrole.getRoleCode();
		QueryWrapper<UserInfoB> queryWrapperUserInfo = new QueryWrapper<UserInfoB>();
		//晋城、长治、阳泉区域   根据角色编码设置其区域 ---写死的，可能得改
		if(roleCode.equals("areaManagerMin")) {
			queryWrapperUserInfo.like("address_info", "晋城").or().like("address_info","长治").or().like("address_info","阳泉");
		}else if(roleCode.equals("areaManageMi")){
			//运城、忻州、吕梁、晋中
			queryWrapperUserInfo.like("address_info", "运城").or().like("address_info","忻州").or().like("address_info","吕梁").or().like("address_info","晋中");
		}
		List<UserInfoB> list = userInfoBService.list(queryWrapperUserInfo);
		List<String> companys = new ArrayList<String>();
		for(UserInfoB userInfoB : list) {
			companys.add(userInfoB.getComAName());
		}
		
		return companys;
	}
}
