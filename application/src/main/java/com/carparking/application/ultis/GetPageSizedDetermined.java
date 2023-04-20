package com.carparking.application.ultis;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.carparking.application.response.Meta;
import com.carparking.application.response.ResponseObject;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;

import com.carparking.application.exception.ResourceNotFoundException;

public class GetPageSizedDetermined {
	public static ResponseObject getPageSizedDetermined(int page, int size, List<?> listData) {
		ResponseObject response = new ResponseObject();
		int start = (page - 1) * size;
		int end = Math.min(start + size, listData.size());
		int sizePage = (int) Math.ceil((double) listData.size() / size);
		if (start > end) {
			throw new ResourceNotFoundException("" + size + " is out of the list range <0," + (sizePage - 1) + ">");
		}
		Meta meta = new Meta();
		meta.setTotal(listData.size());
		meta.setPage(page);
		meta.setSize(size);
		meta.setTotalPage(sizePage);
		List<?> listSub = listData.subList(start, end);
		response.setData(listSub);
		response.setMeta(meta);
		return response;
	};

	public static <T> List<T> getPageSizedObjectT(List<T> list, int page, int size, String field, String sort) {

		if (sort.toLowerCase().equals("desc")) {
			PropertyComparator.sort(list, new MutableSortDefinition(field, false, false));
		} else if (sort.toLowerCase().equals("asc")) {
			PropertyComparator.sort(list, new MutableSortDefinition(field, false, true));
		}

		if (page < 1) {
			throw new IllegalArgumentException("trang không được ít hơn 1, trang" + page);
		}
		if (size < 1) {
			throw new IllegalArgumentException("kích thước không được nhỏ hơn 1, kích thước:" + size);
		}
		if (isEmpty(list)) {
			return Collections.emptyList();
		}

		int totalSize = list.size();

		int fromIndex = (page - 1) * size > (totalSize - 1) ? 0 : (page - 1) * size;

		int endIndex = (fromIndex + size) > (totalSize) ? (totalSize) : (fromIndex + size);

		return list.subList(fromIndex, endIndex);

	};

	public static <T> boolean isEmpty(List<T> list) {
		return list == null || list.isEmpty();
	}

	public static <K, V> boolean isEmpty(Map<K, V> map) {
		return map == null || map.isEmpty();
	}

}
