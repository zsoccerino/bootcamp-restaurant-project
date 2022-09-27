package com.gfa.users.controllers;

import com.gfa.users.dtos.PermissionResponseDto;
import com.gfa.users.dtos.StatusResponseDto;
import com.gfa.users.exceptions.DuplicatePermissionException;
import com.gfa.users.exceptions.PermissionIsMissingException;
import com.gfa.users.exceptions.PermissionNotFoundException;
import com.gfa.users.services.PermissionService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
    controllers = PermissionRestController.class,
    excludeAutoConfiguration = SecurityAutoConfiguration.class)
@ContextConfiguration
class PermissionRestControllerTest {

  @MockBean PermissionService permissionService;

  @Autowired MockMvc mockMvc;

  @Test
  void index_returns_list_of_permissions() throws Exception {

    PermissionResponseDto permissionResponseDto = new PermissionResponseDto(1L, "ability1");

    List<PermissionResponseDto> permissionResponseDtos = new ArrayList<>();

    permissionResponseDtos.add(permissionResponseDto);

    when(permissionService.findAll())
        .thenReturn(permissionResponseDtos);

    mockMvc
        .perform(get("/permissions"))
        .andDo(print())
        .andExpect(status().isOk())
        //            .andExpect(jsonPath("$", hasSize(0)));
        .andExpect(jsonPath("$[0].id", is(1)))
        .andExpect(jsonPath("$[0].ability", is("ability1")));
  }

  @Test
  void store_stores_data() throws Exception {

    PermissionResponseDto permissionInput = new PermissionResponseDto(1L, "ability2");

    when(permissionService.store(Mockito.any())).thenReturn(permissionInput);

    mockMvc
        .perform(post("/permissions/").content("{}").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id", is(1)))
        .andExpect(jsonPath("$.ability", is("ability2")));
  }

  @Test
  void store_bad_request() throws Exception {

    when(permissionService.store(Mockito.any())).thenThrow(PermissionIsMissingException.class);

    mockMvc
        .perform(post("/permissions/").content("{}").contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(content().json("{\"message\":\"Ability is required\"}"));
  }

  @Test
  void store_conflict() throws Exception {

    when(permissionService.store(Mockito.any())).thenThrow(DuplicatePermissionException.class);

    mockMvc
        .perform(post("/permissions/").content("{}").contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isConflict())
        .andExpect(content().json("{\"message\":\"Ability already exists\"}"));
  }

  @Test
  void show_returns_permission() throws Exception {

    PermissionResponseDto permissionInput = new PermissionResponseDto(1L, "ability2");

    when(permissionService.show(Mockito.any())).thenReturn(permissionInput);

    mockMvc
        .perform(get("/permissions/1").content("{}").contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(1)))
        .andExpect(jsonPath("$.ability", is("ability2")));
  }

  @Test
  void show_throws_number_format_exception() throws Exception {
    when(permissionService.show(Mockito.any())).thenThrow(NumberFormatException.class);

    mockMvc
        .perform(get("/permissions/a").contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(content().json("{\"message\":\"Invalid id\"}"));
  }

  @Test
  void show_throws_not_found_exception() throws Exception {

    when(permissionService.show(Mockito.any())).thenThrow(PermissionNotFoundException.class);

    mockMvc
        .perform(get("/permissions/2").contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isNotFound())
        .andExpect(content().json("{\"message\":\"Permission not found\"}"));
  }

  @Test
  void update_updates_database() throws Exception {

    PermissionResponseDto permissionInput = new PermissionResponseDto(1L, "ability2");

    when(permissionService.update(Mockito.any())).thenReturn(permissionInput);

    mockMvc
        .perform(patch("/permissions/1").content("{}").contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(1)))
        .andExpect(jsonPath("$.ability", is("ability2")));
  }

  @Test
  void update_throws_number_format_exception() throws Exception {

    when(permissionService.update(any())).thenThrow(NumberFormatException.class);

    mockMvc
        .perform(patch("/permissions/1").content("{}").contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message", is("Invalid id")));
  }

  @Test
  void update_throws_permission_not_found_exception() throws Exception {

    when(permissionService.update(any())).thenThrow(PermissionNotFoundException.class);

    mockMvc
        .perform(patch("/permissions/1").content("{}").contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.message", is("Permission not found")));
  }

  @Test
  void update_throws_illegal_argument_exception() throws Exception {

    when(permissionService.update(any())).thenThrow(IllegalArgumentException.class);

    mockMvc
        .perform(patch("/permissions/1").content("{}").contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message", is("Invalid data")));
  }

  @Test
  void destroy_deletes_data() throws Exception {

    StatusResponseDto statusResponseDto = new StatusResponseDto("ok");

    when(permissionService.destroy(any())).thenReturn(statusResponseDto);

    mockMvc
        .perform(delete("/permissions/1").content("{}").contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isNoContent())
        .andExpect(jsonPath("$.status", is("ok")));
  }

  @Test
  void destroy_throws_number_format_exception() throws Exception {

    when(permissionService.destroy(any())).thenThrow(NumberFormatException.class);

    mockMvc
        .perform(delete("/permissions/1").content("{}").contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message", is("Invalid id")));
  }

  @Test
  void detroy_throws_permission_not_found_exception() throws Exception {

    when(permissionService.destroy(any())).thenThrow(PermissionNotFoundException.class);

    mockMvc
        .perform(delete("/permissions/1").content("{}").contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.message", is("Permission not found")));
  }
}
