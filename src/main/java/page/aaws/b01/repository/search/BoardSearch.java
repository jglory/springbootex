package page.aaws.b01.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import page.aaws.b01.domain.Board;

public interface BoardSearch {
    Page<Board> search1(Pageable pageable);
    Page<Board> searchAll(String[] types, String keyword, Pageable pageable);
}
