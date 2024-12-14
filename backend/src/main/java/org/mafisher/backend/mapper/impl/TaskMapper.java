package org.mafisher.backend.mapper.impl;

import lombok.AllArgsConstructor;
import org.mafisher.backend.dto.response.Task;
import org.mafisher.backend.entity.TaskEntity;
import org.mafisher.backend.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TaskMapper implements Mapper<TaskEntity, Task> {

    private final ModelMapper modelMapper;

    @Override
    public Task mapTo(TaskEntity taskEntity) {
        return modelMapper.map(taskEntity, Task.class);
    }

    @Override
    public TaskEntity mapFrom(Task task) {
        return modelMapper.map(task, TaskEntity.class);
    }
}
