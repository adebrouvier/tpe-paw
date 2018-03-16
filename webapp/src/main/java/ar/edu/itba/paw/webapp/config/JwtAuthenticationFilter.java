package ar.edu.itba.paw.webapp.config;

import ar.edu.itba.paw.webapp.controller.dto.AuthenticationDTO;
import ar.edu.itba.paw.webapp.controller.dto.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;


public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
  private AuthenticationManager authenticationManager;

  static final String SECRET = "puOPzfYy4SPhrT1DZFdJHR5y9wDI0dDnHt9tdutB8iTc3n0FpLhUxKxYWbHdVaJ8uc0ja2ttcy5uqGtEAb0paKK6svBfp9oCqrAGzy0FAciF0zzQgxM2r0iVy86cismf";
  private static final long EXPIRATION_TIME = 864_000_000; // 10 days
  static final String TOKEN_PREFIX = "Bearer ";
  static final String HEADER_STRING = "Authorization";

  public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest req,
                                              HttpServletResponse res) throws AuthenticationException {
    try {
      AuthenticationDTO creds = new ObjectMapper()
        .readValue(req.getInputStream(), AuthenticationDTO.class);

      return authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
          creds.getUsername(),
          creds.getPassword(),
          new ArrayList<>())
      );
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest req,
                                          HttpServletResponse res,
                                          FilterChain chain,
                                          Authentication auth) throws IOException, ServletException {

    String token = Jwts.builder()
      .setSubject(((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername())
      .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
      .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
      .compact();
    res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
    res.addHeader("Access-Control-Expose-Headers", "Authorization");
  }
}
