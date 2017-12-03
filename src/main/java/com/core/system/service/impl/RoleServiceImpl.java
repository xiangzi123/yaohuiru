package com.core.system.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.core.security.filter.MyInvocationSecurityMetadataSource;
import com.core.system.dao.RoleMapper;
import com.core.system.dao.RolePrivilegeMapper;
import com.core.system.dao.UserRoleMapper;
import com.core.system.dto.RoleAddForm;
import com.core.system.dto.RoleCheckForm;
import com.core.system.dto.RoleDTO;
import com.core.system.service.RoleService;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.core.base.util.SystemConstants;
import com.core.security.model.SpringSecurityUser;
import com.core.system.dao.PrivilegeMapper;
import com.core.system.dto.RoleModifyForm;
import com.core.system.model.Privilege;
import com.core.system.model.PrivilegeExample;
import com.core.system.model.Role;
import com.core.system.model.RoleExample;
import com.core.system.model.RolePrivilege;
import com.core.system.model.RolePrivilegeExample;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

	@Resource(name = "roleMapper")
	private RoleMapper roleMapper;
	@Resource(name = "privilegeMapper")
	private PrivilegeMapper privilegeMapper;
	@Resource(name = "rolePrivilegeMapper")
	private RolePrivilegeMapper rolePrivilegeMapper;
	@Resource(name="userRoleMapper")
	private UserRoleMapper userRoleMapper;
	@Resource(name="sessionRegistry")
	private SessionRegistry sessionRegistry;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		return roleMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Role record) {
		return 0;
	}

	@Override
	public int insertSelective(Role record) {
		return 0;
	}

	@Override
	public Role selectByPrimaryKey(Integer id) {
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(Role record) {
		return 0;
	}

	@Override
	public int updateByPrimaryKey(Role record) {
		return 0;
	}

	@Override
	public int getTotalRecords(HttpServletRequest request, Boolean search) {
		RoleExample ue = new RoleExample();
		if (search) {
			addCriteria(request, ue);
		}
		return roleMapper.countByExample(ue);
	}

	@Override
	public List<RoleDTO> listResults(int start, int limit, String sortName,
                                     String sortOrder, HttpServletRequest request, Boolean search) {
		RoleExample ue = new RoleExample();
		ue.setOrderByClause(sortName + " " + sortOrder);
		ue.setStart(start);
		ue.setLimit(limit);

		if (search) {
			addCriteria(request, ue);
		}

		/* End */
		List<Role> roleList = roleMapper.selectByExample(ue);
		List<RoleDTO> roleDTOList = new ArrayList<RoleDTO>();
		for (Role role : roleList) {
			RoleDTO tempRoleDTO = new RoleDTO();
			// Utils.copyProperties(tempRoleDTO, role);
			// roleDTOList.add(tempRoleDTO);
			tempRoleDTO.setRoleId(role.getRoleId());
			tempRoleDTO.setName(role.getName());
			tempRoleDTO.setDescription(role.getDescription());
			roleDTOList.add(tempRoleDTO);
		}

		return roleDTOList;
	}

	private void addCriteria(HttpServletRequest request, RoleExample ue) {
		String rId = request.getParameter("roleId");
		Integer roleId = null;
		if (rId != null && !"".equals(rId))
			roleId = Integer.parseInt(rId);
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		String pId = request.getParameter("parentId");
		String privilege = request.getParameter("privilege");
		String organ = request.getParameter("organ");
		Integer parentId = null;
		if (pId != null && !"".equals(pId))
			parentId = Integer.parseInt(pId);
		RoleExample.Criteria criteria = ue.createCriteria();
		if (roleId != null && !"".equals(roleId))
			criteria.andRoleIdEqualTo(roleId);
		if (name != null && !"".equals(name))
			criteria.andNameLike("%"+name+"%");
		if (description != null && !"".equals(description))
			criteria.andDescriptionLike("%"+description+"%");
		if (parentId != null && !"".equals(parentId))
			criteria.andParentIdEqualTo(parentId);
		if (privilege != null && !"".equals(privilege))
			criteria.andPrilegeEqualTo(privilege);
		if (organ != null && !"".equals(organ))
			criteria.andDepartmentEqualTo(organ);
	}

	public RoleMapper getRoleMapper() {
		return roleMapper;
	}

	public void setRoleMapper(RoleMapper roleMapper) {
		this.roleMapper = roleMapper;
	}

	@Override
	public Role validateExist(Integer role_id) {
		RoleExample re = new RoleExample();
		re.createCriteria().andRoleIdEqualTo(role_id);
		Role role = this.roleMapper.selectByExample(re).size() == 0 ? null
				: this.roleMapper.selectByExample(re).get(0);
		return role;
	}

	@Override
	public boolean insertRoles(RoleAddForm roleAddForm) {
		boolean result = true;
		try {
			/*
			 * 增加角色
			 */
			//增加角色的时候，默认给主界面访问的权限，即 可登陆。
			Map<String, Collection<ConfigAttribute>> rMap = MyInvocationSecurityMetadataSource.getResourceMap();
			rMap.get(SystemConstants.MAIN_PAGE).add(new SecurityConfig(roleAddForm.getName()));
			
			Map<String, Collection<Privilege>> pMap = MyInvocationSecurityMetadataSource.getPrivilegeMap();
			Collection<Privilege> tempPrivilege = new ArrayList<Privilege>();
			Privilege pTemp = new Privilege();
			pTemp.setUrl(SystemConstants.MAIN_PAGE);
			tempPrivilege.add(pTemp);
			pMap.put(roleAddForm.getName(), tempPrivilege);
			/************************************************************/
			Role role = new Role();
			role.setRoleId(roleAddForm.getRole_id());
			role.setName(roleAddForm.getName());
			role.setDescription(roleAddForm.getDescription());
			role.setParentId(roleAddForm.getParentId());
			roleMapper.insertSelective(role);
			int roleId = role.getRoleId();
			if (roleAddForm.getRoleSelectR() != null) {
				String[] privileges = roleAddForm.getRoleSelectR().split(",");
				for (int i = 0; i < privileges.length; i++) {
					Integer privilegeId = Integer.valueOf(privileges[i]);
					RolePrivilege rolePrivilege = new RolePrivilege();
					rolePrivilege.setPrivilegeId(privilegeId);
					rolePrivilege.setRoleId(roleId);
					// userRole.setRoleId(roleId);
					// userRole.setUserId(userId);
					rolePrivilegeMapper.insert(rolePrivilege);
				}
			}
		} catch (NumberFormatException e) {
			result = false;
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public RoleModifyForm getRolesAndInfo(Integer roleId) {
		RoleModifyForm roleModifyForm = new RoleModifyForm();
		Integer roleid = roleId;
		Role role = this.roleMapper.selectByPrimaryKey(roleid);
		roleModifyForm.setRoleId(role.getRoleId());
		roleModifyForm.setName(role.getName());
		roleModifyForm.setDescription(role.getDescription());
		roleModifyForm.setParentId(role.getParentId());

		String[] privilegeIds = this.rolePrivilegeMapper.getPrivilegeIdsForRole(roleid);
		if (privilegeIds.length != 0) {
			String combineIds = Arrays.toString(privilegeIds);
			String temp = combineIds.substring(1, combineIds.length() - 1);
			temp = temp.replaceAll(" ", "");
			roleModifyForm.setRoleSelectR(temp);
		}
		return roleModifyForm;
	}

	@Override
	public boolean modifyRole(RoleModifyForm roleModifyForm) {
		boolean result = true;
		String origName  = null;
		try {
			Role role = this.roleMapper.selectByPrimaryKey(roleModifyForm.getRoleId());
			origName = role.getName();
			role.setName(roleModifyForm.getName());
			role.setDescription(roleModifyForm.getDescription());
			role.setParentId(roleModifyForm.getParentId());
			this.roleMapper.updateByPrimaryKey(role);
		
			//修改线上用户此角色授权
			List<Object> listPrincipals = sessionRegistry.getAllPrincipals();
			for (Iterator<Object> iterator = listPrincipals.iterator(); iterator.hasNext();) {
				SpringSecurityUser userDetails = (SpringSecurityUser) iterator.next();
				for (Iterator<GrantedAuthority> iterator2 = userDetails.getAuthorities().iterator(); iterator2.hasNext();) {
					GrantedAuthority grantedAuthority = (GrantedAuthority) iterator2.next();
					if(grantedAuthority.getAuthority().equals(origName)){
						userDetails.getAuthorities().remove(grantedAuthority);
						userDetails.getAuthorities().add(new SimpleGrantedAuthority(roleModifyForm.getName()));
						break;
					}
				}
			}
			
			Map<String, Collection<Privilege>> pMap = MyInvocationSecurityMetadataSource.getPrivilegeMap();
			Collection<Privilege> tempPrivilege = pMap.get(origName);
			pMap.remove(origName);
			pMap.put(roleModifyForm.getName(), tempPrivilege);
			
			Map<String, Collection<ConfigAttribute>> rMap = MyInvocationSecurityMetadataSource.getResourceMap();
			for (Iterator<Privilege> iterator = tempPrivilege.iterator(); iterator.hasNext();) {
				Privilege privilege = (Privilege) iterator.next();
				Collection<ConfigAttribute>rTemp = rMap.get(privilege.getUrl());
				for (Iterator<ConfigAttribute> iterator2 = rTemp.iterator(); iterator2.hasNext();) {
					ConfigAttribute configAttribute = (ConfigAttribute) iterator2.next();
					if(configAttribute.getAttribute().equals(origName)) {
						rTemp.remove(configAttribute);
						rTemp.add(new SecurityConfig(roleModifyForm.getName()));
						break;
					}
				}
			}
		}
		catch (NumberFormatException e) {
			result = false;
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public RoleCheckForm getInfoForCheck(Integer roleId) {
		RoleCheckForm roleCheckForm = new RoleCheckForm();
		Role role = this.roleMapper.selectByPrimaryKey(roleId);
		roleCheckForm.setRoleId(role.getRoleId());
		roleCheckForm.setName(role.getName());
		roleCheckForm.setDescription(role.getDescription());
		roleCheckForm.setParentId(role.getParentId());

		Integer roleid = role.getRoleId();

		String[] privilegeIds = this.rolePrivilegeMapper
				.getPrivilegeIdsForRole(roleid);
		if (privilegeIds.length != 0) {
			String combineIds = Arrays.toString(privilegeIds);
			String temp = combineIds.substring(1, combineIds.length() - 1);
			temp = temp.replaceAll(" ", "");
			roleCheckForm.setRoleSelectR(temp);
		}
		return roleCheckForm;

	}

	@Override
	public String deleteRole(Integer roleId) {
		String result = SystemConstants.SUCCESS;
		try {
			if(userRoleMapper.getNumOfUsersForRole(roleId)!=0) return SystemConstants.REFUSE;
			//删除内存静态变量
			Role role = roleMapper.selectByPrimaryKey(roleId);
			Collection<Privilege> privilegesTemp;
			Map<String, Collection<Privilege>> pMap = MyInvocationSecurityMetadataSource.getPrivilegeMap();
			privilegesTemp=pMap.get(role.getName());
			boolean flagForRoleNotInRom = false;
	        if(privilegesTemp==null) {
	        	privilegesTemp = new ArrayList<Privilege>();
	        	flagForRoleNotInRom =true;
	        }
	        Map<String, Collection<ConfigAttribute>> rMap = MyInvocationSecurityMetadataSource.getResourceMap();
	        for (Iterator<Privilege> iterator = privilegesTemp.iterator(); iterator.hasNext();) {
				Privilege privilege = (Privilege) iterator.next();
				Collection<ConfigAttribute> toDeal = rMap.get(privilege.getUrl());
				for (Iterator<ConfigAttribute> iterator2 = toDeal.iterator(); iterator2.hasNext();) {
					ConfigAttribute configAttribute = (ConfigAttribute) iterator2.next();
					if(configAttribute.getAttribute().equals(role.getName())) {
						toDeal.remove(configAttribute);
						if(!privilege.getUrl().equals(SystemConstants.MAIN_PAGE)&&toDeal.size()==0) {
							if(pMap.get("NO_ROLES_FOR_URL")==null) {
								Collection<Privilege> pTemp = new ArrayList<Privilege>();
								pTemp.add(privilege);
								pMap.put("NO_ROLES_FOR_URL", pTemp);
							} else 
								pMap.get("NO_ROLES_FOR_URL").add(privilege);
							ConfigAttribute ca = new SecurityConfig("NO_ROLES_FOR_URL");
							toDeal.add(ca);
						}
						break;
					}
				}
			}  
			if(flagForRoleNotInRom == false) pMap.remove(role.getName());
			else privilegesTemp.clear();
	        
			//清空线上用户此角色授权
			List<Object> listPrincipals = sessionRegistry.getAllPrincipals();
			for (Iterator<Object> iterator = listPrincipals.iterator(); iterator.hasNext();) {
				SpringSecurityUser userDetails = (SpringSecurityUser) iterator.next();
				for (Iterator<GrantedAuthority> iterator2 = userDetails.getAuthorities().iterator(); iterator2.hasNext();) {
					GrantedAuthority grantedAuthority = (GrantedAuthority) iterator2.next();
					if(grantedAuthority.getAuthority().equals(role.getName())){
						userDetails.getAuthorities().remove(grantedAuthority);
						break;
					}
				}
			}
			
			RolePrivilegeExample rpe = new RolePrivilegeExample();
			rpe.or().andRoleIdEqualTo(roleId);
			this.rolePrivilegeMapper.deleteByExample(rpe); //数据库层的外键参照，级联，完全可以不用手动删除
			this.userRoleMapper.deleteByRoleId(roleId); //数据库层的外键参照，级联，完全可以不用手动删除
	        this.roleMapper.deleteByPrimaryKey(roleId);
		} catch (RuntimeException e) {
			result = SystemConstants.FAILURE;
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return result;
	}

	@Override
	public List<Privilege> getAllPrivileges() {
		PrivilegeExample pe = new PrivilegeExample();
		List<Privilege> privileges = privilegeMapper.selectByExample(pe);
		return privileges;
	}

	@Override
	public boolean updateRoles(Integer roleId, String privliege) {
		boolean result = true;
		try {
			// 先删除该角色原有权限信息
			rolePrivilegeMapper.delete(roleId);
			//spring security 需要动态修改resourceMap和privilegeMap			
			Role role = roleMapper.selectByPrimaryKey(roleId);
			Collection<Privilege> privilegesTemp;
			Map<String, Collection<Privilege>> pMap = MyInvocationSecurityMetadataSource.getPrivilegeMap();
			privilegesTemp=pMap.get(role.getName());//新增的角色由于配置了主页的访问地址，不考虑privilegeTemp为null的情况了,但是还是应该加上判空,因为无角色用户的登录
			boolean flagForRoleNotInRom = false;
			if(privilegesTemp==null) {
				privilegesTemp = new ArrayList<Privilege>();
				flagForRoleNotInRom=true;
			}
	        Map<String, Collection<ConfigAttribute>> rMap = MyInvocationSecurityMetadataSource.getResourceMap();
	        for (Iterator<Privilege> iterator = privilegesTemp.iterator(); iterator.hasNext();) {
				Privilege privilege = (Privilege) iterator.next();
				Collection<ConfigAttribute> toDeal = rMap.get(privilege.getUrl());
				for (Iterator<ConfigAttribute> iterator2 = toDeal.iterator(); iterator2.hasNext();) {
					ConfigAttribute configAttribute = (ConfigAttribute) iterator2.next();
					if(configAttribute.getAttribute().equals(role.getName())) {
						toDeal.remove(configAttribute);
						if(!privilege.getUrl().equals(SystemConstants.MAIN_PAGE)&&toDeal.size()==0) {
							//主页面的配置维护到privilegeMap中，所以size为2，表示此权限将没有角色对应
							if(pMap.get("NO_ROLES_FOR_URL")==null) {
								Collection<Privilege> pTemp = new ArrayList<Privilege>();
								pTemp.add(privilege);
								pMap.put("NO_ROLES_FOR_URL", pTemp);
							} else 
								pMap.get("NO_ROLES_FOR_URL").add(privilege);
							ConfigAttribute ca = new SecurityConfig("NO_ROLES_FOR_URL");
							toDeal.add(ca);
						}
						break;
					}
				}
			} 
	        
	        privilegesTemp.clear();
			Privilege ptemp = new Privilege();
			ptemp.setUrl(SystemConstants.MAIN_PAGE);
			privilegesTemp.add(ptemp);

			// 如果没有任何权限，直接跳出
			if (privliege == null) {
				return false;
			}
			if (privliege.equals("")) {
				return true;
			}
			// 权限id传入格式e.g:3,4
			String[] privileges = privliege.split(",");

			// 再重新插入权限信息
			for (int i = 0; i < privileges.length; i++) {
				Integer privilegeId = Integer.valueOf(privileges[i]);
				RolePrivilege rolePrivilege = new RolePrivilege();
				rolePrivilege.setRoleId(roleId);
				rolePrivilege.setPrivilegeId(privilegeId);
				rolePrivilegeMapper.insert(rolePrivilege);
				//重新加入权限，也需要动态修改resourceMap和privilegeMap	
				Privilege pe = privilegeMapper.selectByPrimaryKey(privilegeId);
				privilegesTemp.add(pe);
				Collection<ConfigAttribute> caTemp = rMap.get(pe.getUrl());
				if(caTemp.size()==1){
					for (Iterator<ConfigAttribute> iterator = caTemp.iterator(); iterator.hasNext();) {
						ConfigAttribute ca = (ConfigAttribute) iterator.next();
						if (ca.getAttribute().equals("NO_ROLES_FOR_URL")) {
							caTemp.remove(ca);
							caTemp.add(new SecurityConfig(role.getName()));
							break;
						}
						else {
							caTemp.add(new SecurityConfig(role.getName()));
							break;
						}
					}
				}
				else {
					caTemp.add(new SecurityConfig(role.getName()));
				}
			}
			rMap.get(SystemConstants.MAIN_PAGE).add(new SecurityConfig(role.getName()));
			if(flagForRoleNotInRom==true) pMap.put(role.getName(), privilegesTemp);
			
			//重置线上用户权限
			List<Object> listPrincipals = sessionRegistry.getAllPrincipals();
			for (Iterator<Object> iterator = listPrincipals.iterator(); iterator.hasNext();) {
				SpringSecurityUser userDetails = (SpringSecurityUser) iterator.next();
				for (Iterator<GrantedAuthority> iterator2 = userDetails.getAuthorities().iterator(); iterator2.hasNext();) {
					GrantedAuthority grantedAuthority = (GrantedAuthority) iterator2.next();
					if(grantedAuthority.getAuthority().equals(role.getName())){
						userDetails.getPrivileges().clear();
						for (Iterator<GrantedAuthority> iterator3 = userDetails.getAuthorities().iterator(); iterator3.hasNext();) {
							GrantedAuthority gTemp = (GrantedAuthority) iterator3.next();
				 	        Collection<Privilege> temp = MyInvocationSecurityMetadataSource.getPrivilegeMap().get(gTemp.getAuthority());
				 	        if(temp!=null) userDetails.getPrivileges().addAll(temp);
				 	    }
						break;
					}
				}
			}			
		}

		catch (NumberFormatException e) {
			result = false;
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Role> getAllRolesForPrivilege(Integer privliegeId) {
		RoleExample roleExample = new RoleExample();
		roleExample.or().andPrilegeEqualTo(privliegeId.toString());
		List<Role> roleList = roleMapper.selectByExample(roleExample);
		return roleList;
	}

	@Override
	public Role validateNameExist(String roleName) {
		RoleExample re = new RoleExample();
		re.createCriteria().andNameEqualTo(roleName);
		Role role = this.roleMapper.selectByExample(re).size() == 0 ? null
				: this.roleMapper.selectByExample(re).get(0);
		return role; 
	} 
}
