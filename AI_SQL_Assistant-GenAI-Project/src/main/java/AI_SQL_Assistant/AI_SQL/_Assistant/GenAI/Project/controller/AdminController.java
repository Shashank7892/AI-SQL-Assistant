package AI_SQL_Assistant.AI_SQL._Assistant.GenAI.Project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/logadmin")
    public ResponseEntity<String> adminloggedin(){
        return new ResponseEntity<String>("admin logged in", HttpStatus.OK);
    }

}
