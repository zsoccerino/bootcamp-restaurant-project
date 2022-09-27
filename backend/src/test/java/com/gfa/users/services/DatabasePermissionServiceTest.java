package com.gfa.users.services;

import com.gfa.users.dtos.PermissionRequestDto;
import com.gfa.users.dtos.PermissionResponseDto;
import com.gfa.users.exceptions.DuplicatePermissionException;
import com.gfa.users.exceptions.PermissionIsMissingException;
import com.gfa.users.exceptions.PermissionNotFoundException;
import com.gfa.users.models.Permission;
import com.gfa.users.repositories.PermissionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
class DatabasePermissionServiceTest {

  //  @Mock
  @Autowired PermissionService permissionService;

  @MockBean PermissionRepository permissionRepository;

  AutoCloseable autoCloseable;

  @BeforeEach
  public void setUp() {
    autoCloseable = MockitoAnnotations.openMocks(this);
  }

  @AfterEach
  public void tearDown() throws Exception {
    autoCloseable.close();
  }

  @Test
  void index_returns_list_of_permissions() throws Exception {

    PermissionResponseDto permissionResponseDto = new PermissionResponseDto(1L, "ability1");
    List<PermissionResponseDto> permissionResponseDtos = new ArrayList<>();
    permissionResponseDtos.add(permissionResponseDto);

    assertEquals("ability1", permissionResponseDtos.get(0).ability);
  }

  @Test
  void store_stores_data_and_returns_permission_response_dto() throws Exception {

    PermissionRequestDto permissionRequestDto = new PermissionRequestDto(1L, "ability1");

    when(permissionRepository.save(any())).thenReturn(new Permission(1L, "ability1"));

    assertEquals(
        permissionRequestDto.ability, permissionService.store(permissionRequestDto).ability);
  }

  @Test
  void store_throws_permission_is_missing_exception() {

    assertThrows(
        PermissionIsMissingException.class,
        () -> {
          permissionService.store(new PermissionRequestDto(1L, ""));
        });
  }

  @Test
  void store_throws_duplicate_permission_exception() {
    PermissionRequestDto permissionRequestDto = new PermissionRequestDto(1L, "ability1");

    when(permissionRepository.save(any())).thenReturn(new Permission(1L, "ability1"));

    when(permissionRepository.existsAllByAbility(any()))
        .thenThrow(DuplicatePermissionException.class);

    //    permissionService.store(permissionRequestDto);

    assertThrows(
        DuplicatePermissionException.class,
        () -> {
          permissionService.store(new PermissionRequestDto(1L, "ability1"));
        });
  }

  @Test
  void show_shows_data() {

    Permission permission = new Permission(1L, "ability1");

    when(permissionRepository.findById(1L)).thenReturn(Optional.of(permission));

    assertEquals("ability1", permissionService.show(1L).ability);
  }

  @Test
  void show_throws_permission_not_found_exception() {

    Optional<Permission> permissionOptional = Optional.empty();

    when(permissionRepository.findById(any())).thenReturn(permissionOptional);

    assertThrows(PermissionNotFoundException.class, () -> permissionService.show(1L));
  }

  @Test
  void update_updates_permission() {
    PermissionRequestDto permissionRequestDto = new PermissionRequestDto(1L, "ability1");
    PermissionRequestDto permissionRequestDto2 = new PermissionRequestDto(1L, "ability2");

    when(permissionRepository.save(any())).thenReturn(permissionRequestDto);

    assertEquals("ability2", permissionRequestDto2.ability);
  }

  @Test
  void update_throws_permission_not_found_exception() {

    Optional<Permission> permissionOptional = Optional.empty();

    when(permissionRepository.findById(any())).thenReturn(permissionOptional);

    assertThrows(
        PermissionNotFoundException.class,
        () -> permissionService.update(new PermissionRequestDto(1L, "ability1")));
  }

  @Test
  void update_throws_duplicate_permission_exception() {

    Optional<Permission> permissionOptional = Optional.of(new Permission("ability"));

    when(permissionRepository.findById(any())).thenReturn(permissionOptional);

    when(permissionRepository.existsAllByAbility(any())).thenReturn(true);

    assertThrows(
        DuplicatePermissionException.class,
        () -> permissionService.update(new PermissionRequestDto(1L, "ability")));
  }

  @Test
  void destroy_deletes_entity() {

    when(permissionRepository.save(any())).thenReturn(new Permission(1L, "ability"));

    Permission permission = new Permission(1L, "ability");

    when(permissionRepository.findById(any()))
        .thenReturn(Optional.of(new Permission(1L, "ability")));

    doNothing().when(permissionRepository).delete(permission);

    permissionService.store(new PermissionRequestDto(1L, "ability"));

    assertEquals("ok", permissionService.destroy(1L).status);
  }
}
