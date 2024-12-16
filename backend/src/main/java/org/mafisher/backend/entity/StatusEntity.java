package org.mafisher.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "STATUS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatusEntity {
    @Id
    @SequenceGenerator(name = "STATUS_SEQ", sequenceName = "STATUS_SEQ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STATUS_SEQ")
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToMany(mappedBy = "status")
    private List<TaskEntity> tasks;

    private String name;
}
