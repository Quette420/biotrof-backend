package ru.volgau.graduatework.biotrofbackend.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.volgau.graduatework.biotrofbackend.domain.entity.Employer;
import ru.volgau.graduatework.biotrofbackend.model.request.CreateEmployerRequest;
import ru.volgau.graduatework.biotrofbackend.model.response.EmployerRegistrationResponse;
import ru.volgau.graduatework.biotrofbackend.model.response.GetUserInfoResponse;

@Mapper(componentModel = "spring")
public interface EmployerMapper {

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", constant = "USER")
    @Mapping(target = "status", constant = "ACTIVE")
    Employer createEmployerRequestToEmployer(CreateEmployerRequest request);

    EmployerRegistrationResponse employerToRegistrationResponse(Employer employer);

    GetUserInfoResponse employerToGetEmployerInfoResponse(Employer employer);
}
