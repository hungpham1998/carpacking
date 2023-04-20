package com.carparking.core_response_advice.model;

import lombok.Data;

@Data
public class Meta {
	int page;
	int size;
	long total;
	int totalPage;
}
