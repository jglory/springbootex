package page.aaws.b01.controller.cqrs.command;

import page.aaws.b01.cqrs.Command;

public interface DeleteTodoCommand extends Command {
    Long getId();
}
