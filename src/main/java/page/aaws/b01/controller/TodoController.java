package page.aaws.b01.controller;

import java.util.NoSuchElementException;

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

import page.aaws.b01.controller.transformer.*;
import page.aaws.b01.dto.PageRequestDto;
import page.aaws.b01.dto.TodoDto;
import page.aaws.b01.service.TodoService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todo")
@Log4j2
public class TodoController {
    private final ApplicationContext applicationContext;
    private final TodoService todoService;

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addNewTodo(
            @Valid @RequestBody TodoDto todoDto,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return this.applicationContext
                    .getBean("addNewTodoFailTransformer", AddNewTodoFailTransformer.class)
                    .process(
                            HttpStatusCode.valueOf(HttpStatus.UNPROCESSABLE_ENTITY.value()),
                            new Exception(bindingResult.getFieldErrors().get(0).getField() + " - " + bindingResult.getFieldErrors().get(0).getCode())
                    );
        }

        todoDto.setId(todoService.addNewTodo(todoDto));
        return this.applicationContext
                .getBean("addNewTodoOkTransformer", AddNewTodoOkTransformer.class)
                .process(todoDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable("id") Long id) {
        try {
            this.todoService.deleteTodo(id);
        } catch (NoSuchElementException exception) {
            return (new DeleteTodoFailTransformerImpl().process(exception, HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value())));
        }

        return (new DeleteTodoOkTransformerImpl()).process();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getTodo(@PathVariable("id") Long id) {
        TodoDto todoDto;

        try {
            todoDto = this.todoService.getTodo(id);
        } catch (NoSuchElementException exception) {
            return (new GetTodoFailTransformerImpl()).process(exception, HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
        }

        return (new GetTodoOkTransformerImpl()).process(todoDto);
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
            return (new UpdateTodoFailTransformerImpl()).process(exception, HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()));
        }

        return (new UpdateTodoOkTransformerImpl()).process(todoDto);
    }
}
