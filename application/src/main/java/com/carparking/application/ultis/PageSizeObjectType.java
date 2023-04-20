package com.carparking.application.ultis;

import java.util.List;

import com.carparking.application.response.Meta;
import com.carparking.application.response.ResponseObjectType;

public class PageSizeObjectType {
	public static <T> ResponseObjectType<T> getPageSize(List<T> listData, int page, int size, long total, int totalPage) {
		ResponseObjectType<T> response = new ResponseObjectType<T>();
		response.setData(listData);
		Meta meta = new Meta();
		meta.setTotal(total);
		meta.setPage(page);
		meta.setSize(size);
		meta.setTotalPage(totalPage);
		response.setMeta(meta);
		return response;
	};
}
