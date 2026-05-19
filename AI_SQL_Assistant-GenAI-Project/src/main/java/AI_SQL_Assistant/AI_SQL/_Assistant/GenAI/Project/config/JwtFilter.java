package AI_SQL_Assistant.AI_SQL._Assistant.GenAI.Project.config;

import AI_SQL_Assistant.AI_SQL._Assistant.GenAI.Project.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired

    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader=request.getHeader("Authorization");

        if(authorizationHeader==null || !authorizationHeader.startsWith("Bearer ") ){
            // If it's missing, just move to the next filter and EXIT this method
            filterChain.doFilter(request,response);
            return;
        }
        // 2.header is Granted and not be null;

        try{
            String token=authorizationHeader.substring(7);
            if(jwtService.isValid(token)){
                String email= jwtService.extractUseremail(token);
                String role=jwtService.extractRole(token);

                var auth=new UsernamePasswordAuthenticationToken(
                        email,
                        null,
                        List.of(new SimpleGrantedAuthority(role))
                );

                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        catch(Exception ex){
            logger.error("Could not set user authentication", ex);
        }
        filterChain.doFilter(request,response);
    }
}
