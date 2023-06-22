package com.carparking.core_utils.utils;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class ReflectionUtil {

  /**
   * Get set of field name of a class, with exclusion of all ones that
   * annotated with given classes.
   *
   * @param targetClass              class to get fields
   * @param annotationsForExclusion  annotation that marked for exclusion
   * @return set of field name
   */
  public static Set<String> getFields(
      Class<?> targetClass,
      List<Class<? extends Annotation>> annotationsForExclusion
  ) {
    Set<String> fields = new HashSet<>();

    for (Class<?> cls = targetClass;
         Objects.nonNull(cls);
         cls = cls.getSuperclass()
    ) {
      fields.addAll(
          Arrays.stream(cls.getDeclaredFields())
              .filter(
                  field -> annotationsForExclusion.stream()
                      .noneMatch(field::isAnnotationPresent)
              )
              .map(Field::getName)
              .collect(Collectors.toSet())
      );
    }

    return fields;
  }

  /**
   * New object instance with uncheck exception.
   *
   * @param cls target class of object instance
   * @return object instance
   * @param <T> object type
   */
  public static <T> T getInstanceOf(Class<T> cls) {
    try {
      return cls.newInstance();
    } catch (InstantiationException | IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Convert text to specific data type.
   *
   * @param data text
   * @param type type to convert to
   * @return
   */
  public static Object convertData(String data, Class<?> type) {
    PropertyEditor editor = PropertyEditorManager.findEditor(type);
    editor.setAsText(data);
    return editor.getValue();
  }
}
