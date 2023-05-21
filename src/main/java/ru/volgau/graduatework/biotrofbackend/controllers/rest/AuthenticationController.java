package ru.volgau.graduatework.biotrofbackend.controllers.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import ru.volgau.graduatework.biotrofbackend.domain.entity.Employer;
import ru.volgau.graduatework.biotrofbackend.domain.service.EmployerDaoService;
import ru.volgau.graduatework.biotrofbackend.mappers.EmployerMapper;
import ru.volgau.graduatework.biotrofbackend.model.request.ChangePasswordRequest;
import ru.volgau.graduatework.biotrofbackend.model.request.CreateEmailRequest;
import ru.volgau.graduatework.biotrofbackend.model.request.CreateEmployerRequest;
import ru.volgau.graduatework.biotrofbackend.model.request.LoginRequest;
import ru.volgau.graduatework.biotrofbackend.model.response.EmployerRegistrationResponse;
import ru.volgau.graduatework.biotrofbackend.model.response.GetUserInfoResponse;
import ru.volgau.graduatework.biotrofbackend.security.JwtTokenProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final EmployerDaoService employerDaoService;
    private final EmployerMapper employerMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/register")
    public EmployerRegistrationResponse registration(@Valid @RequestBody CreateEmployerRequest request) {
        log.info("request= " + request.toString());
        Employer employer = employerMapper.createEmployerRequestToEmployer(request);
        employer.setPassword(passwordEncoder.encode(request.getPassword()));
        employerDaoService.save(employer);
        return employerMapper.employerToRegistrationResponse(employer);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            log.info("request= " + request.toString());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            Employer employer = employerDaoService.findByUsername(request.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User does not exists"));
            String token = jwtTokenProvider.createToken(employer.getUsername(), employer.getRole().name());
            Map<Object, Object> response = new HashMap<>();
            response.put("username", employer.getUsername());
            response.put("token", token);
            response.put("role", employer.getRole());
            response.put("uuid", employer.getUuid());
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e){
            return new ResponseEntity<>("Invalid username/password combination", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/email/create")
    @PreAuthorize("hasAnyAuthority('EMPLOYER')")
    public void addEmail(@Valid @RequestBody CreateEmailRequest request) {
        log.info(request.toString());
        Employer employer = employerDaoService.getById(request.getUuid());
        employer.setEmail(request.getEmail());
        employerDaoService.save(employer);
    }

    @PostMapping("/change-password")
    @PreAuthorize("hasAnyAuthority('EMPLOYER')")
    public void changePassword(@Valid @RequestBody ChangePasswordRequest request) {
        Employer employer = employerDaoService.getById(request.getUuid());
        if(!passwordEncoder.matches(request.getOldPassword(), employer.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }
        if(!Objects.equals(request.getNewPassword(), request.getDuplicateNewPassword())) {
            throw new BadCredentialsException("New passwords not matches");
        }
        employer.setPassword(passwordEncoder.encode(request.getNewPassword()));
        employerDaoService.save(employer);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyAuthority('EMPLOYER')")
    public GetUserInfoResponse getRole(HttpServletRequest request) {
        Employer employer = employerDaoService.findByUsername(jwtTokenProvider.getUserName(jwtTokenProvider.resolveToken(request)))
                .orElseThrow(() -> new UsernameNotFoundException("User does not exists"));
        return employerMapper.employerToGetEmployerInfoResponse(employer);
    }
}
