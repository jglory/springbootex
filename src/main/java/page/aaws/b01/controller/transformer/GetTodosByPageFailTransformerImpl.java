package page.aaws.b01.controller.transformer;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class GetTodosByPageFailTransformerImpl extends GetTodosByPageFailTransformer {
    @Override
    public ResponseEntity<?> process(Object... data) {
        Exception exception = (Exception) data[0];
        HttpStatusCode httpStatusCode = (HttpStatusCode) data[1];

        Map<String, String> response = new HashMap<>();
        response.put("result", "fail");
        response.put("message", exception.getMessage());

        return new ResponseEntity<>(response, httpStatusCode);
    }
}
