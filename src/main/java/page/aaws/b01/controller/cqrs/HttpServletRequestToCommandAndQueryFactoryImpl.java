package page.aaws.b01.controller.cqrs;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.apache.tomcat.util.json.TokenMgrError;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.HandlerMapping;
import page.aaws.b01.controller.cqrs.query.GetTodoQueryImpl;
import page.aaws.b01.controller.cqrs.query.GetTodosByPageQueryImpl;
import page.aaws.b01.cqrs.CommandAndQueryFactory;
import page.aaws.b01.controller.cqrs.command.*;
import page.aaws.b01.dto.PageRequestDto;
import page.aaws.b01.dto.TodoDto;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Component
public class HttpServletRequestToCommandAndQueryFactoryImpl implements CommandAndQueryFactory {
    @Override
    public <T> T create(Object input, Class<T> requiredType) throws NoSuchElementException {
        HttpServletRequest request = (HttpServletRequest) input;

        if (requiredType.equals(AddNewTodoCommandImpl.class)) {
            TodoDto todoDto;

            try {
                String body = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
                JSONParser jsonParser = new JSONParser(body);
                LinkedHashMap<String, Object> json = jsonParser.object();

                if (json.get("subject") == null) {
                    throw new NoSuchElementException("제목에 해당하는 자료가 없습니다.");
                } else if (json.get("description") == null) {
                    throw new NoSuchElementException("내용에 해당하는 자료가 없습니다.");
                }

                todoDto = TodoDto.builder()
                        .subject((String)json.get("subject"))
                        .description((String)json.get("description"))
                        .periodStartedAt(
                            json.get("periodStartedAt") == null ? null : LocalDateTime.parse((String)json.get("periodStartedAt"), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))
                        )
                        .periodEndedAt(
                            json.get("periodEndedAt") == null ? null : LocalDateTime.parse((String)json.get("periodEndedAt"), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))
                        )
                        .done((Boolean)json.get("done"))
                        .build();
            } catch (IOException | ParseException | TokenMgrError e) {
                throw new RuntimeException(e);
            }

            return (T) new AddNewTodoCommandImpl(todoDto);
        } else if (requiredType.equals(DeleteTodoCommandImpl.class)) {
            Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            return (T) new DeleteTodoCommandImpl(Long.valueOf(map.get("id")));
        } else if (requiredType.equals(GetTodoQueryImpl.class)) {
            Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            return (T) new GetTodoQueryImpl(Long.valueOf(map.get("id")));
        } else if (requiredType.equals(GetTodosByPageQueryImpl.class)) {
            return (T) new GetTodosByPageQueryImpl(
                    PageRequestDto.builder()
                            .number(request.getParameter("number") == null ? PageRequestDto.DEFAULT_NUMBER : Integer.valueOf(request.getParameter("number")))
                            .size(request.getParameter("size") == null ? PageRequestDto.DEFAULT_SIZE : Integer.valueOf(request.getParameter("size")))
                            .types(request.getParameterValues("types"))
                            .keyword(request.getParameter("keyword"))
                            .done(request.getParameter("done") == null ? null : Boolean.valueOf(request.getParameter("done")))
                            .periodStartedAt(request.getParameter("periodStartedAt") == null ? null : LocalDateTime.parse(request.getParameter("periodStartedAt"), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")))
                            .periodEndedAt(request.getParameter("periodEndedAt") == null ? null : LocalDateTime.parse(request.getParameter("periodEndedAt"), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")))
                            .build()
            );
        } else if (requiredType.equals(UpdateTodoCommandImpl.class)) {
            TodoDto todoDto;

            Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            TodoDto.TodoDtoBuilder builder = TodoDto.builder().id(Long.valueOf(map.get("id")));

            try {
                String body = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
                JSONParser jsonParser = new JSONParser(body);
                LinkedHashMap<String, Object> json = jsonParser.object();

                todoDto = builder.subject((String)json.get("subject"))
                        .description((String)json.get("description"))
                        .periodStartedAt(
                            json.get("periodStartedAt") == null ? null : LocalDateTime.parse((String)json.get("periodStartedAt"), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))
                        )
                        .periodEndedAt(
                            json.get("periodEndedAt") == null ? null : LocalDateTime.parse((String)json.get("periodEndedAt"), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))
                        )
                        .done((Boolean)json.get("done"))
                        .build();
            } catch (IOException | ParseException e) {
                throw new RuntimeException(e);
            }

            return (T) new UpdateTodoCommandImpl(todoDto);
        }
        return null;
    }
}
