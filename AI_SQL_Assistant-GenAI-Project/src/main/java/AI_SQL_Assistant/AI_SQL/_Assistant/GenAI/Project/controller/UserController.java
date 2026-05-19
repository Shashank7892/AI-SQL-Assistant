package AI_SQL_Assistant.AI_SQL._Assistant.GenAI.Project.controller;

import AI_SQL_Assistant.AI_SQL._Assistant.GenAI.Project.helpers.PromptRequestDTO;
import AI_SQL_Assistant.AI_SQL._Assistant.GenAI.Project.helpers.QueryResponseDTO;
import AI_SQL_Assistant.AI_SQL._Assistant.GenAI.Project.service.AIQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private AIQueryService aiQueryService;

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/userloggin")
    public ResponseEntity<String> userloggedin(){
        return new ResponseEntity<String>("User/admin logged in", HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PostMapping("/generatesql")
    public ResponseEntity<QueryResponseDTO> askQuestion(@RequestBody PromptRequestDTO requestDTO){
        QueryResponseDTO response=aiQueryService.processprompt(requestDTO.getPrompt());
        return new ResponseEntity<QueryResponseDTO>(response, HttpStatus.OK);
    }
}
