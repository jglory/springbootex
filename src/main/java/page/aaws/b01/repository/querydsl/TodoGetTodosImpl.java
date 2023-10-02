package page.aaws.b01.repository.querydsl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;

import java.util.List;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import page.aaws.b01.domain.QTodoEntity;
import page.aaws.b01.domain.TodoEntity;
import page.aaws.b01.dto.PageRequestDto;

public class TodoGetTodosImpl
        extends QuerydslRepositorySupport
        implements TodoGetTodos {
    public TodoGetTodosImpl() {
        super(TodoEntity.class);
    }

    @Override
    public List<TodoEntity> getTodos(PageRequestDto pageRequestDto) {
        QTodoEntity todo = QTodoEntity.todoEntity;
        JPQLQuery<TodoEntity> query = from(todo);

        if (pageRequestDto.getTypes().length > 0 && pageRequestDto.getKeyword().equals("") == false) {
            BooleanBuilder booleanBuilder = new BooleanBuilder();

            for (String type: pageRequestDto.getTypes()) {
                switch (type) {
                    case "subject" -> booleanBuilder.or(todo.subject.contains(pageRequestDto.getKeyword()));
                    case "description" -> booleanBuilder.or(todo.description.contains(pageRequestDto.getKeyword()));
                }
            }
            query.where(booleanBuilder);
        }

        if (pageRequestDto.getDone() != null) {
            query.where(todo.done.eq(pageRequestDto.getDone()));
        }

        if (pageRequestDto.getPeriodStartedAt() != null) {
            query.where(todo.periodStartedAt.gt(pageRequestDto.getPeriodStartedAt()));
        }
        if (pageRequestDto.getPeriodEndedAt() != null) {
            query.where(todo.periodEndedAt.lt(pageRequestDto.getPeriodEndedAt()));
        }

        return query.fetch();
    }
}
