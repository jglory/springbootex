package page.aaws.b01.controller.cqrs.query;

import page.aaws.b01.cqrs.Command;

public interface GetTodoQuery extends Command {
    Long getId();
}
