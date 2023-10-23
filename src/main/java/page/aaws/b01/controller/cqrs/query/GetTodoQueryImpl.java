package page.aaws.b01.controller.cqrs.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GetTodoQueryImpl implements GetTodoQuery {
    private final Long id;
}
