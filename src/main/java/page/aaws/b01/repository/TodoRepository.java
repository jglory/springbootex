package page.aaws.b01.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import page.aaws.b01.domain.TodoEntity;
import page.aaws.b01.repository.querydsl.TodoGetTodos;
import page.aaws.b01.repository.querydsl.TodoGetTodosByPage;
public interface TodoRepository
        extends JpaRepository<TodoEntity, Long>, TodoGetTodos, TodoGetTodosByPage {
}
