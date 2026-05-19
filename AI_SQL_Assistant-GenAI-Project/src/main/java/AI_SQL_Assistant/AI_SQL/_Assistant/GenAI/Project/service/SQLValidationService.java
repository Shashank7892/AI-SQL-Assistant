package AI_SQL_Assistant.AI_SQL._Assistant.GenAI.Project.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SQLValidationService {
    private static final List<String> BLOCKED_KEYWORDS = List.of(
            "DROP", "DELETE", "TRUNCATE", "UPDATE", "INSERT", "ALTER"
    );

    public boolean isSafe(String sql){
        if (sql == null || sql.isBlank()) {
            return false;
        }

        // Clean leading/trailing spaces and make uppercase
        String uppersql = sql.trim().toUpperCase();

        if(!uppersql.startsWith("SELECT")){
            return false;
        }
        return BLOCKED_KEYWORDS.stream().noneMatch(uppersql::contains);
    }

}
