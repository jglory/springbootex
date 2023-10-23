package page.aaws.b01.controller.handler;

import page.aaws.b01.controller.cqrs.command.DeleteTodoCommand;

public interface DeleteTodoCommandHandler extends CommandAndQueryHandler<DeleteTodoCommand, Long> {
}
