package page.aaws.b01.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import page.aaws.b01.dto.PageRequestDto;
import page.aaws.b01.dto.TodoDto;
import page.aaws.b01.service.TodoService;

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
    ) throws BindException {
        if (bindingResult.hasErrors()) {
            Map<String, String> response = new HashMap<String, String>();
            response.put("result", "fail");
            response.put("message", "입력 정보가 잘못 되었습니다. "
            + bindingResult.getFieldErrors().get(0).getField()
                    + " - " + bindingResult.getFieldErrors().get(0).getCode());

            return new ResponseEntity<Map<String, String>>(response, HttpStatusCode.valueOf(422));
        }

        todoDto.setId(todoService.addNewTodo(todoDto));
        return ResponseEntity.ok(todoDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable("id") Long id) {
        try {
            this.todoService.deleteTodo(id);
        } catch (NoSuchElementException e) {
            Map<String, String> response = new HashMap<String, String>();
            response.put("result", "fail");
            response.put("message", "해당하는 일정 정보를 찾을 수 없습니다.");

            return new ResponseEntity<Map<String, String>>(response, HttpStatusCode.valueOf(404));
        }

        return (ResponseEntity<?>) ResponseEntity.ok();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getTodo(@PathVariable("id") Long id) {
        TodoDto todoDto;

        try {
            todoDto = this.todoService.getTodo(id);
        } catch (NoSuchElementException e) {
            Map<String, String> response = new HashMap<String, String>();
            response.put("result", "fail");
            response.put("message", "해당하는 일정 정보를 찾을 수 없습니다.");

            return new ResponseEntity<Map<String, String>>(response, HttpStatusCode.valueOf(404));
        }

        return (ResponseEntity<?>) ResponseEntity.ok(todoDto);
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
