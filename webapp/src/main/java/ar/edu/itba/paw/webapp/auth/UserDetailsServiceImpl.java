package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UserService us;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    final User user = us.findByName(username);

    if (user == null) {
      throw new UsernameNotFoundException("No Username by the name " + username);
    }
    final Collection<? extends GrantedAuthority> authorities = Arrays.asList(
      new SimpleGrantedAuthority("ROLE_USER")
    );

    return new org.springframework.security.core.userdetails.User(username, user.getPassword(), authorities);
  }
}
