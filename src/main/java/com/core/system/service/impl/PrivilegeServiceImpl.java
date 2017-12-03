package com.core.system.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.core.base.util.Utils;
import com.core.security.filter.MyInvocationSecurityMetadataSource;
import com.core.system.dao.RolePrivilegeMapper;
import com.core.system.dto.PrivilegeDTO;
import com.core.system.model.Privilege;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;

import com.core.security.model.SpringSecurityUser;
import com.core.system.dao.PrivilegeMapper;
import com.core.system.model.PrivilegeExample;
import com.core.system.service.PrivilegeService;

@Service("privilegeService")
public class PrivilegeServiceImpl implements PrivilegeService {

	@Resource(name = "privilegeMapper")
	private PrivilegeMapper privilegeMapper;
	
	@Resource(name = "rolePrivilegeMapper")
	private RolePrivilegeMapper rolePrivilegeMapper;
	
	@Resource(name="sessionRegistry")
	private SessionRegistry sessionRegistry;

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return privilegeMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Privilege record) {
		return privilegeMapper.insert(record);
	}

	@Override
	public int insertSelective(Privilege record) {
		return privilegeMapper.insertSelective(record);
	}

	@Override
	public Privilege selectByPrimaryKey(Integer id) {
		return privilegeMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Privilege record) {
		return updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Privilege record) {
		return updateByPrimaryKey(record);
	}

	@Override
	public List<Privilege> getPrivilegeTree(String types) {
		return null;
	}

	@Override
	public Privilege getPrivilegeTreeById(int id) {
		return privilegeMapper.selectByPrimaryKey(id);
	}

	@Override
	public String deletePrivilege(int id) {
		String result;
		try {
			/*处理静态变量区 spring security*/
			Privilege privilege = privilegeMapper.selectByPrimaryKey(id);
	        Map<String, Collection<ConfigAttribute>> rMap = MyInvocationSecurityMetadataSource.getResourceMap();
	        Collection<ConfigAttribute> configTemp = rMap.get(privilege.getUrl());
	        if(configTemp==null) configTemp = new ArrayList<ConfigAttribute>();
	        Map<String, Collection<Privilege>> pMap = MyInvocationSecurityMetadataSource.getPrivilegeMap();
	        for (Iterator<ConfigAttribute> iterator = configTemp.iterator(); iterator.hasNext();) {
	        	ConfigAttribute configAttribute = (ConfigAttribute) iterator.next();
				Collection<Privilege> toDeal = pMap.get(configAttribute.getAttribute());
				for (Iterator<Privilege> iterator2 = toDeal.iterator(); iterator2.hasNext();) {
					Privilege privilegeLocal = (Privilege) iterator2.next();
					if(privilege.getUrl().equals(privilegeLocal.getUrl())) {
						toDeal.remove(privilegeLocal);
						break;
					}
				}
			} 
	        rMap.remove(privilege.getUrl());
	        
			//重置每个在线用户的userPrivileges，权限动态改变，权衡菜单的filer和改变权限发生的频率，选择在此操作
			List<Object> listPrincipals = sessionRegistry.getAllPrincipals();
			for (Iterator<Object> iterator = listPrincipals.iterator(); iterator.hasNext();) {
				SpringSecurityUser userDetails = (SpringSecurityUser) iterator.next();
				for (Iterator<Privilege> iterator2 = userDetails.getPrivileges().iterator(); iterator2.hasNext();) {
					Privilege pTemp = (Privilege) iterator2.next();
					if(pTemp.getUrl().equals(privilege.getUrl())){
						userDetails.getPrivileges().remove(pTemp);
						break;
					}
				}
			}
	        
	        // 找到要删除的权限
	        rolePrivilegeMapper.deleteByPId(id);	 //数据库层的外键参照，级联，完全可以不用手动删除rolePrivilege
	        privilegeMapper.deleteByPrimaryKey(id);
	        if(privilege.getParentId()!=null){
				//判断是否父节点要为叶节点
				boolean flag = false;
				Privilege pParent = privilegeMapper.selectByPrimaryKey(privilege.getParentId());
				List<Privilege> listP = privilegeMapper.getPrivilegeList();
				for (Iterator<Privilege> iterator = listP.iterator(); iterator.hasNext();) {
					Privilege pTemp = (Privilege) iterator.next();
					if(pTemp.getParentId()==pParent.getPrivilegeId()){
						flag = true;
						break;
					}
				}
				if(flag == false){
					pParent.setIsLeaf(true);
					privilegeMapper.updateByPrimaryKey(pParent);
				}
	        }
			result = "success";
		} catch (Exception e) {
			e.printStackTrace();
			result = "删除失败";
		}
		return result;
	}

	@Override
	public String modifyPrivilege(Privilege record) {
		String result;
		try {
			/*处理静态变量区 spring security*/
			Privilege privilege = privilegeMapper.selectByPrimaryKey(record.getPrivilegeId());
	        Map<String, Collection<ConfigAttribute>> rMap = MyInvocationSecurityMetadataSource.getResourceMap();
	        Collection<ConfigAttribute> configTemp = rMap.get(privilege.getUrl());
	        if(configTemp==null) configTemp = new ArrayList<ConfigAttribute>();//目前程序逻辑来讲，不会进入此if（无bug的话）
	        if (!privilege.getUrl().equals(record.getUrl())) {
	        	if(configTemp.size()==0) configTemp.add(new SecurityConfig("NO_ROLES_FOR_URL"));
				rMap.put(record.getUrl(), configTemp);
				rMap.remove(privilege.getUrl());
			}
			Map<String, Collection<Privilege>> pMap = MyInvocationSecurityMetadataSource.getPrivilegeMap();
	        for (Iterator<ConfigAttribute> iterator = configTemp.iterator(); iterator.hasNext();) {
	        	ConfigAttribute configAttribute = (ConfigAttribute) iterator.next();
				Collection<Privilege> toDeal = pMap.get(configAttribute.getAttribute());
				for (Iterator<Privilege> iterator2 = toDeal.iterator(); iterator2.hasNext();) {
					Privilege privilegeLocal = (Privilege) iterator2.next();
					if(privilege.getUrl().equals(privilegeLocal.getUrl())) {
						Utils.copyProperties(privilegeLocal, record);
						break;
					}
				}
			}
	        privilegeMapper.updateByPrimaryKeySelective(record);
			result = "success";
		} catch (Exception e) {
			e.printStackTrace();
			result = "failure";
		}
		return result;
	}

	@Override
	public String addPrivilege(Privilege record) {
		String result;
		try {
			Map<String, Collection<ConfigAttribute>> rMap = MyInvocationSecurityMetadataSource.getResourceMap();
			Collection<ConfigAttribute> addConfig = new ArrayList<ConfigAttribute>();
			addConfig.add(new SecurityConfig("NO_ROLES_FOR_URL"));//防止新增权限受访
			rMap.put(record.getUrl(), addConfig);
			
			Map<String,Collection<Privilege>> pMap = MyInvocationSecurityMetadataSource.getPrivilegeMap();
			Collection<Privilege> pTemp = pMap.get("NO_ROLES_FOR_URL");
			if(pTemp == null) {
				pTemp=new ArrayList<Privilege>();
				pTemp.add(record);
				pMap.put("NO_ROLES_FOR_URL", pTemp);
			} else pTemp.add(record);
			// 新增权限节点
			privilegeMapper.insert(record);
			if(record.getParentId()!=null){
				Privilege privilege = privilegeMapper.selectByPrimaryKey(record.getParentId());
				privilege.setIsLeaf(false);
				privilegeMapper.updateByPrimaryKey(privilege);
			}
			result = "success";
		} catch (Exception e) {
			e.printStackTrace();
			result = "failure";
		}
		return result;
	}

	@Override
	public int getTotalRecords(HttpServletRequest request, Boolean search) {
		PrivilegeExample ue = new PrivilegeExample();
		if (search) {
			addCriteria(request, ue);
		}
		return privilegeMapper.countByExample(ue);
	}

	@Override
	public List<PrivilegeDTO> listResults(int start, int limit,
                                          String sortName, String sortOrder, HttpServletRequest request,
                                          Boolean search) {
		PrivilegeExample pe = new PrivilegeExample();
		pe.setOrderByClause(sortName + " " + sortOrder);
		if (search) {
			addCriteria(request, pe);
		}

		List<Privilege> privilegeList = privilegeMapper.selectByExample(pe);

		List<PrivilegeDTO> PrivilegeDTOList = new ArrayList<PrivilegeDTO>();
		PrivilegeDTO privilegeDTO = null;

		for (Privilege privilege : privilegeList) {
			privilegeDTO = new PrivilegeDTO();
			privilegeDTO.setPrivilegeId(privilege.getPrivilegeId());
			privilegeDTO.setDescription(privilege.getDescription());
			privilegeDTO.setName(privilege.getName());
			privilegeDTO.setDisplay(privilege.getDisplay());
			privilegeDTO.setLevel(privilege.getLevel());
			privilegeDTO.setIsLeaf(privilege.getIsLeaf());
			privilegeDTO.setUrl(privilege.getUrl());
			privilegeDTO.setTarget(privilege.getTarget());
			privilegeDTO.setOrderNum(privilege.getOrderNum());
			privilegeDTO.setDisplay(privilege.getDisplay());
			privilegeDTO.setType(privilege.getType());
			PrivilegeDTOList.add(privilegeDTO);
		}

		return PrivilegeDTOList;
	}

	private void addCriteria(HttpServletRequest request, PrivilegeExample pe) {

		String privilege_id = request.getParameter("privilege_id");
		Integer parent_id = Integer.parseInt(request.getParameter("parent_id"));
		String description = request.getParameter("description");
		String name = request.getParameter("name");
		boolean display_name = Boolean.getBoolean(request
				.getParameter("display_name"));
		byte level = Byte.parseByte(request.getParameter("level"));
		boolean is_leaf = Boolean.getBoolean(request.getParameter("is_leaf"));
		String url = request.getParameter("url");
		String target = request.getParameter("target");
		Integer order_num = Integer.parseInt(request.getParameter("order_num"));
		boolean display = Boolean.getBoolean(request.getParameter("display"));
		String type = request.getParameter("type");

		PrivilegeExample.Criteria criteri = pe
				.createCriteria();
		if (privilege_id != null && !"".equals(privilege_id))
			criteri.andPrivilegeIdEqualTo(Integer.valueOf(privilege_id));
		if (parent_id != null && !"".equals(parent_id))
			criteri.andParentIdEqualTo(parent_id);
		if (description != null && !"".equals(description))
			criteri.andDescriptionEqualTo(description);
		if (name != null && !"".equals(name))
			criteri.andNameEqualTo(name);
		if (!"".equals(display_name))
			criteri.andDisplayEqualTo(display_name);
		if (!"".equals(level))
			criteri.andLevelEqualTo(level);
		if (!"".equals(is_leaf))
			criteri.andIsLeafEqualTo(is_leaf);
		if (url != null && !"".equals(url))
			criteri.andUrlEqualTo(url);
		if (target != null && !"".equals(target))
			criteri.andTargetEqualTo(target);
		if (order_num != null && !"".equals(order_num))
			criteri.andOrderNumEqualTo(order_num);
		if (!"".equals(display))
			criteri.andDisplayEqualTo(display);
		if (type != null && !"".equals(type))
			criteri.andTypeEqualTo(type);

	}

	// get和set方法
	public PrivilegeMapper getPrivilegeMapper() {
		return privilegeMapper;
	}

	public void setPrivilegeMapper(PrivilegeMapper privilegeMapper) {
		this.privilegeMapper = privilegeMapper;
	}

	public RolePrivilegeMapper getRolePrivilegeMapper() {
		return rolePrivilegeMapper;
	}

	public void setRolePrivilegeMapper(RolePrivilegeMapper rolePrivilegeMapper) {
		this.rolePrivilegeMapper = rolePrivilegeMapper;
	}

	@Override
	public List<Privilege> getPrivilegeIdsUrls() {
		return this.privilegeMapper.getPrivilegeList();
	}

}
