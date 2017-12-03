package com.core.system.dto;

public class ExportLogForm {

	@Override
	public String toString() {
		return "ExportLogForm [module=" + module + ", starttime=" + starttime
				+ ", endtime=" + endtime + "]";
	}
	private String module;
	private String starttime;
	private String endtime;
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	
}
