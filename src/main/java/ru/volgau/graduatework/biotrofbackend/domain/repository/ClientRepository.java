package ru.volgau.graduatework.biotrofbackend.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.volgau.graduatework.biotrofbackend.domain.entity.Client;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, String> {

    @Query("select c from Client c where " +
            "c.lastName = :lastName " +
            "and c.lastName = :lastName " +
            "and c.firstName = :firstName " +
            "and c.middleName = :middleName " +
            "and c.phoneNumber = :phone")
    List<Client> findByFioAndPhoneNumber(@Param("lastName") String lastName,
                                         @Param("firstName") String firstName,
                                         @Param("middleName") String middleName,
                                         @Param("phone") String phone);
}
