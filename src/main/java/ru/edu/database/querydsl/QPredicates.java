package ru.edu.database.querydsl;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
// Помогает создавать динамические запросы
public class QPredicates {

  private final List<Predicate> predicates = new ArrayList<>();

  public static QPredicates builder() {
    return new QPredicates();
  }

  public <T> QPredicates add(T object, Function<T, Predicate> function) {
    if (object != null) {
      predicates.add(function.apply(object));
    }

    return this;
  }

  // логическое И
  public Predicate build() {
    return ExpressionUtils.allOf(predicates);
  }

  // логическое ИЛИ
  public Predicate buildOr() {
    return ExpressionUtils.anyOf(predicates);
  }

}
