package org.mafisher.backend.service;

import org.mafisher.backend.dto.request.CreateTaskRequest;
import org.mafisher.backend.dto.response.Task;

import java.security.Principal;
import java.util.List;

public interface TaskService {

    List<Task> getAll(Principal principal);

    Task getById(long id, Principal principal);

    Task create(CreateTaskRequest task, Principal principal);

    Task edit(long id, CreateTaskRequest task, Principal principal);

    void delete(long id, Principal principal);

    Task changeCategory(long taskId, long newCategoryId, Principal principal);
}
