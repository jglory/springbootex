package page.aaws.b01;

import lombok.extern.log4j.Log4j2;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Sort;
import page.aaws.b01.domain.Board;
import page.aaws.b01.repository.BoardRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class BoardRepositoryTests {
    @Autowired
    private BoardRepository boardRepository;

    @Test
    void testInsert() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Board board = Board.builder()
                    .title("title " + i)
                    .content("content " + i)
                    .writer("writer " + i)
                    .build();
            Board result = boardRepository.save(board);
            log.info("id: " + result.getId());
        });
    }

    @Test
    void testSelect() {
        Long id = 100L;

        Optional<Board> result = boardRepository.findById(id);
        Board board = result.orElseThrow();
        log.info(board);
    }

    @Test
    void testUpdate() {
        Long id = 100L;

        Optional<Board> result = boardRepository.findById(id);
        Board board = result.orElseThrow();
        board.change("title: 100[u]", "content: 100[u]");
        boardRepository.save(board);
    }

    @Test
    void testDelete() {
        Long id = 1L;
        boardRepository.deleteById(id);
    }

    @Test
    void testPaging() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());
        Page<Board> result = boardRepository.findAll(pageable);

        log.info("total count: " + result.getTotalElements());
        log.info("total pages: " + result.getTotalPages());
        log.info("page number: " + result.getNumber());
        log.info("page size: " + result.getSize());

        List<Board> list = result.getContent();
        list.forEach(board -> log.info(board));
    }
}
