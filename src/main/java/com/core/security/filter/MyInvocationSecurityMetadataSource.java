package com.core.security.filter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import com.core.base.util.SystemConstants;
import com.core.security.tool.AntUrlPathMatcher;
import com.core.security.tool.UrlMatcher;
import com.core.system.dao.PrivilegeMapper;
import com.core.system.dao.RoleMapper;
import com.core.system.model.Privilege;
import com.core.system.model.Role;
import com.core.system.model.RoleExample;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 该过滤器的主要作用就是通过spring著名的IoC生成securityMetadataSource。  
 * securityMetadataSource相当于本包中自定义的MyInvocationSecurityMetadataSourceService。  
 * 该MyInvocationSecurityMetadataSourceService的作用提从数据库提取权限和资源，装配到HashMap中，  
 * 供Spring Security使用，用于权限校验。
 * @author hequn litong
 *
 */
@Component("myInvocationSecurityMetadataSource")
public class MyInvocationSecurityMetadataSource  
        implements FilterInvocationSecurityMetadataSource {  
    private UrlMatcher urlMatcher = new AntUrlPathMatcher();
    private static Map<String, Collection<ConfigAttribute>> resourceMap = null;  
    private static Map<String, Collection<Privilege>> privilegeMap = null;
	private final Log log = LogFactory.getLog(getClass());
	private boolean debug = log.isDebugEnabled();
	
	//权限Mapper层
	@Resource(name="privilegeMapper")
	private PrivilegeMapper privilegeMapper;
	//角色Mapper层
	@Resource(name="roleMapper")
	private RoleMapper roleMapper;
	public  MyInvocationSecurityMetadataSource(){

	}
	//此处必须用构造器构造，因为此securityMetadataSource加载早于spring注入
    public MyInvocationSecurityMetadataSource(PrivilegeMapper privilegeMapper,RoleMapper roleMapper) {  
    	this.privilegeMapper = privilegeMapper;
    	this.roleMapper = roleMapper;
        loadResourceDefine();  
     }  
  
    private void loadResourceDefine() {  
         resourceMap = new HashMap<String, Collection<ConfigAttribute>>();    
         privilegeMap = new HashMap<String, Collection<Privilege>>();  
         ConfigAttribute ca = new SecurityConfig("ROLE_ANONYMOUS");
		List<Privilege> privileges=this.privilegeMapper.getPrivilegeList();
		//privileges = Connector.getPrivilegeList();

         for (Iterator<Privilege> iterator = privileges.iterator(); iterator.hasNext();) {
			Privilege privilege = (Privilege) iterator.next();
			RoleExample roleExample = new RoleExample();
			roleExample.or().andPrilegeEqualTo(privilege.getPrivilegeId().toString());
			List<Role> roles = roleMapper.selectByExample(roleExample);
			 //List<Role> roles = Connector.getRoleList(privilege.getPrivilegeId().toString());
			Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();  
			if(roles.size()==0) {
				atts.add(new SecurityConfig("NO_ROLES_FOR_URL"));
				if(privilegeMap.get("NO_ROLES_FOR_URL")==null) {
					Collection<Privilege> cpTemp = new ArrayList<Privilege>();
					cpTemp.add(privilege);
					privilegeMap.put("NO_ROLES_FOR_URL",cpTemp);
				}else
				privilegeMap.get("NO_ROLES_FOR_URL").add(privilege);
			}
			for (Iterator<Role> iterator2 = roles.iterator(); iterator2.hasNext();) {
				Role role = (Role) iterator2.next();
				ca = new SecurityConfig(role.getName());  
				atts.add(ca);
				if(privilegeMap.get(role.getName())==null) {
					Collection<Privilege> cs = new ArrayList<Privilege>();
					cs.add(privilege);
			        Privilege pMain = new Privilege();
			        pMain.setUrl(SystemConstants.MAIN_PAGE);
			        cs.add(pMain);
					privilegeMap.put(role.getName(), cs);
				}else {
					privilegeMap.get(role.getName()).add(privilege);
				}
			}
			resourceMap.put(privilege.getUrl(), atts);
		}
         /*只要拥有至少一个角色的用户，就可以访问主地址*/
         Collection<ConfigAttribute> attsRoles = new ArrayList<ConfigAttribute>();
         Iterator<String> it = privilegeMap.keySet().iterator();
         while (it.hasNext()){   
        	 String key=(String)it.next();
        	 ConfigAttribute caRole= new SecurityConfig(key);
        	 if(!key.equals("NO_ROLES_FOR_URL")) attsRoles.add(caRole);
         } 
         //没有角色的用户也可以访问主地址了，但是绝对无其他权限
         attsRoles.add(new SecurityConfig("NO_ROLE_OR_PRIVILEGE_USER"));
         resourceMap.put(SystemConstants.MAIN_PAGE, attsRoles);
         Privilege p = new Privilege();
         p.setUrl(SystemConstants.MAIN_PAGE);
         Collection<Privilege>tempP = new ArrayList<Privilege>();
         tempP.add(p);
         privilegeMap.put("NO_ROLE_OR_PRIVILEGE_USER", tempP);
         /*-------------------------------------------*/
     }  
  
    // According to a URL, Find out permission configuration of this URL.  
    public Collection<ConfigAttribute> getAttributes(Object object)  
            throws IllegalArgumentException {
    	Collection<ConfigAttribute> result = new ArrayList<ConfigAttribute>();
        // guess object is a URL.  
         String url = ((FilterInvocation)object).getRequestUrl();  
         Iterator<String> ite = resourceMap.keySet().iterator();  
        while (ite.hasNext()) {  
             String resURL = ite.next();  
            if (urlMatcher.pathMatchesUrl(url, resURL)) {  
            	result = resourceMap.get(resURL);  
            	if(result.size()==0&&!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals(SystemConstants.ANONYMOUS_USER))
            		result.add(new SecurityConfig("NO_ROLES_FOR_URL"));
            	break;
             }  
         }  
        if(debug){
        	if(result!=null){
	        	for (ConfigAttribute configAttribute : result) {
	        		log.debug(configAttribute.getAttribute());
				}
        	}
        }
      //对没有角色对应的url如何处理，此句话会使json url，细节到每一个，如果没有录入数据库并加载到内容，将不能访问
      //  if(result.size()==0) result.add(new SecurityConfig("NO_ROLE_USER"));
        return result;  
     }  
  
    public boolean supports(Class<?> clazz) {  
        return true;  
     }  
      
    public Collection<ConfigAttribute> getAllConfigAttributes() {  
        return null;  
     }

	public PrivilegeMapper getPrivilegeMapper() {
		return privilegeMapper;
	}

	public void setPrivilegeMapper(PrivilegeMapper privilegeMapper) {
		this.privilegeMapper = privilegeMapper;
	}

	public RoleMapper getRoleMapper() {
		return roleMapper;
	}

	public void setRoleMapper(RoleMapper roleMapper) {
		this.roleMapper = roleMapper;
	}

	public static Map<String, Collection<ConfigAttribute>> getResourceMap() {
		return resourceMap;
	}

	public static void setResourceMap(
			Map<String, Collection<ConfigAttribute>> resourceMap) {
		MyInvocationSecurityMetadataSource.resourceMap = resourceMap;
	}

	public static Map<String, Collection<Privilege>> getPrivilegeMap() {
		return privilegeMap;
	}

	public static void setPrivilegeMap(Map<String, Collection<Privilege>> privilegeMap) {
		MyInvocationSecurityMetadataSource.privilegeMap = privilegeMap;
	}
}  