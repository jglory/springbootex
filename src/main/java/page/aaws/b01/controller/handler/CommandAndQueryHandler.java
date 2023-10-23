package page.aaws.b01.controller.handler;

import page.aaws.b01.cqrs.CommandAndQuery;

public interface CommandAndQueryHandler<CQ extends CommandAndQuery, T> {
    T process(CQ commandOrQuery);
}
