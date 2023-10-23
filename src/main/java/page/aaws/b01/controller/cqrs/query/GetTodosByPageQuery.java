package page.aaws.b01.controller.cqrs.query;

import page.aaws.b01.cqrs.Command;
import page.aaws.b01.dto.PageRequestDto;

public interface GetTodosByPageQuery extends Command {
    PageRequestDto getPageRequestDto();
}
