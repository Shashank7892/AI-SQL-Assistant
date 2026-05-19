package AI_SQL_Assistant.AI_SQL._Assistant.GenAI.Project.service;

import AI_SQL_Assistant.AI_SQL._Assistant.GenAI.Project.helpers.LoginRequestDTO;
import AI_SQL_Assistant.AI_SQL._Assistant.GenAI.Project.helpers.LoginResponseDTO;
import AI_SQL_Assistant.AI_SQL._Assistant.GenAI.Project.helpers.UserRegisterDTO;
import AI_SQL_Assistant.AI_SQL._Assistant.GenAI.Project.model.Role;
import AI_SQL_Assistant.AI_SQL._Assistant.GenAI.Project.model.UserEntity;
import AI_SQL_Assistant.AI_SQL._Assistant.GenAI.Project.repository.UserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class UserServices {

    @Autowired
    private UserRespository userRespository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    public String registerNormaluser(UserRegisterDTO userdetails) {
        UserEntity userEntity= UserEntity.builder()
                .email(userdetails.getEmail())
                .password(passwordEncoder.encode(userdetails.getPassword()))
                .role(Role.valueOf("USER"))
                .createdAt(LocalDateTime.now())
                .build();
        userRespository.save(userEntity);
        return "User Registered Successfully";
    }

    public String registerAdmin(UserRegisterDTO userdetailss) {
        UserEntity userEntity= UserEntity.builder()
                .email(userdetailss.getEmail())
                .password(passwordEncoder.encode(userdetailss.getPassword()))
                .role(Role.valueOf("ADMIN"))
                .createdAt(LocalDateTime.now())
                .build();
        userRespository.save(userEntity);
        return "ADMIN Registered Successfully";
    }

    public LoginResponseDTO loggin(LoginRequestDTO requestDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestDTO.getEmail(), requestDTO.getPassword()));
            User user = (User) authentication.getPrincipal();

            String userrole = user.getAuthorities().iterator().next().toString(); //ROLE_USER/ROLE_ADMIN

            String generateToken = jwtService.generateToken(user.getUsername(), userrole);

            return LoginResponseDTO.builder().
                    token(generateToken)
                    .role(userrole)
                    .build();
        }
        catch (AuthenticationException ex) {

            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Invalid username or password"
            );
        }
    }
}
