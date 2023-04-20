package com.carparking.core_entity.entities;

import com.carparking.core_entity.SortDirectionE;
import com.carparking.core_entity.definition.AppDefinition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SortCriteria {
	
	private String field = AppDefinition.DefaultFieldSorting;
	
	private String order = SortDirectionE.DESC.toString();
}
