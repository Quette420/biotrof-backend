package ru.volgau.graduatework.biotrofbackend.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.volgau.graduatework.biotrofbackend.dictionary.Role;
import ru.volgau.graduatework.biotrofbackend.domain.entity.Employer;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, String> {

    @Query("select e from Employer e where e.role in (:roles) and e.email is not null")
    List<Employer> findAllNotShippedOrders(@Param("roles") Set<Role> roles);

    Optional<Employer> findByUsername(String username);
}
