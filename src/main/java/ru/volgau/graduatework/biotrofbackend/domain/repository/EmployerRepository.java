package ru.volgau.graduatework.biotrofbackend.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.volgau.graduatework.biotrofbackend.domain.entity.Employer;

import java.util.Optional;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, String> {

    Optional<Employer> findByUsername(String username);
}
