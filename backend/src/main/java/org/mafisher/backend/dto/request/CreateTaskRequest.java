package org.mafisher.backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateTaskRequest {
    private String title;
    private String description;
    private String status;
    private long category_id;
}
