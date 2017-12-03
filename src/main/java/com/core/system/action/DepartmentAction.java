package com.core.system.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.core.base.action.JqGridBaseAction;
import com.core.system.dao.DepartmentMapper;
import com.core.system.dto.DepartmentDTO;
import com.core.system.model.Department;
import com.core.system.model.DepartmentTeacherTree;
import com.core.system.model.DepartmentUserTree;
import com.core.system.service.DepartmentService;

@Controller
@RequestMapping("/system/depart")
public class DepartmentAction extends JqGridBaseAction<DepartmentDTO> {
	@Resource(name = "departmentMapper")
	private DepartmentMapper departmentMapper;
	@Resource(name = "departmentService")
	private DepartmentService departmentService;

	// 在zTree上显示部门
	@RequestMapping("/departList.do")
	@ResponseBody
	public List<Department> getDepartmentTreeList() throws Exception {
		return departmentMapper.getDepartmentList();
	}

	// 增加部门
	@RequestMapping("/addDepartment.do")
	@ResponseBody
	public Map<Object,Object> addDepartment(HttpServletRequest request) {
		boolean isLeaf = true;
		int parentId = Integer.parseInt(request.getParameter("parentId"));
		String level = request.getParameter("level");
		String departmentName = request.getParameter("name");
		level = Integer.parseInt(level) + 2 + "";//当前节点本身和ztree节点差1，其子节点又加1层，所以加二获得当前自己点级别
		Department dtoDepart = new Department();
		dtoDepart.setLevel(level);
		dtoDepart.setIsLeaf(isLeaf);
		dtoDepart.setName(departmentName);
		dtoDepart.setParentId(parentId);
		Map<Object,Object> resultMap = new HashMap<Object,Object>();
		try {
			departmentService.addDepartment(dtoDepart);
			resultMap.put("result", "success");
			resultMap.put("identity", dtoDepart);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("result", "failure");
		}
		return resultMap;
	}

	// 修改部门
	@RequestMapping("/modifyDepartment.do")
	@ResponseBody
	public Map<Object,Object> modifyDepartment(HttpServletRequest request) {
		String departmentId = request.getParameter("departmentId");
		String name = request.getParameter("departmentName");
		Department dtoDepart = new Department();
		dtoDepart.setDepartmentId(Integer.parseInt(departmentId));
		dtoDepart.setName(name);
		Map<Object,Object> resultMap = new HashMap<Object,Object>();
		try {
			departmentService.modifyDepartment(dtoDepart);
			resultMap.put("result", "success");
			resultMap.put("identity", dtoDepart);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("result", "failure"); 
		}
		return resultMap;
	}

	// 删除部门
	@RequestMapping("/deleteDepartment.do")
	@ResponseBody
	public Map<Object,Object> deleteDepartment(HttpServletRequest request) {
		String id = request.getParameter("departmentId");
		Map<Object,Object> resultMap = new HashMap<Object,Object>();
		if (id == null) {
			resultMap.put("result", "failure"); 
		} else {
			try {
				String rStr = departmentService.deleteDepartment(Integer.parseInt(id));
				if (rStr.equals("success")) {
					resultMap.put("departmentId", id);
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

	public DepartmentService getDepartmentService() {
		return departmentService;
	}

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	public DepartmentMapper getDepartmentMapper() {
		return departmentMapper;
	}

	public void setDepartmentMapper(DepartmentMapper departmentMapper) {
		this.departmentMapper = departmentMapper;
	}

	@Override
	public List<DepartmentDTO> listResults(int start, int limit,
			String sortName, String sortOrder, HttpServletRequest request,
			Boolean search) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getTotalRecords(HttpServletRequest request, Boolean search) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// 获得部门人员树结构
	@RequestMapping("/getTree.do")
	@ResponseBody
	public List<DepartmentUserTree> getTree() throws Exception {
		return departmentService.selectTree();
	}
	
	// 获得部门教师树结构
	@RequestMapping("/getTeacherTree.do")
	@ResponseBody
	public List<DepartmentTeacherTree> getTeacherTree() throws Exception {
		return departmentService.selectTeacherTree();
	}
	
	@RequestMapping("/checkHasUser.do")
	@ResponseBody
	public boolean checkHasUser(@RequestParam(value="departmentId") Integer departmentId){
		boolean validate = true;
		if(!this.departmentService.checkUserBydeId(departmentId)) validate=false;
		return validate;
	}
	
}
