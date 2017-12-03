package com.core.base.dto;

import java.util.ArrayList;
import java.util.List;

public class MenuDTO implements Comparable<MenuDTO>{

    private Integer Id;

    private Integer parentId;

    private String name;
    
    private String url;
    
    private boolean hasChild = false;
    
    private List<MenuDTO> children = new ArrayList<MenuDTO>();

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

	public List<MenuDTO> getChildren() {
		return children;
	}

	public void setChildren(List<MenuDTO> children) {
		this.children = children;
	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		this.Id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isHasChild() {
		return hasChild;
	}

	public void setHasChild(boolean hasChild) {
		this.hasChild = hasChild;
	}

	@Override
	public int compareTo(MenuDTO o) {
		return this.getId().compareTo(o.getId());
	}
}