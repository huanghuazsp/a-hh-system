package com.hh.system.util.dto;

import java.util.ArrayList;
import java.util.List;

public class PagingData<T> {
	private List<T> items = new ArrayList<T>();
	private int total;

	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

}
