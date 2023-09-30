package page.aaws.b01.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.modelmapper.ModelMapper;

import org.springframework.stereotype.Service;

import page.aaws.b01.domain.TodoEntity;
import page.aaws.b01.dto.TodoDto;
import page.aaws.b01.repository.TodoRepository;

@Service
@Log4j2
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {
    private final ModelMapper modelMapper;

    private final TodoRepository todoRepository;

    @Override
    public Long addNewTodo(TodoDto dto) {
        return todoRepository.save(modelMapper.map(dto, TodoEntity.class)).getId();
    }

    @Override
    public TodoDto getTodo(Long id) {
        return this.modelMapper.map(todoRepository.findById(id).orElseThrow(), TodoDto.class);
    }

    @Override
    public void updateTodo(TodoDto dto) {
        TodoEntity todoEntity = todoRepository.findById(dto.getId()).orElseThrow();
        todoEntity.changeSubject(dto.getSubject());
        todoEntity.changeDescription(dto.getDescription());
        todoEntity.changePeriod(dto.getPeriodStartedAt(), dto.getPeriodEndedAt());
        todoEntity.hasDone(true);
        todoRepository.save(todoEntity);
    }
}
