package page.aaws.b01;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

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
import page.aaws.b01.dto.TodoDto;
import page.aaws.b01.util.SnowflakeIdGenerator;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    @DisplayName("새로운 TODO 가 잘 등록 되는지 테스트")
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
}
