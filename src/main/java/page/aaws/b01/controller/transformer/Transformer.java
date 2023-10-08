package page.aaws.b01.controller.transformer;

import org.springframework.http.ResponseEntity;

public interface Transformer {
    ResponseEntity<?> process();
}
