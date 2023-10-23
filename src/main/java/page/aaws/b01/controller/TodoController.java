package page.aaws.b01.controller;

import java.util.NoSuchElementException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import page.aaws.b01.controller.cqrs.command.*;
import page.aaws.b01.controller.cqrs.query.GetTodoQueryImpl;
import page.aaws.b01.controller.handler.*;
import page.aaws.b01.controller.transformer.*;
import page.aaws.b01.cqrs.CommandAndQueryFactory;
import page.aaws.b01.dto.PageRequestDto;
import page.aaws.b01.dto.TodoDto;
import page.aaws.b01.service.TodoService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todo")
@Log4j2
public class TodoController {
    private final ApplicationContext applicationContext;

    private final CommandAndQueryFactory commandAndQueryFactory;
    private final TodoService todoService;

    private final AddNewTodoOkTransformer addNewTodoOkTransformer;
    private final AddNewTodoFailTransformer addNewTodoFailTransformer;
    private final AddNewTodoCommandHandler addNewTodoCommandHandler;

    private final DeleteTodoOkTransformer deleteTodoOkTransformer;
    private final DeleteTodoFailTransformer deleteTodoFailTransformer;
    private final DeleteTodoCommandHandler deleteTodoCommandHandler;

    private final GetTodoOkTransformer getTodoOkTransformer;
    private final GetTodoFailTransformer getTodoFailTransformer;
    private final GetTodoQueryHandler getTodoQueryHandler;

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addNewTodo(
            HttpServletRequest request,
            @Valid @RequestBody TodoDto todoDto,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return this.addNewTodoFailTransformer
                    .process(
                            HttpStatusCode.valueOf(HttpStatus.UNPROCESSABLE_ENTITY.value()),
                            new Exception(
                                    bindingResult.getFieldErrors().get(0).getField()
                                            + " - "
                                            + bindingResult.getFieldErrors().get(0).getCode()
                            )
                    );
        }

        this.addNewTodoCommandHandler.process(
                this.commandAndQueryFactory
                        .create(request, AddNewTodoCommandImpl.class)
        );

        return this.addNewTodoOkTransformer.process(todoDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteTodo(HttpServletRequest request) {
        try {
            this.deleteTodoCommandHandler.process(
                    this.commandAndQueryFactory
                            .create(request, DeleteTodoCommandImpl.class)
            );
        } catch (NoSuchElementException exception) {
            return this.deleteTodoFailTransformer
                    .process(exception, HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
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
        } catch (NoSuchElementException exception) {
            return this.getTodoFailTransformer
                    .process(exception, HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
        }

        return this.getTodoOkTransformer.process(todoDto);
    }

    @GetMapping(value = "/")
    public ResponseEntity<?> getTodosByPage(@Valid PageRequestDto pageRequestDto) {
        return this.applicationContext
                .getBean("getTodosByPageOkTransformer", GetTodosByPageOkTransformer.class)
                .process(this.todoService.getTodosByPage(pageRequestDto));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateTodo(
            @PathVariable("id") Long id,
            @Valid @RequestBody TodoDto todoDto) {
        todoDto.setId(id);
        try {
            this.todoService.updateTodo(todoDto);
        } catch (NoSuchElementException exception) {
            return this.applicationContext
                    .getBean("updateTodoFailTransformer", UpdateTodoFailTransformer.class)
                    .process(exception, HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
        }

        return this.applicationContext
                .getBean("updateTodoOkTransformer", UpdateTodoOkTransformer.class)
                .process(todoDto);
    }
}
