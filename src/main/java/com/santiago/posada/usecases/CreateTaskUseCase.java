package com.santiago.posada.usecases;

import com.santiago.posada.adapters.RabbitMqEventPublisher;
import com.santiago.posada.repository.ToDoRepository;
import com.santiago.posada.repository.model.ToDo;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Component
public class CreateTaskUseCase implements Function<ToDo, Mono<ToDo>> {

    private final ToDoRepository toDoRepository;
    private final RabbitMqEventPublisher publisher;

    public CreateTaskUseCase(ToDoRepository toDoRepository, RabbitMqEventPublisher publisher) {
        this.toDoRepository = toDoRepository;
        this.publisher = publisher;
    }

    @Override
    public Mono<ToDo> apply(ToDo toDo) {
        return toDoRepository.save(toDo)
                .map(todo -> {
                    publisher.publishTaskCreated(todo).subscribe();
                    return todo;
                });
    }
}
