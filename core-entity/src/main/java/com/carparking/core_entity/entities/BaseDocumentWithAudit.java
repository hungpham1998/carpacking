package com.carparking.core_entity.entities;

import com.htsc.core_utils.annotation.DontUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseDocumentWithAudit extends BaseDocument {

  @DontUpdate
  @CreatedDate
  private Instant createdAt;

  @DontUpdate
  @CreatedBy
  private String createdBy;

  @LastModifiedDate
  private Instant updatedAt;

  @CreatedBy
  private String updatedBy;
}
