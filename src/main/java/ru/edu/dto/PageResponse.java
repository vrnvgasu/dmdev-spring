package ru.edu.dto;

import java.util.List;
import lombok.Value;
import org.springframework.data.domain.Page;

@Value
public class PageResponse<T> {

  List<T> content;
  Metadata metadata;

  public static <T> PageResponse<T> of(Page<T> page) {
    var metadata = new Metadata(page.getNumber(), page.getSize(), page.getTotalElements());
    return new PageResponse(page.getContent(), metadata);
  }

  @Value
  public static class Metadata {
    int page;
    int size;
    long TotalElements;
  }

}
