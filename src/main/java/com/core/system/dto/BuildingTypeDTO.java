package com.core.system.dto;

	public class BuildingTypeDTO { 
	
	    private Integer typeId;

	    private String typeName;

	    private String typeDes;

	    private String icon;

	    public Integer getTypeId() {
	        return typeId;
	    }

	    public void setTypeId(Integer typeId) {
	        this.typeId = typeId ;
	    }
	    
	    public String getTypeName() {
	        return typeName;
	    }

	    public void setTypeName(String typeName) {
	        this.typeName = typeName ;
	    }

	    public String getTypeDes() {
	        return typeDes;
	    }


	    public void setTypeDes(String typeDes) {
	        this.typeDes = typeDes;
	    }

	    public String getIcon() {
	        return icon;
	    }

	    public void setIcon(String icon) {
	        this.icon = icon ;
	    }
	}