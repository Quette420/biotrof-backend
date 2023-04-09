package ru.volgau.graduatework.biotrofbackend.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.volgau.graduatework.biotrofbackend.domain.entity.Client;
import ru.volgau.graduatework.biotrofbackend.model.request.CreateOrderRequest;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class ClientMapper {


    @Mapping(target = "lastName", expression = "java(getFioValue(request.getClientFio(), 0))")
    @Mapping(target = "firstName", expression = "java(getFioValue(request.getClientFio(), 1))")
    @Mapping(target = "middleName", expression = "java(getFioValue(request.getClientFio(), 2))")
    public abstract Client createClientByCreateOrderRequest(CreateOrderRequest request);

    protected String getFioValue(String fio, int index) {
        String[] words = fio.split("\\ ");
        return words[index];
    }
}
