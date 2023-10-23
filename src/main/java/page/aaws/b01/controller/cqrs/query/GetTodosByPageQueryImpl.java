package page.aaws.b01.controller.cqrs.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import page.aaws.b01.dto.PageRequestDto;

@AllArgsConstructor
@Getter
public class GetTodosByPageQueryImpl implements GetTodosByPageQuery {
    private final PageRequestDto pageRequestDto;
}
