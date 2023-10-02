package page.aaws.b01.repository.querydsl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import page.aaws.b01.domain.QTodoEntity;
import page.aaws.b01.domain.TodoEntity;
import page.aaws.b01.dto.PageRequestDto;

public class TodoGetTodosByPageImpl
        extends QuerydslRepositorySupport
        implements TodoGetTodosByPage {
    public TodoGetTodosByPageImpl() {
        super(TodoEntity.class);
    }

    @Override
    public Page<TodoEntity> getTodosByPage(PageRequestDto pageRequestDto, Pageable pageable) {
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

        this.getQuerydsl().applyPagination(pageable, query);

        return new PageImpl<TodoEntity>(query.fetch(), pageable, query.fetchCount());
    }
}
