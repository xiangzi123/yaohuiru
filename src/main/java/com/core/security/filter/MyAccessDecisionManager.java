package com.core.security.filter;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.core.base.util.SystemConstants;
import com.core.security.model.SpringSecurityUser;

public class MyAccessDecisionManager implements AccessDecisionManager {
	private final Log log = LogFactory.getLog(getClass());
	private boolean debug = log.isDebugEnabled();
    //In this method, need to compare authentication with configAttributes.
    // 1, A object is a URL, a filter was find permission configuration by this URL, and pass to here.
    // 2, Check authentication has attribute in permission configuration (configAttributes)
    // 3, If not match corresponding authentication, throw a AccessDeniedException.
    public void decide(Authentication authentication, Object object,
            Collection<ConfigAttribute> configAttributes)
            throws AccessDeniedException, InsufficientAuthenticationException {
        if(configAttributes == null){
            return ;
        }
        if(debug){
        	log.debug(object.toString());  //object is a URL.
        }
        Iterator<ConfigAttribute> ite=configAttributes.iterator();
        while(ite.hasNext()){
        	//SecurityContextHolder.getContext().getAuthentication().getAuthorities().remove(o);
            ConfigAttribute ca=ite.next();
            String needRole=((SecurityConfig)ca).getAttribute();
            //authentication 当前验证过的角色，注意，动态修改后，要重新决定是否有权限，authentication（这是一个unmodifyablecollection）有，同时用户configattribute中有才可
            if(SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals(SystemConstants.ANONYMOUS_USER)) throw new AccessDeniedException("AnonymousUser No Right");
            SpringSecurityUser userDetails = (SpringSecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            for(GrantedAuthority ga:userDetails.getAuthorities()){//authentication.getAuthorities()
                if(needRole.equals(ga.getAuthority())){  //ga is user's role.
                	return;
                }
            }
        }
        throw new AccessDeniedException("No Right");
    }

    public boolean supports(ConfigAttribute attribute) {
        // TODO Auto-generated method stub
        return true;
    }

    public boolean supports(Class<?> clazz) {
        return true;
    }


}
