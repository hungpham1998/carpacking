package com.carparking.application.response;

import java.util.List;

public class ResponseObject {
	Object data;
	Meta meta = new Meta();
	List<?> list;
	int page;
	int size;
	long total;
	int totalPage;

	public void setMeta(Meta meta) {
		this.meta = meta;
	}

	public Meta getMeta() {
		return meta;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
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

	public void setMetaRepo(int page, int size, int total, int totalPage) {
		Meta newMeta = new Meta();
		newMeta.setPage(page);
		newMeta.setSize(size);
		newMeta.setTotal(total);
		newMeta.setTotalPage(totalPage);
		setMeta(newMeta);
	}
}
