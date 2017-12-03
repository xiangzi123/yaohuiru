package com.core.security.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import com.core.system.model.Department;
import com.core.system.model.Privilege;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

public class SpringSecurityUser implements UserDetails, CredentialsContainer {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4942411098141944678L;
	//~ Instance fields ================================================================================================
	private Department department;
	private String userRealName;
	private String password;
	private String permitNum;
    private final Set<GrantedAuthority> authorities;//不再将其设置为unmodifiable属性，可以动态修改，随用户动态权限改变
    private final Set<Privilege> privileges;//仅仅为辅助作用，概念上，动态实现在rMap和pMap中通过较复杂的逻辑实现了
    private final String username;
    private final boolean accountNonExpired;
    private final boolean accountNonLocked;
    private final boolean credentialsNonExpired;
    private final boolean enabled;

    public SpringSecurityUser(String username, String password, boolean enabled, boolean accountNonExpired,
            boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities,
            Collection<? extends Privilege> privileges ,Department department , String permitNum , String userRealName) {

        if (((username == null) || "".equals(username)) || (password == null)) {
            throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
        }

        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.accountNonExpired = accountNonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.authorities = sortAuthorities(authorities);//Collections.unmodifiableSet(sortAuthorities(authorities)); 是否可动态修改权限修改？
        this.privileges=sortPrivileges(privileges);
        this.department=department;
        this.permitNum = permitNum;
        this.userRealName = userRealName;
    }

    //~ Methods ========================================================================================================

    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void eraseCredentials() {
        password = null;
    }

    private static SortedSet<GrantedAuthority> sortAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");
        // Ensure array iteration order is predictable (as per UserDetails.getAuthorities() contract and SEC-717)
        SortedSet<GrantedAuthority> sortedAuthorities =
            new TreeSet<GrantedAuthority>(new AuthorityComparator());

        for (GrantedAuthority grantedAuthority : authorities) {
            Assert.notNull(grantedAuthority, "GrantedAuthority list cannot contain any null elements");
            sortedAuthorities.add(grantedAuthority);
        }

        return sortedAuthorities;
    }
    
    private static SortedSet<Privilege> sortPrivileges(Collection<? extends Privilege> privileges) {
        Assert.notNull(privileges, "Cannot pass a null Privilege collection");
        // Ensure array iteration order is predictable (as per UserDetails.getAuthorities() contract and SEC-717)
        SortedSet<Privilege> sortedPrivileges =
            new TreeSet<Privilege>(new PrivilegeComparator());

        for (Privilege privilege : privileges) {
            Assert.notNull(privilege, "Privilege list cannot contain any null elements");
            sortedPrivileges.add(privilege);
        }

        return sortedPrivileges;
    }

    private static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable {
        /**
		 * 
		 */
		private static final long serialVersionUID = -5745043697164288634L;

		public int compare(GrantedAuthority g1, GrantedAuthority g2) {
            // Neither should ever be null as each entry is checked before adding it to the set.
            // If the authority is null, it is a custom authority and should precede others.
            if (g2.getAuthority() == null) {
                return -1;
            }

            if (g1.getAuthority() == null) {
                return 1;
            }

            return g1.getAuthority().compareTo(g2.getAuthority());
        }
    }
    
    private static class PrivilegeComparator implements Comparator<Privilege>, Serializable {
        /**
		 * 
		 */
		private static final long serialVersionUID = -5745043697164288634L;

		public int compare(Privilege g1, Privilege g2) {
            // Neither should ever be null as each entry is checked before adding it to the set.
            // If the authority is null, it is a custom authority and should precede others.
            if (g2.getPrivilegeId() == null) {
                return -1;
            }

            if (g1.getPrivilegeId() == null) {
                return 1;
            }

            return g1.getPrivilegeId().compareTo(g2.getPrivilegeId());
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append(": ");
        sb.append("Username: ").append(this.username).append("; ");
        sb.append("UserRealname: ").append(this.userRealName).append("; ");
        sb.append("Password: [PROTECTED]; ");
        sb.append("Enabled: ").append(this.enabled).append("; ");
        sb.append("AccountNonExpired: ").append(this.accountNonExpired).append("; ");
        sb.append("credentialsNonExpired: ").append(this.credentialsNonExpired).append("; ");
        sb.append("AccountNonLocked: ").append(this.accountNonLocked).append("; ");

        if (!authorities.isEmpty()) {
            sb.append("Granted Authorities: ");

            boolean first = true;
            for (GrantedAuthority auth : authorities) {
                if (!first) {
                    sb.append(",");
                }
                first = false;

                sb.append(auth);
            }
        } else {
            sb.append("Not granted any authorities");
        }

        return sb.toString();
    }

	public Collection<Privilege> getPrivileges() {
		return privileges;
	}

	public Department getDepartment() {
		return department;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SpringSecurityUser other = (SpringSecurityUser) obj;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	public String getPermitNum() {
		return permitNum;
	}

	public void setPermitNum(String permitNum) {
		this.permitNum = permitNum;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserRealName() {
		return userRealName;
	}

	public void setUserRealName(String userRealName) {
		this.userRealName = userRealName;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
}
