package page.aaws.b01.controller.transformer;

import org.springframework.http.ResponseEntity;

import page.aaws.b01.dto.TodoDto;

public class GetTodoOkTransformer extends OkTransformer {
    private final TodoDto todoDto;

    public GetTodoOkTransformer(TodoDto todoDto) {
        this.todoDto = todoDto;
    }

    @Override
    public ResponseEntity<?> process() {
        return (ResponseEntity<?>) ResponseEntity.ok(this.todoDto);
    }
}
