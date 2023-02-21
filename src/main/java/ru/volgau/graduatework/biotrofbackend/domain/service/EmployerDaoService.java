package ru.volgau.graduatework.biotrofbackend.domain.service;

import ru.volgau.graduatework.biotrofbackend.domain.entity.Employer;

import java.util.List;
import java.util.Optional;

public interface EmployerDaoService {

    Employer getById(String uuid);

    List<Employer> getAll();

    void save(Employer employer);

    Optional<Employer> findByUsername(String username);
}
