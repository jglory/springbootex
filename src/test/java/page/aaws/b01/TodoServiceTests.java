package page.aaws.b01;

import lombok.extern.log4j.Log4j2;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        todoDto.setId(todoService.addNewTodo(todoDto));
        log.info(todoDto);
    }
}
