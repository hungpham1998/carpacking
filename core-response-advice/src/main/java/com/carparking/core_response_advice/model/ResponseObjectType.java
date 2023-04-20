package com.carparking.core_response_advice.model;

import lombok.Data;

import java.util.List;

@Data
public class ResponseObjectType<T> {
	Object data;
	Meta meta = new Meta();
	List<T> list;
	int page;
	int size;
	long total;
	int totalPage;
}
