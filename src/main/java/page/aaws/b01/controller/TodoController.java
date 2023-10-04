package page.aaws.b01.controller;

import java.util.HashMap;
import java.util.Map;
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

import page.aaws.b01.controller.transformer.AddNewTodoFailTransformerImpl;
import page.aaws.b01.controller.transformer.AddNewTodoOkTransformerImpl;
import page.aaws.b01.controller.transformer.DeleteTodoFailTransformerImpl;
import page.aaws.b01.controller.transformer.DeleteTodoOkTransformerImpl;
import page.aaws.b01.controller.transformer.GetTodoFailTransformerImpl;
import page.aaws.b01.controller.transformer.GetTodoOkTransformerImpl;

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
                new AddNewTodoFailTransformerImpl(
                    HttpStatusCode.valueOf(422),
                    new Exception(bindingResult.getFieldErrors().get(0).getField() + " - " + bindingResult.getFieldErrors().get(0).getCode())
                )
            ).process();
        }

        todoDto.setId(todoService.addNewTodo(todoDto));
        return (new AddNewTodoOkTransformerImpl(todoDto)).process();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable("id") Long id) {
        try {
            this.todoService.deleteTodo(id);
        } catch (NoSuchElementException e) {
            return (new DeleteTodoFailTransformerImpl(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()), e).process());
        }

        return (new DeleteTodoOkTransformerImpl()).process();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getTodo(@PathVariable("id") Long id) {
        TodoDto todoDto;

        try {
            todoDto = this.todoService.getTodo(id);
        } catch (NoSuchElementException e) {
            return (new GetTodoFailTransformerImpl(HttpStatusCode.valueOf(404), e)).process();
        }

        return (new GetTodoOkTransformerImpl(todoDto)).process();
    }

    @GetMapping(value = "/")
    public ResponseEntity<?> getTodosByPage(@Valid PageRequestDto pageRequestDto) {
        return (ResponseEntity<?>) ResponseEntity.ok(this.todoService.getTodosByPage(pageRequestDto));
    }

    @GetMapping(value = "")

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateTodo(
            @PathVariable("id") Long id,
            @Valid @RequestBody TodoDto todoDto) {

        todoDto.setId(id);
        try {
            this.todoService.updateTodo(todoDto);
        } catch (NoSuchElementException e) {
            Map<String, String> response = new HashMap<String, String>();
            response.put("result", "fail");
            response.put("message", "해당하는 일정 정보를 찾을 수 없습니다.");

            return new ResponseEntity<Map<String, String>>(response, HttpStatusCode.valueOf(404));
        }

        return ResponseEntity.ok(todoDto);
    }
}
