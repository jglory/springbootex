package page.aaws.b01;

import lombok.extern.log4j.Log4j2;

import org.junit.jupiter.api.Test;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import page.aaws.b01.domain.TodoEntity;
import page.aaws.b01.dto.PageRequestDto;
import page.aaws.b01.dto.TodoDto;
import page.aaws.b01.repository.TodoRepository;

import java.util.stream.IntStream;

import java.time.LocalDateTime;

@SpringBootTest
@Log4j2
public class TodoRepositoryTests {
    @Autowired
    TodoRepository todoRepository;

    @Autowired
    ModelMapper modelMapper;

    @Test
    void testInsert() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            TodoEntity todoEntity = TodoEntity.builder()
                    .subject("subject " + i)
                    .description("description " + i)
                    .periodStartedAt(LocalDateTime.now())
                    .periodEndedAt(LocalDateTime.now())
                    .build();
            TodoEntity result = todoRepository.save(todoEntity);
            log.info("id: " + result.getId());
        });
    }

    @Test
    void testGetTodosByPage() {
        PageRequestDto pageRequestDto = PageRequestDto.builder()
                .number(1)
                .size(10)
                .done(true)
//                .types(new String[]{"subject", "description"})
//                .keyword("abc")
//                .periodStartedAt(LocalDateTime.now())
//                .periodEndedAt(LocalDateTime.now())
                .build();
        Page<TodoEntity> page = todoRepository.getTodosByPage(pageRequestDto, PageRequest.of(pageRequestDto.getNumber(), pageRequestDto.getSize(), Sort.by("createdAt").ascending()));
        log.info(page);
        log.info(page.getContent().stream().map(item -> this.modelMapper.map(item, TodoDto.class)).toList());
    }
}
