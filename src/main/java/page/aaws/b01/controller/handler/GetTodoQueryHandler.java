package page.aaws.b01.controller.handler;

import page.aaws.b01.controller.cqrs.query.GetTodoQuery;
import page.aaws.b01.dto.TodoDto;

public interface GetTodoQueryHandler extends CommandAndQueryHandler<GetTodoQuery, TodoDto> {
}
