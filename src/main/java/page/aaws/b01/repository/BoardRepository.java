package page.aaws.b01.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import page.aaws.b01.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
