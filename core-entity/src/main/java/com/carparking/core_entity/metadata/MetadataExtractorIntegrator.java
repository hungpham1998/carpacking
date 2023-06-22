package com.carparking.core_entity.metadata;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.boot.Metadata;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;

import java.util.*;

@Slf4j
public class MetadataExtractorIntegrator implements Integrator {

  public static final MetadataExtractorIntegrator INSTANCE =
      new MetadataExtractorIntegrator();
  private Metadata metadata;
  private final Map<String, Set<String>> tableColumnMap = new HashMap<>();

  public Metadata getMetadata() {
    return this.metadata;
  }

  public boolean checkColumnExist(String entityClass, String columnName) {
    return this.tableColumnMap.containsKey(entityClass)
        && this.tableColumnMap.get(entityClass).contains(columnName);
  }

  @Override
  public void integrate(
      Metadata metadata,
      SessionFactoryImplementor sessionFactory,
      SessionFactoryServiceRegistry serviceRegistry
  ) {
    this.metadata = metadata;

    for (PersistentClass persistentClass : metadata.getEntityBindings()) {
      Set<String> columns = new HashSet<>();
      Iterator<Column> columnIterator =
          persistentClass.getTable().getColumnIterator();
      while (columnIterator.hasNext()) {
        Column c = columnIterator.next();
        columns.add(c.getName());
      }
      tableColumnMap.put(persistentClass.getClassName(), columns);
    }
  }

  @Override
  public void disintegrate(
      SessionFactoryImplementor sessionFactory,
      SessionFactoryServiceRegistry serviceRegistry
  ) {
    // do nothing
  }
}
