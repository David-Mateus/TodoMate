package br.com.davidmateus.todolistrkt.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.davidmateus.todolistrkt.user.IUserRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {
    @Autowired
    private IUserRepository userRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       var servletPath = request.getServletPath();
       if(servletPath.equals("/tasks/")) {


           //pegar autenticação(user e senha)
           var autorizathion = request.getHeader("Authorization");

           var authEncode = autorizathion.substring("Basic".length()).trim();
           byte[] authDecode = Base64.getDecoder().decode(authEncode);
           var authString = new String(authDecode);
           System.out.println(authString);

           String[] credentials = authString.split(":");
           String username = credentials[0];
           String password = credentials[1];
           System.out.println(username);
           System.out.println(password);
           //validar usuario
           var user = this.userRepository.findByUsername(username);
           if (user == null) {
               response.sendError(401);
           } else {
               //validar senha
               var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
               if (passwordVerify.verified) {
                   request.setAttribute("idUser", user.getId());
                   filterChain.doFilter(request, response);
               } else {
                   response.sendError(401);
               }
               //finalizar

           }
       }else{
           filterChain.doFilter(request, response);
       }

    }
}
