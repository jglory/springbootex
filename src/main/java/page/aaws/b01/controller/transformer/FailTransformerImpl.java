package page.aaws.b01.controller.transformer;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.springframework.http.HttpStatusCode;
import page.aaws.b01.transformer.Transformer;

@AllArgsConstructor
@Getter
public abstract class FailTransformerImpl implements Transformer {
    private final HttpStatusCode httpStatusCode;
    private final Exception exception;
}
