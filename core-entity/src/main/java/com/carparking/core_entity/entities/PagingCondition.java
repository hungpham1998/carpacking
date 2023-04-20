package com.carparking.core_entity.entities;

import com.carparking.core_entity.definition.AppDefinition;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PagingCondition {
	public int pageNo = AppDefinition.DefaultPageNo;
	
	public int pageSize = AppDefinition.DefaultPageSize;
}
