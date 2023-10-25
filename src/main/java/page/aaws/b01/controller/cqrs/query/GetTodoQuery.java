package page.aaws.b01.controller.cqrs.query;

import page.aaws.b01.cqrs.Query;

public interface GetTodoQuery extends Query {
    Long getId();
}
