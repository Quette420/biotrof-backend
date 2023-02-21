package ru.volgau.graduatework.biotrofbackend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.volgau.graduatework.biotrofbackend.domain.entity.Employer;
import ru.volgau.graduatework.biotrofbackend.domain.repository.EmployerRepository;

@Service("userDetailsServiceImpl")
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final EmployerRepository employerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employer employer = employerRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User does not exists"));
        return SecurityUser.fromEmployer(employer);
    }
}
