package page.aaws.b01;

import lombok.extern.log4j.Log4j2;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import page.aaws.b01.dto.PageDto;
import page.aaws.b01.dto.PageRequestDto;
import page.aaws.b01.dto.TodoDto;
import page.aaws.b01.service.TodoService;

import java.time.LocalDateTime;

@SpringBootTest
@Log4j2
public class TodoServiceTests {
    @Autowired
    TodoService todoService;

    @Test
    void testAddNewTodo() {
        log.info(todoService.getClass().getName());

        TodoDto todoDto = TodoDto.builder()
                .subject("test subject")
                .description("test description")
                .done(false)
                .periodStartedAt(LocalDateTime.now())
                .periodEndedAt(LocalDateTime.now())
                .build();
        todoDto = todoService.addNewTodo(todoDto);
        log.info(todoDto);
    }

    @Test
    void testGetTodo() {
        TodoDto todoDto = this.todoService.getTodo(350L);
        log.info(todoDto);
    }

    @Test
    void testGetTodosByPage() {
        PageDto<TodoDto> pageDto = this.todoService.getTodosByPage(PageRequestDto.builder().number(1).size(10).build());
        log.info(pageDto);
    }

    @Test
    void testUpdateTodo() {
        todoService.updateTodo(TodoDto.builder()
                .id(350L)
                .subject("subject - updated")
                .description("description - updated")
                .periodStartedAt(LocalDateTime.now())
                .periodEndedAt(LocalDateTime.now())
                .build()
        );
    }
}
