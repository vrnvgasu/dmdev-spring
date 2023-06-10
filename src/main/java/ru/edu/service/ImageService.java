package ru.edu.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageService {

  // на проде переопределим конфиг app.image.bucket - место хранения
  @Value("${app.image.bucket:/Users/dmitrii/code/edu/java/dianiz/spring/spring/images}")
  private final String bucket;

  @SneakyThrows
  public void upload(String imagePath, InputStream content) {
    Path fullImagePath = Path.of(bucket, imagePath);

    try (content) {
      // создадим файлохранилище на всякий случай
      Files.createDirectories(fullImagePath.getParent());

      // content.readAllBytes() - полностью взяли картинку по байтам. Для видео такое не подойдет
      // StandardOpenOption.CREATE - создать, если не существует
      // StandardOpenOption.TRUNCATE_EXISTING - перезаписать, если существует
      Files.write(
        fullImagePath,
        content.readAllBytes(),
        StandardOpenOption.CREATE,
        StandardOpenOption.TRUNCATE_EXISTING
      );
    }
  }

  // сразу возвращаем массив байт
  // подходит для небольших файлов. На видео упадет
  @SneakyThrows
  public Optional<byte[]> get(String imagePath) {
    Path fullImagePath = Path.of(bucket, imagePath);

    return Files.exists(fullImagePath)
      ? Optional.of(Files.readAllBytes(fullImagePath))
      : Optional.empty();
  }

}
