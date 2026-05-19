package AI_SQL_Assistant.AI_SQL._Assistant.GenAI.Project.globalException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", "Invalid username or password!"));
    }

    @ExceptionHandler(InvalidSqlException.class)
    public ResponseEntity<?> handleInvalidSqlException(InvalidSqlException e){
        Map<String,Object> errorbody = new HashMap<>();

        errorbody.put("timestamp", LocalDateTime.now().toString());
        errorbody.put("status",HttpStatus.BAD_REQUEST.value());
        errorbody.put("error","Bad request");
        errorbody.put("message", e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorbody);
    }

    @ExceptionHandler(AIException.class)
    public ResponseEntity<?> handleAIException(AIException e){
        Map<String,Object> errorbody = new HashMap<>();

        errorbody.put("timestamp", LocalDateTime.now().toString());
        errorbody.put("status",HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorbody.put("error","AI COnnection Error");
        errorbody.put("message",e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorbody);
    }
}
