package page.aaws.b01.controller.transformer;

import org.springframework.http.ResponseEntity;
import page.aaws.b01.dto.TodoDto;

public class UpdateTodoOkTransformerImpl extends OkTransformerImpl {
    private final TodoDto todoDto;

    public UpdateTodoOkTransformerImpl(TodoDto todoDto) {
        this.todoDto = todoDto;
    }

    @Override
    public ResponseEntity<?> process() {
        return ResponseEntity.ok(this.todoDto);
    }
}
