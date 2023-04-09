package ru.volgau.graduatework.biotrofbackend.service;

import ru.volgau.graduatework.biotrofbackend.domain.entity.Client;
import ru.volgau.graduatework.biotrofbackend.model.request.CreateOrderRequest;

public interface ClientService {

    Client findOrCreateNew(CreateOrderRequest request);
}
