package com.core.system.action;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.core.base.action.JqGridBaseAction;
import com.core.system.dto.UserAddForm;
import com.core.system.dto.UserCheckForm;
import com.core.system.dto.UserDTO;
import com.core.system.dto.UserModifyForm;
import com.core.system.model.User;
import com.core.system.service.UserService;

/**
 *  Copyright 2013 BUPU TR3-917
 *  All right reserved.
 *
 * @author 贺群
 * @Creat Time : 2013-11-4 下午9:33:06
 * @UserAction 处理用户ACTION，Mapping Url
 */
@Controller
@RequestMapping("/system/user")
public class UserAction extends JqGridBaseAction<UserDTO>{
	
//	private final Log log = LogFactory.getLog(getClass());
//	private boolean debug = log.isDebugEnabled();
	
	//将spring 配置文件中的bean 通过setter注入进来
	@javax.annotation.Resource(name="userService")
	private UserService userService=null;

	
	@Override
	public List<UserDTO> listResults(int start, int limit, String sortName,
			String sortOrder, HttpServletRequest request, Boolean search) {
		return this.userService.listResults(start, limit, sortName, sortOrder, request,search);
	}

	@Override
	public Integer getTotalRecords(HttpServletRequest request, Boolean search) {
		return userService.getTotalRecords(request,search);
	}	
	
	/**
	 * @author 贺群
	 * @param NONE
	 * @return Json object to html select dom
	 */
	@RequestMapping("/info.do")
	@ResponseBody
	public Map<String, List<?>> getUserSelectInfo(){
		Map<String, List<?>> map = new HashMap<String, List<?>>();
		map.put("role", this.userService.getAllRoles());
		map.put("organ", this.userService.getAllDepartments());
		return map;
	}

	/**
	 * @author 贺群
	 * @param loginName 要注册的用户名称
	 * @return 后台验证是否重名，true为未重名，false为重名
	 */
	
	//app登录验证
	@RequestMapping("/validateLogin.do")
	@ResponseBody
	public  Map<String, Object> validateLogin(@RequestParam(value="name") String loginName,
				@RequestParam(value="password") String password){
		
			String loginResult= this.userService.validateLogin(loginName,password);
			Map<String,Object>  map = new HashMap<String,Object>();
			map.put("loginResult", loginResult);
			return map;
			}
	
	@RequestMapping("/validateLoginName.do")
	@ResponseBody
	public boolean validateLoginName(@RequestParam(value="loginName") String loginName){
		boolean validate = true;
		User u = this.userService.validateExist(loginName);
		if(u!=null) validate=false;
		return validate;
	}
	
	
	@RequestMapping("/validatePermitNum.do")
	@ResponseBody
	public boolean validateLPermitNum(@RequestParam(value="permitNum") String permitNum,
			@RequestParam(value="oldPermitNum") String oldPermitNum){
		boolean validate = true;
		if(permitNum.equals(oldPermitNum))
			return validate;
		else{
			UserDTO ud = this.userService.validatePNExist(permitNum);
			if(ud!=null) 
				validate=false;
			return validate;
		}
	}
	@RequestMapping("/addUserOut.do")
	@ResponseBody
	public boolean addUserOut(@ModelAttribute("userAddForm") UserAddForm userAddForm){
		boolean validate = true;
		if(!this.userService.insertUserAndRoles(userAddForm)) validate=false;
		return validate;
	}
	
	@RequestMapping("/modifyUserIn.do")
	@ResponseBody
	public UserModifyForm modifyUserIn(@RequestParam(value="userId") String userId){
		UserModifyForm userModifyForm;
		userModifyForm = this.userService.getUserRolesAndInfo(userId) ;
		return userModifyForm;
	}
	
	@RequestMapping("/modifyUserOut.do")
	@ResponseBody
	public boolean modifyUserOut(@ModelAttribute("userModifyForm") UserModifyForm userModifyForm){
		boolean validate = true;
		if(!this.userService.updateUserAndRoles(userModifyForm)) validate=false;
		return validate;
	}
	
	@RequestMapping("/checkUser.do")
	@ResponseBody
	public UserCheckForm checkUserIn(@RequestParam(value="userId") String userId){
		UserCheckForm userCheckForm;
		userCheckForm = this.userService.getInfoForCheck(userId) ;
		return userCheckForm;
	}
	
	@RequestMapping("/deleteUser.do")
	@ResponseBody
	public boolean deleteUser(@RequestParam(value="userId") String userId){
		boolean validate = true;
		if(!this.userService.deleteUser(userId)) validate=false;
		return validate;
	}
	
	@RequestMapping("/recoverUserPass.do")
	@ResponseBody
	public boolean recoverUserPass(@RequestParam(value="userId") String userId){
		boolean validate = true;
		if(!this.userService.recoverUserPass(userId)) validate=false;
		return validate;
	}
	
	@RequestMapping("/lockUser.do")
	@ResponseBody
	public boolean lockUser(@RequestParam(value="userId") String userId){
		boolean validate = true;
		if(!this.userService.lockUser(userId)) validate=false;
		return validate;
	}
	
	@RequestMapping("/multiLockUser.do")
	@ResponseBody
	public boolean multiLockUser(@RequestParam(value="userIds") String userIds){
		boolean validate = true;
		String[] ids = userIds.split(",");
		for(String id : ids){ 
			if(!this.userService.lockUser(id)) 
				validate=false;
		} 
		return validate;
	}
	
	@RequestMapping("/unLockUser.do")
	@ResponseBody
	public boolean unLockUser(@RequestParam(value="userId") String userId){
		boolean validate = true;
		if(!this.userService.unLockUser(userId)) validate=false;
		return validate;
	}
	
	@RequestMapping("/expireUser.do")
	@ResponseBody
	public boolean expireUser(@RequestParam(value="userId") String userId){
		boolean validate = true;
		if(!this.userService.expireUser(userId)) validate=false;
		return validate;
	}
	
	@RequestMapping("/recoverUser.do")
	@ResponseBody
	public boolean recoverUser(@RequestParam(value="userId") String userId){
		boolean validate = true;
		if(!this.userService.recoverUser(userId)) validate=false;
		return validate;
	}
	
	@RequestMapping("/changePassword.do")
	@ResponseBody
	public boolean changePassword(@RequestParam(value="userLoginName") String userLoginName, 
														@RequestParam(value="password") String newPassWord){
		boolean validate = true;
		if(!this.userService.changePassword(userLoginName , newPassWord)) validate=false;
		return validate;
	}
	
	@RequestMapping("/validateExcel.do")
	@ResponseBody
	public Map<String, Object> validateExcel(@RequestParam("userExcel") MultipartFile file , HttpServletRequest request) throws Throwable{
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		String result = "";
		String resultInfo = "";
		
        String path = request.getSession().getServletContext().getRealPath("upload");  
        String fileName = file.getOriginalFilename();   

        File targetFile = new File(path, fileName);  
        
        if(!targetFile.exists()){  
            targetFile.mkdirs();  
        }  
        //保存   
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        if (fileName != null && !"".equals(fileName)) {
        	if (fileName.equals("user_import.xlsx")) {
        		result = this.userService.validateExcel(file.getInputStream(),request.getSession().getId());
        		if (!result.equals("success")) {
        			resultInfo = result;
        			result = "fail";
        		}
        	} else {
        		resultInfo = "请下载正确的模板 :  user_import.xlsx";
        		result = "fail";
        	}
        } 
	    dataMap.put("result", result);
		dataMap.put("resultInfo", resultInfo);
		return dataMap;
	}
	
	@RequestMapping("/importExcel.do")
	@ResponseBody
	public Map<String, Object> importExcel(HttpServletRequest request){
		Map<String, Object> dataMap = new HashMap<String, Object>();
		
		String[] result = this.userService.importExcel(request.getSession().getId());
		
		dataMap.put("result", result[0]);
		dataMap.put("resultInfo", result[1]);
		return dataMap;
	}
	
	//查询有发布权限的人员
	@RequestMapping("/getUserByPrivilege.do")
	@ResponseBody
	public Map<String, List<?>> getUserByPriviliege(@RequestParam(value="privilegeId") String privilegeId){
		Map<String, List<?>> m = new HashMap<String, List<?>>();
		m.put("user",userService.getUserByCriteria(privilegeId));
		return m;
	}
	
	@RequestMapping("/downloadTemplate.do")
	public ResponseEntity<byte[]> downloadTemplateExcel() throws Throwable{
		Resource res =  new ClassPathResource("serverSource/importTemplate/user_import.xls");
		HttpHeaders headers = new HttpHeaders();  
	    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);  
	    headers.setContentDispositionFormData("attachment", "user_import.xls"); 
		byte[] fileData = FileCopyUtils.copyToByteArray(res.getInputStream());
		ResponseEntity<byte[]> responseEntity = new ResponseEntity<byte[]>(fileData,headers,HttpStatus.OK);
		return responseEntity;
	}
	
	@RequestMapping("/validatePW.do")
	@ResponseBody
	public boolean validatePW(@RequestParam(value="originalpw")String originalPw){
		boolean validate = true;
		if(!this.userService.checkOriginalPW(originalPw)) validate=false;
		return validate;
	}
	
	@RequestMapping("/getDeparmentInfo.do")
	@ResponseBody
	public Map<String, List<?>> getDeparmentInfo(){
		Map<String, List<?>> map = new HashMap<String, List<?>>();
		map.put("postDepartment", this.userService.getAllDepartments());
		return map;
	}
	
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
