package com.core.system.dto;

public class BuildingTypeCheck {
	

	    private String typeName;

	 
	    private String typeDes;

	 
	    private String icon;

	  
	    public String getTypeName() {
	        return typeName;
	    }

	    /**
	     * This method was generated by MyBatis Generator.
	     * This method sets the value of the database column building_type.type_name
	     *
	     * @param typeName the value for building_type.type_name
	     *
	     * @mbggenerated Mon Nov 17 11:15:10 CST 2014
	     */
	    public void setTypeName(String typeName) {
	        this.typeName = typeName == null ? null : typeName.trim();
	    }

	    /**
	     * This method was generated by MyBatis Generator.
	     * This method returns the value of the database column building_type.type_des
	     *
	     * @return the value of building_type.type_des
	     *
	     * @mbggenerated Mon Nov 17 11:15:10 CST 2014
	     */
	    public String getTypeDes() {
	        return typeDes;
	    }

	    /**
	     * This method was generated by MyBatis Generator.
	     * This method sets the value of the database column building_type.type_des
	     *
	     * @param typeDes the value for building_type.type_des
	     *
	     * @mbggenerated Mon Nov 17 11:15:10 CST 2014
	     */
	    public void setTypeDes(String typeDes) {
	        this.typeDes = typeDes == null ? null : typeDes.trim();
	    }

	    /**
	     * This method was generated by MyBatis Generator.
	     * This method returns the value of the database column building_type.icon
	     *
	     * @return the value of building_type.icon
	     *
	     * @mbggenerated Mon Nov 17 11:15:10 CST 2014
	     */
	    public String getIcon() {
	        return icon;
	    }

	    /**
	     * This method was generated by MyBatis Generator.
	     * This method sets the value of the database column building_type.icon
	     *
	     * @param icon the value for building_type.icon
	     *
	     * @mbggenerated Mon Nov 17 11:15:10 CST 2014
	     */
	    public void setIcon(String icon) {
	        this.icon = icon == null ? null : icon.trim();
	    }
	}
