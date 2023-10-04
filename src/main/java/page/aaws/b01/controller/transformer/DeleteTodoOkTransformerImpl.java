package page.aaws.b01.controller.transformer;

import org.springframework.http.ResponseEntity;

public class DeleteTodoOkTransformerImpl extends OkTransformerImpl {
    @Override
    public ResponseEntity<?> process() {
        return ResponseEntity.ok().build();
    }
}
