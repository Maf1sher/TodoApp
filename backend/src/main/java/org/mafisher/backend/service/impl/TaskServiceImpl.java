package org.mafisher.backend.service.impl;

import lombok.AllArgsConstructor;
import org.mafisher.backend.dto.request.CreateTaskRequest;
import org.mafisher.backend.dto.response.Task;
import org.mafisher.backend.entity.CategoryEntity;
import org.mafisher.backend.entity.StatusEntity;
import org.mafisher.backend.entity.TaskEntity;
import org.mafisher.backend.entity.UserEntity;
import org.mafisher.backend.handler.BusinessErrorCodes;
import org.mafisher.backend.handler.CustomException;
import org.mafisher.backend.mapper.Mapper;
import org.mafisher.backend.repository.CategoryRepository;
import org.mafisher.backend.repository.StatusRepository;
import org.mafisher.backend.repository.TaskRepository;
import org.mafisher.backend.repository.UserRepository;
import org.mafisher.backend.service.TaskService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final Mapper<TaskEntity, Task> mapper;
    private final StatusRepository statusRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<Task> getAll(Principal principal) {
        UserEntity userEntity = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<TaskEntity> taskEntityList = taskRepository.findByUserId(userEntity.getId());
        return taskEntityList.stream().map(mapper::mapTo).collect(Collectors.toList());
    }

    @Override
    public Task getById(long id, Principal principal) {
        UserEntity userEntity = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        TaskEntity task = taskRepository.findById(id)
                .orElseThrow(() -> new CustomException(BusinessErrorCodes.TASK_NOT_FOUND));

        if(!task.getUser().getId().equals(userEntity.getId())) {
            throw new CustomException(BusinessErrorCodes.BAD_CREDENTIALS);
        }

        return mapper.mapTo(task);
    }

    @Override
    public Task create(CreateTaskRequest task, Principal principal) {
        UserEntity userEntity = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        StatusEntity statusEntity = statusRepository.findByName(task.getStatus())
                .orElseThrow(() -> new CustomException(BusinessErrorCodes.INVALID_TASK_STATUS));

        CategoryEntity categoryEntity = categoryRepository.findById(task.getCategory_id())
                .orElseThrow(() -> new CustomException(BusinessErrorCodes.CATEGORY_NOT_FOUND));

        if(!categoryEntity.getUser().getId().equals(userEntity.getId())) {
            throw new CustomException(BusinessErrorCodes.BAD_CREDENTIALS);
        }

        TaskEntity taskEntity = TaskEntity.builder()
                .title(task.getTitle())
                .description(task.getDescription())
                .status(statusEntity)
                .user(userEntity)
                .category(categoryEntity)
                .build();

        TaskEntity savedTask = taskRepository.save(taskEntity);
        return mapper.mapTo(savedTask);
    }

    @Override
    public Task edit(long id, CreateTaskRequest task, Principal principal) {
        UserEntity userEntity = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        TaskEntity taskEntity = taskRepository.findById(id)
                .orElseThrow(() -> new CustomException(BusinessErrorCodes.TASK_NOT_FOUND));

        if(!taskEntity.getUser().getId().equals(userEntity.getId())) {
            throw new CustomException(BusinessErrorCodes.BAD_CREDENTIALS);
        }

        StatusEntity statusEntity = statusRepository.findByName(task.getStatus())
                .orElseThrow(() -> new CustomException(BusinessErrorCodes.INVALID_TASK_STATUS));

        taskEntity.setTitle(task.getTitle());
        taskEntity.setDescription(task.getDescription());
        taskEntity.setStatus(statusEntity);

        TaskEntity updatedTask = taskRepository.save(taskEntity);

        return mapper.mapTo(updatedTask);
    }

    @Override
    public void delete(long id, Principal principal) {
        UserEntity userEntity = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        TaskEntity taskEntity = taskRepository.findById(id)
                .orElseThrow(() -> new CustomException(BusinessErrorCodes.TASK_NOT_FOUND));

        if(!taskEntity.getUser().getId().equals(userEntity.getId())) {
            throw new CustomException(BusinessErrorCodes.BAD_CREDENTIALS);
        }

        taskRepository.delete(taskEntity);
    }

    @Override
    public Task changeCategory(long taskId, long newCategoryId, Principal principal) {
        UserEntity userEntity = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        TaskEntity taskEntity = taskRepository.findById(taskId)
                .orElseThrow(() -> new CustomException(BusinessErrorCodes.TASK_NOT_FOUND));

        if(!taskEntity.getUser().getId().equals(userEntity.getId())) {
            throw new CustomException(BusinessErrorCodes.BAD_CREDENTIALS);
        }

        CategoryEntity categoryEntity = categoryRepository.findById(newCategoryId)
                .orElseThrow(() -> new CustomException(BusinessErrorCodes.CATEGORY_NOT_FOUND));

        taskEntity.setCategory(categoryEntity);

        TaskEntity savedTask = taskRepository.save(taskEntity);

        return mapper.mapTo(savedTask);
    }


}
