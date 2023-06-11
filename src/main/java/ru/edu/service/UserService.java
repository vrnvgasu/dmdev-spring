package ru.edu.service;

import static ru.edu.database.entity.QUser.user;

import com.querydsl.core.types.Predicate;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.edu.database.entity.User;
import ru.edu.database.querydsl.QPredicates;
import ru.edu.database.repository.CompanyRepository;
import ru.edu.database.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import ru.edu.dto.UserCreateEditDto;
import ru.edu.dto.UserFilter;
import ru.edu.dto.UserReadDto;
import ru.edu.mapper.UserCreateEditMapper;
import ru.edu.mapper.UserReadMapper;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;
  private final UserReadMapper userReadMapper;
  private final UserCreateEditMapper userCreateEditMapper;
  private final ImageService imageService;

//  @PostFilter("filterObject.role.name().equals('ADMIN')")
//  @PostFilter("@companyService.findById(filterObject.company.id()).isPresent()")
  public Page<UserReadDto> findAll(UserFilter filter, Pageable pageable) {
    Predicate predicate = QPredicates.builder()
      .add(filter.firstname(), user.firstname::containsIgnoreCase)
      .add(filter.lastname(), user.lastname::containsIgnoreCase)
      .add(filter.birthDate(), user.birthDate::before)
      .build();

    return userRepository.findAll(predicate, pageable)
      .map(userReadMapper::map);
  }

  public List<UserReadDto> findAll() {
    return userRepository.findAll().stream()
      .map(userReadMapper::map)
      .toList();
  }

  public Optional<UserReadDto> findById(Long id) {
    return userRepository.findById(id)
      .map(userReadMapper::map);
  }

  public Optional<byte[]> findAvatar(Long id) {
    return userRepository.findById(id)
      .map(User::getImage) // взяли название картинки
      .filter(StringUtils::hasText) // идем дальше, если название существует
      .flatMap(imageService::get);

  }

  @Transactional
  public UserReadDto create(UserCreateEditDto userDto) {
    return Optional.of(userDto)
      .map(dto -> {
        uploadImage(dto.getImage());
        return userCreateEditMapper.map(dto);
      })
      .map(userRepository::save)
      .map(userReadMapper::map)
      .orElseThrow();
  }

  @Transactional
  public Optional<UserReadDto> update(Long id, UserCreateEditDto userDto) {
    return userRepository.findById(id)
      .map(entity -> {
        uploadImage(userDto.getImage());
        return userCreateEditMapper.map(userDto, entity);
      })
      .map(userRepository::saveAndFlush)
      .map(userReadMapper::map);
  }

  @SneakyThrows
  private void uploadImage(MultipartFile image) {
    if (!image.isEmpty()) {
      imageService.upload(image.getOriginalFilename(), image.getInputStream());
    }
  }

  @Transactional
  public boolean delete(Long id) {
    return userRepository.findById(id)
      .map(entity -> {
        userRepository.delete(entity);
        userRepository.flush();
        return true;
      })
      .orElse(false);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByUsername(username)
      // вернем готовую реализацию UserDetails из спринга
      .map(user -> new org.springframework.security.core.userdetails.User(
        user.getUsername(),
        user.getPassword(),
        Collections.singleton(user.getRole())
      ))
      .orElseThrow(() -> new UsernameNotFoundException("Failed to retrieve user: " + username));
  }

}
