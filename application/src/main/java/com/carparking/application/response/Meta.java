package com.carparking.application.response;

public class Meta {
	int page;
	int size;
	long total;
	int totalPage;

	public void setSize(int size) {
		this.size = size;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public int getPage() {
		return page;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
}
