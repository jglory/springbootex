package page.aaws.b01.controller.handler;

import page.aaws.b01.controller.cqrs.query.GetTodosByPageQuery;
import page.aaws.b01.dto.PageDto;
import page.aaws.b01.dto.TodoDto;

public interface GetTodosByPageQueryHandler extends CommandAndQueryHandler<GetTodosByPageQuery, PageDto<TodoDto>> {
}
