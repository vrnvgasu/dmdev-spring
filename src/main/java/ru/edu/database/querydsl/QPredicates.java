package ru.edu.database.querydsl;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    // если в условие ничего не пришло, то подставляем условие true=true
    return Optional.ofNullable(ExpressionUtils.allOf(predicates))
      .orElseGet(() -> Expressions.asBoolean(true).isTrue());
  }

  // логическое ИЛИ
  public Predicate buildOr() {
    return Optional.ofNullable(ExpressionUtils.anyOf(predicates))
      .orElseGet(() -> Expressions.asBoolean(true).isTrue());
  }

}
