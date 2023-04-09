package ru.volgau.graduatework.biotrofbackend.domain.service;

import ru.volgau.graduatework.biotrofbackend.domain.entity.Client;

public interface ClientDaoService {

    void save(Client client);

    Client getClientById(String uuid);

    Client findByFioAndPhone(String fio, String phone);
}
