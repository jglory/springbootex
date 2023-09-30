package page.aaws.b01.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import page.aaws.b01.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query("select b from Board b where b.writer = ?1")
    Page<Board> findByWriter(String writer, Pageable pageable);
}
