package page.aaws.b01.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import page.aaws.b01.domain.TodoEntity;

public interface TodoRepository extends JpaRepository<TodoEntity, Long> {
}
