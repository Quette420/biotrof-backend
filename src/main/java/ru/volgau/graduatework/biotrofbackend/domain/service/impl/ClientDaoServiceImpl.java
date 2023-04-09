package ru.volgau.graduatework.biotrofbackend.domain.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.volgau.graduatework.biotrofbackend.domain.entity.Client;
import ru.volgau.graduatework.biotrofbackend.domain.repository.ClientRepository;
import ru.volgau.graduatework.biotrofbackend.domain.service.ClientDaoService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientDaoServiceImpl implements ClientDaoService {

    private final ClientRepository repository;

    @Override
    public void save(Client client) {
        repository.save(client);
    }

    @Override
    public Client getClientById(String uuid) {
        return repository.getById(uuid);
    }

    @Override
    public Client findByFioAndPhone(String fio, String phone) {
        String[] fioArray = fio.split("\\ ");
        List<Client> clients = repository.findByFioAndPhoneNumber(fioArray[0], fioArray[1], fioArray[2], phone);
        return clients.isEmpty() ? null : clients.get(0);
    }
}
