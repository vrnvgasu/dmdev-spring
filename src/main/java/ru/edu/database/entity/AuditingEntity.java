package ru.edu.database.entity;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@MappedSuperclass // чтобы другие сущности могли наследоваться
// AuditingEntityListener - готовый листнер из спринга
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditingEntity<T extends Serializable> implements BaseEntity<T> {

  @CreatedDate // аннотация для листнера, чтобы понимал, что обновлять
  private Instant createdAt;

  @LastModifiedDate // аннотация для листнера, чтобы понимал, что обновлять
  private Instant modifiedAt;

  @CreatedBy
  private String createdBy;

  @LastModifiedBy
  private String modifiedBy;

}
