package AI_SQL_Assistant.AI_SQL._Assistant.GenAI.Project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "queryhistory")
@Builder
public class QueryHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String prompt;

    @Column(columnDefinition = "TEXT")
    private String generatedSql;

    private String executionStatus;

    private LocalDateTime createdAt;


}
