package AI_SQL_Assistant.AI_SQL._Assistant.GenAI.Project.service;

import AI_SQL_Assistant.AI_SQL._Assistant.GenAI.Project.globalException.AIException;
import AI_SQL_Assistant.AI_SQL._Assistant.GenAI.Project.globalException.InvalidSqlException;
import AI_SQL_Assistant.AI_SQL._Assistant.GenAI.Project.helpers.QueryResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class AIQueryService {

    @Autowired
    private OpenAIService openAIService;

    @Autowired
    private QueryExecutionService queryExecutionService;

    @Autowired
    private SQLValidationService sqlValidationService;

    public QueryResponseDTO processprompt(String prompt){
        String sql = openAIService.generateSql(prompt);

        // Fail early if the API returned a rate limit or connection error string
        if (sql == null || sql.isBlank() || sql.startsWith("AI Service") || sql.startsWith("Error")) {
            throw new AIException("AI Service or SQL Error");
        }

        if (!sqlValidationService.isSafe(sql)){
            throw new InvalidSqlException("Not allowed to execute DML commands"+" "+prompt);
        }

//        List<Map<String,Object>> result = queryExecutionService.executeQuery(sql);

        return QueryResponseDTO.builder()
                .generatedSql(sql)
                .data("executed")
                .summary("Query Executed Successfully")
                .build();
    }
}