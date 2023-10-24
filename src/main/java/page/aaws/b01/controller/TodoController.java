package page.aaws.b01.controller;

import java.util.NoSuchElementException;

import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import page.aaws.b01.controller.cqrs.command.*;
import page.aaws.b01.controller.cqrs.query.GetTodoQueryImpl;
import page.aaws.b01.controller.cqrs.query.GetTodosByPageQueryImpl;
import page.aaws.b01.controller.handler.*;
import page.aaws.b01.controller.transformer.*;
import page.aaws.b01.cqrs.CommandAndQueryCreationException;
import page.aaws.b01.cqrs.CommandAndQueryFactory;
import page.aaws.b01.dto.TodoDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todo")
@Log4j2
public class TodoController {
    private static final String INTERNAL_SERVER_ERROR = "서버 오류가 발생하였습니다.";
    private static final String NOT_FOUND = "요청에 해당하는 자료를 찾을 수 없습니다.";
    private static final String UNPROCESSABLE_ENTITY = "요청이 정상적인 형식에 맞지 않습니다.";

    private final CommandAndQueryFactory commandAndQueryFactory;

    private final AddNewTodoOkTransformer addNewTodoOkTransformer;
    private final AddNewTodoFailTransformer addNewTodoFailTransformer;
    private final AddNewTodoCommandHandler addNewTodoCommandHandler;

    private final DeleteTodoOkTransformer deleteTodoOkTransformer;
    private final DeleteTodoFailTransformer deleteTodoFailTransformer;
    private final DeleteTodoCommandHandler deleteTodoCommandHandler;

    private final GetTodoOkTransformer getTodoOkTransformer;
    private final GetTodoFailTransformer getTodoFailTransformer;
    private final GetTodoQueryHandler getTodoQueryHandler;

    private final GetTodosByPageOkTransformer getTodosByPageOkTransformer;
    private final GetTodosByPageFailTransformer getTodosByPageFailTransformer;
    private final GetTodosByPageQueryHandler getTodosByPageQueryHandler;

    private final UpdateTodoOkTransformer updateTodoOkTransformer;
    private final UpdateTodoFailTransformer updateTodoFailTransformer;
    private final UpdateTodoCommandHandler updateTodoCommandHandler;

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addNewTodo(HttpServletRequest request) {
        TodoDto todoDto;

        try {
            todoDto = this.addNewTodoCommandHandler.process(
                    this.commandAndQueryFactory
                            .create(request, AddNewTodoCommandImpl.class)
            );
        } catch (CommandAndQueryCreationException exception) {
            return this.addNewTodoFailTransformer
                    .process(new Exception(INTERNAL_SERVER_ERROR), HttpStatusCode.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        } catch (RuntimeException exception) {
            return this.addNewTodoFailTransformer
                    .process(new Exception(UNPROCESSABLE_ENTITY), HttpStatusCode.valueOf(HttpStatus.UNPROCESSABLE_ENTITY.value()));
        }

        return this.addNewTodoOkTransformer.process(todoDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteTodo(HttpServletRequest request) {
        try {
            this.deleteTodoCommandHandler.process(
                    this.commandAndQueryFactory
                            .create(request, DeleteTodoCommandImpl.class)
            );
        } catch (CommandAndQueryCreationException exception) {
            return this.deleteTodoFailTransformer
                    .process(new Exception(INTERNAL_SERVER_ERROR), HttpStatusCode.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        } catch (NoSuchElementException exception) {
            return this.deleteTodoFailTransformer
                    .process(new Exception(NOT_FOUND), HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
        }

        return this.deleteTodoOkTransformer.process();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getTodo(HttpServletRequest request) {
        TodoDto todoDto;

        try {
            todoDto = this.getTodoQueryHandler.process(
                    this.commandAndQueryFactory
                            .create(request, GetTodoQueryImpl.class)
            );
        } catch (CommandAndQueryCreationException exception) {
            return this.getTodoFailTransformer
                    .process(new Exception(INTERNAL_SERVER_ERROR), HttpStatusCode.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        } catch (NoSuchElementException exception) {
            return this.getTodoFailTransformer
                    .process(new Exception(NOT_FOUND), HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
        }

        return this.getTodoOkTransformer.process(todoDto);
    }

    @GetMapping(value = "/")
    public ResponseEntity<?> getTodosByPage(HttpServletRequest request) {
        try {
            return this.getTodosByPageOkTransformer
                    .process(
                            this.getTodosByPageQueryHandler
                                    .process(
                                            this.commandAndQueryFactory
                                                    .create(request, GetTodosByPageQueryImpl.class)
                                    )
                    );
        } catch (CommandAndQueryCreationException exception) {
            return this.getTodosByPageFailTransformer.process(new Exception(INTERNAL_SERVER_ERROR), HttpStatusCode.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateTodo(HttpServletRequest request) {
        TodoDto todoDto;

        try {
            todoDto = this.updateTodoCommandHandler.process(
                    this.commandAndQueryFactory
                            .create(request, UpdateTodoCommandImpl.class)
            );
        } catch (CommandAndQueryCreationException exception) {
            return this.updateTodoFailTransformer
                    .process(new Exception(INTERNAL_SERVER_ERROR), HttpStatusCode.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        } catch (NoSuchElementException exception) {
            return this.updateTodoFailTransformer
                    .process(new Exception(NOT_FOUND), HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
        }

        return this.updateTodoOkTransformer.process(todoDto);
    }
}
