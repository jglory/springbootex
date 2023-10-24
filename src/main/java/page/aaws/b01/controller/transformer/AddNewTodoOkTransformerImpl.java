package page.aaws.b01.controller.transformer;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import page.aaws.b01.dto.TodoDto;

@Component
@Lazy
public class AddNewTodoOkTransformerImpl extends AddNewTodoOkTransformer {
    @Override
    public ResponseEntity<?> process(Object... data) {
        return ResponseEntity.ok((TodoDto) data[0]);
    }
}
