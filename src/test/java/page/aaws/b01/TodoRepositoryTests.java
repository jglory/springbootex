package page.aaws.b01;

import lombok.extern.log4j.Log4j2;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import page.aaws.b01.domain.TodoEntity;
import page.aaws.b01.repository.TodoRepository;

import java.util.stream.IntStream;

import java.time.LocalDateTime;

@SpringBootTest
@Log4j2
public class TodoRepositoryTests {
    @Autowired
    TodoRepository todoRepository;

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
}
