package com.hh.system.util.dto;

public class PageRange {
	// 第几页
//	private int page;
	// 第几条数据开始
	private int start;
	// 每页显示多少条
	private int limit;

	public int getEnd() {
		return limit + start;
	}

	public PageRange(int start2, int limit2) {
		this.setLimit(limit2);
//		this.setPage(page2);
		this.setStart(start2);
	}

//	public int getPage() {
//		return page;
//	}
//
//	public void setPage(int page) {
//		this.page = page;
//	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

}
