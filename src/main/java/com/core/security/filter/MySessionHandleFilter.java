package com.core.security.filter;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.core.system.model.Privilege;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.TextEscapeUtils;

import com.core.base.dto.MenuDTO;
import com.core.base.util.JsonUtil;
import com.core.base.util.SystemConstants;
import com.core.base.util.Utils;
import com.core.security.model.SpringSecurityUser;

public class MySessionHandleFilter implements Filter {

	private Format ft= new SimpleDateFormat("yyyy-MM-dd");
	private MyInvocationSecurityMetadataSource securityMetadataSource;
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,FilterChain chain) throws IOException, ServletException {

			/*Session不一致就刷新Menu  */
			HttpServletRequest request = (HttpServletRequest)req;
			HttpSession session = request.getSession();
			Collection<Privilege> userPrivileges = new HashSet<Privilege>();
			if((SecurityContextHolder.getContext().getAuthentication()!=null)
					&&(!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals(SystemConstants.ANONYMOUS_USER))){
				SpringSecurityUser userDetails = (SpringSecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				userPrivileges=userDetails.getPrivileges();
				if(session.getAttribute(SystemConstants.FIRST_VISIT)==null
						||session.getAttribute(SystemConstants.FIRST_VISIT).equals(true)){
					/*组织该用户的菜单*/
					int menuCount = SystemConstants.MENU_COUNT;
					Map<String , List<String>> leafDoms = new HashMap<String, List<String>>();
					@SuppressWarnings("unchecked")
					List<MenuDTO>[] hierarchyMenu = new ArrayList[menuCount];
					for(int i = 0 ; i < menuCount ; i++){
						hierarchyMenu[i] = new ArrayList<MenuDTO>();
					}
					
					Map<Integer , String> reflection = new HashMap<Integer, String>();
					for (Iterator<Privilege> iterator = userPrivileges.iterator(); iterator.hasNext();) {//斟酌之后，还是决定使用循环，做索引
						Privilege privilege = (Privilege) iterator.next();
						reflection.put(privilege.getPrivilegeId(), privilege.getUrl());
					}

					for (Iterator<Privilege> iterator = userPrivileges.iterator(); iterator.hasNext();) {
						Privilege privilege = (Privilege) iterator.next();
						if(privilege.getLevel()==null) continue;//每个用户必有主页面浏览权限，所以考虑判空，跳过
						//1为level，一定是一级目录
						MenuDTO md = new MenuDTO();
						md.setId(privilege.getPrivilegeId());
						md.setName(privilege.getName());
						md.setUrl(privilege.getUrl());
						if(privilege.getLevel()==1)	{
							md.setParentId(null);
							hierarchyMenu[privilege.getLevel()-1].add(md);
						}else if(privilege.getLevel()==2){
							md.setParentId(privilege.getParentId());
							hierarchyMenu[privilege.getLevel()-1].add(md);
						}else if(privilege.getLevel()==3&&privilege.getType().equals(SystemConstants.PAGE_TYPE)){
							md.setParentId(privilege.getParentId());		
							hierarchyMenu[privilege.getLevel()-1].add(md);
						}else if(privilege.getType().equals(SystemConstants.BUTTON_TYPE)){
							String temp = privilege.getTarget();
							if(!Utils.isEmptyOrNull(temp)) {
								String parentUrl = reflection.get(privilege.getParentId());
								if(leafDoms.get(parentUrl)==null) {
									List<String> listDoms = new ArrayList<String>();
									listDoms.add(temp);
									leafDoms.put(parentUrl, listDoms);
								}
								else leafDoms.get(parentUrl).add(temp);
							}
						}
					}
					
					for(int i = 0 ; i < menuCount ; i++){
						if( i > 0 ) createHierarchicalRelationship(hierarchyMenu[i],hierarchyMenu[i-1]);
					}
					
					String jsonResult = JsonUtil.toJson(leafDoms);
					session.setAttribute(SystemConstants.BUTTON_AUTH, jsonResult);
					session.setAttribute(SystemConstants.FIRST_MENU, hierarchyMenu[0]);
					session.setAttribute(SystemConstants.USER_NAME, userDetails.getUserRealName());
					session.setAttribute(SystemConstants.CURR_DATE, ft.format(new Date()));
					session.setAttribute(SystemConstants.LAST_USER,TextEscapeUtils.escapeEntities(userDetails.getUsername()));
					session.setAttribute(SystemConstants.FIRST_VISIT, false);
				}
				else {
					Map<String , List<String>> leafDoms = new HashMap<String, List<String>>();
					
					Map<Integer , String> reflection = new HashMap<Integer, String>();
					for (Iterator<Privilege> iterator = userPrivileges.iterator(); iterator.hasNext();) {//斟酌之后，还是决定使用循环，做索引
						Privilege privilege = (Privilege) iterator.next();
						reflection.put(privilege.getPrivilegeId(), privilege.getUrl());
					}
					
					for (Iterator<Privilege> iterator = userPrivileges.iterator(); iterator.hasNext();) {
						Privilege privilege = (Privilege) iterator.next();
						if(privilege.getLevel()==null) continue;//每个用户必有主页面浏览权限，所以考虑判空，跳过
						if(privilege.getType().equals(SystemConstants.BUTTON_TYPE)){
							String temp = privilege.getTarget();
							if(!Utils.isEmptyOrNull(temp)) {
								String parentUrl = reflection.get(privilege.getParentId());
								if(leafDoms.get(parentUrl)==null) {
									List<String> listDoms = new ArrayList<String>();
									listDoms.add(temp);
									leafDoms.put(parentUrl, listDoms);
								}
								else leafDoms.get(parentUrl).add(temp);
							}
						}
					}				
					String jsonResult = JsonUtil.toJson(leafDoms);
					session.setAttribute(SystemConstants.BUTTON_AUTH, jsonResult);
				}
			}
			chain.doFilter(req,res);
	}

	public void createHierarchicalRelationship(List<MenuDTO> childMenu , List<MenuDTO> parentMenu){
		for (Iterator<MenuDTO> iterator = childMenu.iterator(); iterator.hasNext();) {
			MenuDTO mdChild = (MenuDTO) iterator.next();
			for (Iterator<MenuDTO> iterator2 = parentMenu.iterator(); iterator2.hasNext();) {
				MenuDTO mdParent = (MenuDTO) iterator2.next();
				if(mdChild.getParentId().equals(mdParent.getId())){
					mdParent.getChildren().add(mdChild);
					if(mdParent.isHasChild()==false) mdParent.setHasChild(true);
					break;
				}
			}
		}	
	}
	
	@Override
	public void destroy() {
		
	}
	
	public MyInvocationSecurityMetadataSource getSecurityMetadataSource() {
		return securityMetadataSource;
	}

	public void setSecurityMetadataSource(MyInvocationSecurityMetadataSource securityMetadataSource) {
		this.securityMetadataSource = securityMetadataSource;
	}  
}  