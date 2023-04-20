package com.htsc.core_utils.utils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.htsc.core_utils.annotation.DontUpdate;

//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;

public class MapperUtil {
	private static ObjectMapper objectMapper;

	static {
		objectMapper = new ObjectMapper();
		//objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		//objectMapper.registerModule(new JavaTimeModule())
        //.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);;
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.registerModule(new JavaTimeModule());

	}

	public static Map<String, ?> toMap(String json) {
		try {
			return objectMapper.readValue(json, new TypeReference<Map<String, ?>>() {
			});
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

  public static <T> List<T> toListFromObject(Object data, Class<T> classT) {
    TypeReference<List<T>> typeReference = new TypeReference<List<T>>() {};
    List<T> results =  new ObjectMapper().convertValue(data, typeReference);
  //TypeReference<List<DeviceUpdateLogInfoInCampaign>> typeReference = new TypeReference<List<DeviceUpdateLogInfoInCampaign>>() {/* */};
    //List<DeviceUpdateLogInfoInCampaign> devices = new ObjectMapper().convertValue(item.getValue(),  typeReference);
    return results;
  }
	
	public static <T> List<T> toList(String json) {
    try {
      return objectMapper.readValue(json, new TypeReference<List<T>>() {
      });
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

	public static String toJson(Object o) {
		try {
			return objectMapper.writeValueAsString(o);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T convertObject(Object sourceObj, Class<T> classT) {
		return objectMapper.convertValue(sourceObj, classT);
	}

	public static <S, T> void convertObjectUpdate(S fromObj, T targetObj)
			throws IllegalArgumentException, IllegalAccessException {
		Class<? extends Object> fromClass = fromObj.getClass();
		Field[] fromFields = fromClass.getDeclaredFields();

		Class<? extends Object> toClass = targetObj.getClass();
		//Field[] toFields = toClass.getDeclaredFields();

		for (Field fromField : fromFields) {
			Field toField = null;
			try {
				toField = toClass.getDeclaredField(fromField.getName());
			} catch (NoSuchFieldException e) {
				// do nothing
			} catch (SecurityException e) {
				// do nothing
			}
			if (!Objects.isNull(toField) && toField.getType().equals(fromField.getType())) {
				DontUpdate annotation = toField.getAnnotation(DontUpdate.class);
				if (Objects.isNull(annotation)) {
					toField.setAccessible(true);
					fromField.setAccessible(true);
					if (!Objects.isNull(fromField.get(fromObj))) {
					  toField.set(targetObj, fromField.get(fromObj));
					}
					
				}
			}
		}
	}
	
	
  public static <S, T> void customizeConvert(S fromObj, T targetObj)
      throws IllegalArgumentException, IllegalAccessException {
    Class<? extends Object> fromClass = fromObj.getClass();
    Field[] fromFields = fromClass.getDeclaredFields();

    Class<? extends Object> toClass = targetObj.getClass();
    // Field[] toFields = toClass.getDeclaredFields();

    for (Field fromField : fromFields) {
      Field toField = null;
      try {
        toField = toClass.getDeclaredField(fromField.getName());
      } catch (NoSuchFieldException e) {
        // do nothing
      } catch (SecurityException e) {
        // do nothing
      }
      if (!Objects.isNull(toField) && toField.getType().equals(fromField.getType())) {
        toField.setAccessible(true);
        fromField.setAccessible(true);
        if (!Objects.isNull(fromField.get(fromObj))) {
          toField.set(targetObj, fromField.get(fromObj));
        }
      }
    }
  }
}
