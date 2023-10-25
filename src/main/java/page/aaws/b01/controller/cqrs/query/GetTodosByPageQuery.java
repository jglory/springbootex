package page.aaws.b01.controller.cqrs.query;

import page.aaws.b01.cqrs.Query;
import page.aaws.b01.dto.PageRequestDto;

public interface GetTodosByPageQuery extends Query {
    PageRequestDto getPageRequestDto();
}
