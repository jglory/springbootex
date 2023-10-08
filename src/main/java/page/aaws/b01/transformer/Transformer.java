package page.aaws.b01.transformer;

import org.springframework.http.ResponseEntity;

public interface Transformer {
    ResponseEntity<?> process();
}
