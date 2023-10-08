package page.aaws.b01.controller;

import java.util.NoSuchElementException;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import page.aaws.b01.dto.PageRequestDto;
import page.aaws.b01.dto.TodoDto;
import page.aaws.b01.service.TodoService;

import page.aaws.b01.controller.transformer.AddNewTodoFailTransformer;
import page.aaws.b01.controller.transformer.AddNewTodoOkTransformer;
import page.aaws.b01.controller.transformer.DeleteTodoFailTransformer;
import page.aaws.b01.controller.transformer.DeleteTodoOkTransformer;
import page.aaws.b01.controller.transformer.GetTodoFailTransformer;
import page.aaws.b01.controller.transformer.GetTodoOkTransformer;
import page.aaws.b01.controller.transformer.GetTodosByPageOkTransformer;
import page.aaws.b01.controller.transformer.UpdateTodoFailTransformer;
import page.aaws.b01.controller.transformer.UpdateTodoOkTransformer;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todo")
@Log4j2
public class TodoController {
    private final TodoService todoService;

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addNewTodo(
            @Valid @RequestBody TodoDto todoDto,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return (
                new AddNewTodoFailTransformer(
                    HttpStatusCode.valueOf(HttpStatus.UNPROCESSABLE_ENTITY.value()),
                    new Exception(bindingResult.getFieldErrors().get(0).getField() + " - " + bindingResult.getFieldErrors().get(0).getCode())
                )
            ).process();
        }

        todoDto.setId(todoService.addNewTodo(todoDto));
        return (new AddNewTodoOkTransformer(todoDto)).process();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable("id") Long id) {
        try {
            this.todoService.deleteTodo(id);
        } catch (NoSuchElementException e) {
            return (new DeleteTodoFailTransformer(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()), e).process());
        }

        return (new DeleteTodoOkTransformer()).process();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getTodo(@PathVariable("id") Long id) {
        TodoDto todoDto;

        try {
            todoDto = this.todoService.getTodo(id);
        } catch (NoSuchElementException e) {
            return (new GetTodoFailTransformer(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()), e)).process();
        }

        return (new GetTodoOkTransformer(todoDto)).process();
    }

    @GetMapping(value = "/")
    public ResponseEntity<?> getTodosByPage(@Valid PageRequestDto pageRequestDto) {
        return (new GetTodosByPageOkTransformer(this.todoService.getTodosByPage(pageRequestDto))).process();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateTodo(
            @PathVariable("id") Long id,
            @Valid @RequestBody TodoDto todoDto) {
        todoDto.setId(id);
        try {
            this.todoService.updateTodo(todoDto);
        } catch (NoSuchElementException e) {
            return (new UpdateTodoFailTransformer(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()), e)).process();
        }

        return (new UpdateTodoOkTransformer(todoDto)).process();
    }
}
