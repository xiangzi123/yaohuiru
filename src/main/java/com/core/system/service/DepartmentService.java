package com.core.system.service;
import java.util.List;

import com.core.base.service.BaseService;
import com.core.system.dto.DepartmentDTO;
import com.core.system.model.Department;
import com.core.system.model.DepartmentTeacherTree;
import com.core.system.model.DepartmentUserTree;

public interface DepartmentService extends BaseService <Department,DepartmentDTO> {
	public List<Department> getDepartmentTree(String types);
	
	public Department getDepartmentTreeById(int id);
	
	public String deleteDepartment(int id);
	
	public String modifyDepartment(Department record);
	
	public String addDepartment(Department record);
	
	public List<DepartmentUserTree> selectTree();
	
	public List<DepartmentTeacherTree> selectTeacherTree();

	public boolean checkUserBydeId(Integer departmentId);
	
	public List<Department> getAllTeachingAndResearch();
}
