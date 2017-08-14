package cn.com.open.apptoolservice.app.common;

import java.io.Serializable;

public class BasePage implements Serializable,Cloneable{
	
	private static final long serialVersionUID = 1L;

	private Integer page = 1; //当前页
	private Integer rows =10; //每页行数
	private int pageOffset = 0;// 当前页起始记录

	public String getMysqlQueryCondition() {
		int p=this.page==0?1:this.page;
		this.pageOffset = (p - 1) * this.rows;
		return " limit " + pageOffset + "," + rows;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public int getPageOffset() {
		return pageOffset;
	}

	public void setPageOffset(int pageOffset) {
		this.pageOffset = pageOffset;
	}
}
