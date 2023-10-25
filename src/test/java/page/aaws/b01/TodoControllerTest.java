package page.aaws.b01;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import page.aaws.b01.controller.TodoController;
import page.aaws.b01.controller.handler.*;
import page.aaws.b01.dto.PageDto;
import page.aaws.b01.dto.TodoDto;
import page.aaws.b01.util.SnowflakeIdGenerator;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TodoController.class)
@Import({TestConfig.class})
@MockBean(JpaMetamodelMappingContext.class)
public class TodoControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    AddNewTodoCommandHandler addNewTodoCommandHandler;

    @MockBean
    DeleteTodoCommandHandler deleteTodoCommandHandler;

    @MockBean
    GetTodoQueryHandler getTodoQueryHandler;

    @MockBean
    GetTodosByPageQueryHandler getTodosByPageQueryHandler;

    @MockBean
    UpdateTodoCommandHandler updateTodoCommandHandler;

    @Autowired
    SnowflakeIdGenerator snowflakeIdGenerator;

    @Test
    @DisplayName("새로운 TODO 등록")
    void addNewTodoTest() throws Exception {
        TodoDto todoDto = TodoDto.builder()
                .subject("subject")
                .description("description")
                .done(false)
                .periodStartedAt(LocalDateTime.now())
                .periodEndedAt(LocalDateTime.now().plusDays(7))
                .build();

        TodoDto responseDto = todoDto.clone();
        responseDto.setId(this.snowflakeIdGenerator.nextId());

        // given
        given(this.addNewTodoCommandHandler.process(any())).willReturn(responseDto);

        // when
        ResultActions resultActions = this.mockMvc.perform(
                post("/todo/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(todoDto))
                );

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.subject").exists())
                .andExpect(jsonPath("$.description").exists())
                .andExpect(jsonPath("$.done").exists())
                .andExpect(jsonPath("$.periodStartedAt").exists())
                .andExpect(jsonPath("$.periodEndedAt").exists())
                .andExpect(jsonPath("$.periodEndedAt").exists())
                .andDo(print());
    }

    @Test
    @DisplayName("TODO 삭제(200 OK)")
    void deleteTodoTest1() throws Exception {
        Long id = this.snowflakeIdGenerator.nextId();

        given(this.deleteTodoCommandHandler.process(any())).willReturn(id);

        ResultActions resultActions = this.mockMvc.perform(
                delete("/todo/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        resultActions.andExpect(status().isOk());
    }

    @Test
    @DisplayName("TODO 삭제(404 NOT FOUND)")
    void deleteTodoTest2() throws Exception {
        long id = this.snowflakeIdGenerator.nextId();

        when(this.deleteTodoCommandHandler.process(any())).thenThrow(new NoSuchElementException(TodoController.NOT_FOUND));

        ResultActions resultActions = this.mockMvc.perform(
                delete("/todo/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        resultActions
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("TODO 열람(200 OK)")
    void getTodoTest1() throws Exception {
        TodoDto todoDto = TodoDto.builder()
                .id(this.snowflakeIdGenerator.nextId())
                .subject("subject")
                .description("description")
                .done(false)
                .periodStartedAt(LocalDateTime.now())
                .periodEndedAt(LocalDateTime.now().plusDays(7))
                .build();

        given(this.getTodoQueryHandler.process(any())).willReturn(todoDto);

        ResultActions resultActions = this.mockMvc.perform(
                get("/todo/" + todoDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        );

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.subject").exists())
                .andExpect(jsonPath("$.description").exists())
                .andExpect(jsonPath("$.done").exists())
                .andExpect(jsonPath("$.periodStartedAt").exists())
                .andExpect(jsonPath("$.periodEndedAt").exists())
                .andExpect(jsonPath("$.periodEndedAt").exists())
                .andDo(print());
    }

    @Test
    @DisplayName("TODO 열람(404 NOT FOUND)")
    void getTodoTest2() throws Exception {
        long id = this.snowflakeIdGenerator.nextId();

        when(this.getTodoQueryHandler.process(any())).thenThrow(new NoSuchElementException(TodoController.NOT_FOUND));

        ResultActions resultActions = this.mockMvc.perform(
                get("/todo/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        resultActions
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("TODO 목록 열람")
    void getTodosByPageTest() throws Exception {
        TodoDto todoDto = TodoDto.builder()
                .id(this.snowflakeIdGenerator.nextId())
                .subject("subject")
                .description("description")
                .done(false)
                .periodStartedAt(LocalDateTime.now())
                .periodEndedAt(LocalDateTime.now().plusDays(7))
                .build();

        List<TodoDto> items = new ArrayList<>();
        items.add(todoDto);

        PageDto<TodoDto> pageDto = PageDto.<TodoDto>builder()
                .number(0)
                .size(10)
                .totalElements(1L)
                .items(items)
                .build();

        given(this.getTodosByPageQueryHandler.process(any())).willReturn(pageDto);

        ResultActions resultActions = this.mockMvc.perform(
                get("/todo/")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.number").exists())
                .andExpect(jsonPath("$.size").exists())
                .andExpect(jsonPath("$.totalElements").exists())
                .andExpect(jsonPath("$.items").exists())
                .andDo(print());
    }

    @Test
    @DisplayName("TODO 수정")
    void updateTodoTest() throws Exception {
        TodoDto todoDto = TodoDto.builder()
                .id(this.snowflakeIdGenerator.nextId())
                .subject("subject")
                .description("description")
                .done(false)
                .periodStartedAt(LocalDateTime.now())
                .periodEndedAt(LocalDateTime.now().plusDays(7))
                .build();

        given(this.updateTodoCommandHandler.process(any())).willReturn(todoDto);

        ResultActions resultActions = this.mockMvc.perform(
                put("/todo/" + todoDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(todoDto))
        );

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.subject").exists())
                .andExpect(jsonPath("$.description").exists())
                .andExpect(jsonPath("$.done").exists())
                .andExpect(jsonPath("$.periodStartedAt").exists())
                .andExpect(jsonPath("$.periodEndedAt").exists())
                .andExpect(jsonPath("$.periodEndedAt").exists())
                .andDo(print());
    }
}
