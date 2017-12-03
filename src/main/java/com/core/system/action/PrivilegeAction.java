package com.core.system.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/*import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;*/
import com.core.system.dto.PrivilegeDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.base.action.JqGridBaseAction;
import com.core.base.util.Utils;
import com.core.system.dao.PrivilegeMapper;
import com.core.system.model.Privilege;
import com.core.system.service.PrivilegeService;

@Controller
@RequestMapping("/system/privilege")
public class PrivilegeAction extends JqGridBaseAction<PrivilegeDTO> {
	@Resource(name = "privilegeMapper")
	private PrivilegeMapper privilegeMapper;
	@Resource(name = "privilegeService")
	private PrivilegeService privilegeService;

	// 在zTree上显示权限
	@RequestMapping("/privilegeList.do")
	@ResponseBody
	public List<Privilege> getPrivilegeTreeList() throws Exception {
		//return Connector.getPrivilegeList();
		return privilegeMapper.getPrivilegeList();
	}

	// 增加权限
	@RequestMapping("/addPrivilege.do")
	@ResponseBody
	public Map<Object,Object> addPrivilege(HttpServletRequest request) {
		
		boolean isLeaf=true;
		Integer parentId = null;
		if(!request.getParameter("parentId").equals("")&&!request.getParameter("parentId").equals(null)){
			parentId = Integer.parseInt(request.getParameter("parentId"));
			if(parentId==0) parentId=null;
		}
		Byte level = 0;
		if(!request.getParameter("level").equals("")&&!request.getParameter("level").equals(null))
			level = (byte) (Byte.valueOf(request.getParameter("level"))+1);
		String description = request.getParameter("description");
		String privilegeName = request.getParameter("name");
		String displayName = request.getParameter("displayName")==null?"":request.getParameter("displayName");
		String url = request.getParameter("url");
		String target = request.getParameter("target");
		String type = request.getParameter("type");

		Privilege dtoPrivilege = new Privilege();
		dtoPrivilege.setDescription(description);
		dtoPrivilege.setParentId(parentId);
		dtoPrivilege.setName(privilegeName);
		dtoPrivilege.setDisplayName(displayName);
		dtoPrivilege.setLevel(level);
		dtoPrivilege.setIsLeaf(isLeaf);
		dtoPrivilege.setUrl(url);
		dtoPrivilege.setTarget(target);
		dtoPrivilege.setType(type);
		
		Map<Object,Object> resultMap = new HashMap<Object,Object>();
		try {
			privilegeService.addPrivilege(dtoPrivilege);
			resultMap.put("result", "success");
			resultMap.put("identity", dtoPrivilege);
		} catch (Exception e) {
			resultMap.put("result", "failure");
		}
		return resultMap;
	}

	// 修改权限
	@RequestMapping("/modifyPrivilege.do")
	@ResponseBody
	public Map<Object,Object> modifyPrivilege(HttpServletRequest request) {

		Integer privilegeId = Integer.parseInt(request.getParameter("privilegeId"));
		Integer parentId = null;
		if(!Utils.isEmptyOrNull(request.getParameter("parentId")))
			parentId= Integer.parseInt(request.getParameter("parentId"));
		if(parentId==0) parentId = null;
		Byte level = Byte.parseByte(request.getParameter("level"));
		String description = request.getParameter("description");
		String privilegeName = request.getParameter("name");
		String displayName = request.getParameter("displayName");
		String url = request.getParameter("url");
		String target = request.getParameter("target");
		String type = request.getParameter("type");
		
		Privilege dtoPrivilege = new Privilege();
		dtoPrivilege.setPrivilegeId(privilegeId);
		dtoPrivilege.setName(privilegeName);
		dtoPrivilege.setDescription(description);
		dtoPrivilege.setDisplayName(displayName);
		dtoPrivilege.setUrl(url);
		dtoPrivilege.setParentId(parentId);
		dtoPrivilege.setTarget(target);
		dtoPrivilege.setType(type);
		dtoPrivilege.setLevel(level);
		
		Map<Object,Object> resultMap = new HashMap<Object,Object>();
		try {
			privilegeService.modifyPrivilege(dtoPrivilege);
			resultMap.put("result", "success");
			resultMap.put("identity", dtoPrivilege);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("result", "failure"); 
		}
		return resultMap;
	}

	@RequestMapping("/deletePrivilege.do")
	@ResponseBody
	public Map<Object,Object> deletePrivilege(HttpServletRequest request) {
		// Privilege privilege;
		String id = request.getParameter("privilegeId");
		Map<Object,Object> resultMap = new HashMap<Object,Object>();

		if (id == null) {
			resultMap.put("result", "failure"); 
		} else {
			try {
				String rstr = privilegeService.deletePrivilege(Integer.parseInt(id));
				if (rstr.equals("success")) {
					resultMap.put("privilegeId", id);
					resultMap.put("result", "success"); 
				} else {
					resultMap.put("result", "failure"); 
				}
			} catch (Exception e) {
				e.printStackTrace();
				resultMap.put("result", "failure"); 
			}
		}
		return resultMap;
	}

	@Override
	public List<PrivilegeDTO> listResults(int start, int limit,
			String sortName, String sortOrder, HttpServletRequest request,
			Boolean search) {
		return this.privilegeService.listResults(start, limit, sortName,
				sortOrder, request, search);
	}

	@Override
	public Integer getTotalRecords(HttpServletRequest request, Boolean search) {
		return privilegeService.getTotalRecords(request, search);
	}

	public PrivilegeService getPrivilegeService() {
		return privilegeService;
	}

	public void setPrivilegeService(PrivilegeService privilegeService) {
		this.privilegeService = privilegeService;
	}

	public PrivilegeMapper getPrivilegeMapper() {
		return privilegeMapper;
	}

	public void setPrivilegeMapper(PrivilegeMapper privilegeMapper) {
		this.privilegeMapper = privilegeMapper;
	}

}
