package AI_SQL_Assistant.AI_SQL._Assistant.GenAI.Project.controller;

import AI_SQL_Assistant.AI_SQL._Assistant.GenAI.Project.helpers.LoginRequestDTO;
import AI_SQL_Assistant.AI_SQL._Assistant.GenAI.Project.helpers.LoginResponseDTO;
import AI_SQL_Assistant.AI_SQL._Assistant.GenAI.Project.helpers.UserRegisterDTO;
import AI_SQL_Assistant.AI_SQL._Assistant.GenAI.Project.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

//    private final UserServices userServices;
//
//    public UserController(UserServices userServices) {
//        this.userServices = userServices;
//    }   constructor injection

    @Autowired
    private UserServices userServices;

    @PostMapping("/registeruser")
    public ResponseEntity<String> RegisterNormalUser(@RequestBody UserRegisterDTO userdetails) {
        String response = userServices.registerNormaluser(userdetails);
        return new  ResponseEntity<String>(response, HttpStatus.CREATED);
    }

    @PostMapping("/admin-register")
    public ResponseEntity<String> RegisterAdminUser(@RequestBody UserRegisterDTO userdetailss) {
        String response = userServices.registerAdmin(userdetailss);
        return new ResponseEntity<String>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO requestDTO){
        LoginResponseDTO response=userServices.loggin(requestDTO);
        return new ResponseEntity<LoginResponseDTO>(response, HttpStatus.OK);
    }

}
