package ru.volgau.graduatework.biotrofbackend.domain.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.volgau.graduatework.biotrofbackend.dictionary.Role;
import ru.volgau.graduatework.biotrofbackend.domain.entity.Employer;
import ru.volgau.graduatework.biotrofbackend.domain.repository.EmployerRepository;
import ru.volgau.graduatework.biotrofbackend.domain.service.EmployerDaoService;
import ru.volgau.graduatework.biotrofbackend.exceptions.UserAlreadyExistsException;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployerDaoServiceImpl implements EmployerDaoService {

    private final EmployerRepository repository;

    @Override
    public Employer getById(String uuid) {
        return repository.getById(uuid);
    }

    @Override
    public List<Employer> getAll() {
        return repository.findAll();
    }

    @Override
    public void save(Employer employer) {
        try {
            repository.save(employer);
        } catch (Exception e) {
            throw new UserAlreadyExistsException();
        }
    }

    @Override
    public Optional<Employer> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public Optional<List<String>> findEmployerEmailsByRole(Set<Role> roles) {
        List<Employer> employersWithEmailByRole = repository.findAllNotShippedOrders(roles);
        if(!employersWithEmailByRole.isEmpty()) {
            return Optional.of(employersWithEmailByRole.stream().map(Employer::getEmail).collect(Collectors.toList()));
        }
        return Optional.empty();
    }
}
