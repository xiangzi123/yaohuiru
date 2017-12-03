package com.core.system.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.core.system.dto.RoleAddForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.base.action.JqGridBaseAction;
import com.core.system.dto.RoleCheckForm;
import com.core.system.dto.RoleDTO;
import com.core.system.dto.RoleModifyForm;
import com.core.system.model.Role;
import com.core.system.service.RoleService;

@Controller
@RequestMapping("/system/role")
public class RoleAction extends JqGridBaseAction<RoleDTO>{
	
//	private final Log log = LogFactory.getLog(getClass());
//	private boolean debug = log.isDebugEnabled();
	
	//将spring 配置文件中的bean 通过setter注入进来
	@Resource(name="roleService")
	private RoleService roleService=null;

	@Override
	public List<RoleDTO> listResults(int start, int limit, String sortName,
			String sortOrder, HttpServletRequest request, Boolean search) {
		return this.roleService.listResults(start, limit, sortName, sortOrder, request,search);
	}

	@Override
	public Integer getTotalRecords(HttpServletRequest request, Boolean search) {
		return roleService.getTotalRecords(request,search);
	}	
	
	@RequestMapping("/validateRoleId.do")
	@ResponseBody
	public boolean validateRoleId(@RequestParam(value="role_id") Integer role_id){
		boolean validate = true;
		Role r = this.roleService.validateExist(role_id);
		if(r!=null) validate=false;
		return validate;
	}
	@RequestMapping("/validateRoleName.do")
	@ResponseBody
	public boolean validateRoleName(@RequestParam(value="name") String roleName,
			@RequestParam(value="oldRoleName") String oldRoleName){
		boolean validate = true;
		if(oldRoleName.equals(roleName)) 
			return validate;
		else{
			Role r = this.roleService.validateNameExist(roleName);
			if(r!=null) validate=false;
			return validate;
		}
	}
	@RequestMapping("/addRole.do")
	@ResponseBody
	public boolean addRole(@ModelAttribute("addRoleForm") RoleAddForm roleAddForm){
		boolean validate = true;
		if(!this.roleService.insertRoles(roleAddForm)) validate=false;
		return validate;
	}
	
	@RequestMapping("/modifyRoleIn.do")
	@ResponseBody
	public RoleModifyForm modifyRoleIn(@RequestParam(value="roleId") Integer roleId){
		RoleModifyForm roleModifyForm;
		roleModifyForm = this.roleService.getRolesAndInfo(roleId) ;
		return roleModifyForm;
	}
	
	@RequestMapping("/modifyRoleOut.do")
	@ResponseBody
	public boolean modifyRoleOut(@ModelAttribute("modifyRoleForm") RoleModifyForm roleModifyForm){
		boolean validate = true;
		if(!this.roleService.modifyRole(roleModifyForm)) validate=false;
		return validate;
	}
	
	@RequestMapping("/savePrivilegeChange.do")
	@ResponseBody
	public boolean savePrivilegeChange(@RequestParam(value="roleId") Integer roleId,@RequestParam(value="privliege")String privliege){
		boolean validate = true;
		if(!this.roleService.updateRoles(roleId,privliege)) validate=false;
		return validate;
	}
	
	@RequestMapping("/checkRole.do")
	@ResponseBody
	public RoleCheckForm checkRoleIn(@RequestParam(value="roleId") Integer roleId){
		RoleCheckForm roleCheckForm;
		roleCheckForm = this.roleService.getInfoForCheck(roleId) ;
		return roleCheckForm;
	}
	
	@RequestMapping("/deleteRole.do")
	@ResponseBody
	public String deleteRole(@RequestParam(value="roleId") Integer roleId){		
		return this.roleService.deleteRole(roleId);
	}
	
	@RequestMapping("/info.do")
	@ResponseBody
	public Map<String, List<?>> getPrivilegeInfo(){
		Map<String, List<?>> map = new HashMap<String, List<?>>();
		map.put("privilege", this.roleService.getAllPrivileges());
		return map;
	}
	
	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
}
