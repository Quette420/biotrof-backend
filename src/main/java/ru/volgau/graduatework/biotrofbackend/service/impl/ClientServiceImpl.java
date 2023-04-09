package ru.volgau.graduatework.biotrofbackend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.volgau.graduatework.biotrofbackend.domain.entity.Client;
import ru.volgau.graduatework.biotrofbackend.domain.service.ClientDaoService;
import ru.volgau.graduatework.biotrofbackend.mappers.ClientMapper;
import ru.volgau.graduatework.biotrofbackend.model.request.CreateOrderRequest;
import ru.volgau.graduatework.biotrofbackend.service.ClientService;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientDaoService clientDaoService;
    private final ClientMapper clientMapper;

    @Override
    public Client findOrCreateNew(CreateOrderRequest request) {
        Client client = clientDaoService.findByFioAndPhone(request.getClientFio(), request.getPhoneNumber());
        if(client == null) {
            Client clientFromRequest = clientMapper.createClientByCreateOrderRequest(request);
            clientDaoService.save(clientFromRequest);
        }
        return client;
    }
}
