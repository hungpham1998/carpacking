package com.carparking.application.ultis;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class SortPageSize {
	public static Pageable getPageSize(int page, int size, String field, String sort) {
		Pageable pageable;
		if (sort.toLowerCase().equals("asc")) {
			pageable = PageRequest.of(page - 1, size, Sort.by(field).ascending());
		} else {
			pageable = PageRequest.of(page - 1, size, Sort.by(field).descending());
		}
		return pageable;
	};
}
