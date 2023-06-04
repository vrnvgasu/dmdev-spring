package ru.edu.database.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;

// name - название; attributeNodes - что подтягиваем
// @NamedAttributeNode("company") - подятягиваем сущность company
@NamedEntityGraph(
  name = "User.company",
  attributeNodes = @NamedAttributeNode("company")
)
@Data
@ToString(exclude = "userChats")
@EqualsAndHashCode(of = "username")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
// NOT_AUDITED - не аудировать связь на одну сущность
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class User extends AuditingEntity<Long> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String username;

  // по умолчанию преобразует в camelCase
//  @Column(name = "birth_date")
  private LocalDate birthDate;

  private String firstname;

  private String lastname;

  @Enumerated(EnumType.STRING) // отображаем строкой
  private Role role;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "company_id")
  private Company company;

  @Builder.Default
  @OneToMany(mappedBy = "user")
  // для списков надо вручную указывать, чтобы не аудирвоать
  @NotAudited
  private List<UserChat> userChats = new ArrayList<>();

}
