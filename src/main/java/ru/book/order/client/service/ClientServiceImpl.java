package ru.book.order.client.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.book.order.client.Client;
import ru.book.order.client.ClientRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public Client getClient(UUID id) {
        return clientRepository.findById(id).orElseThrow();
    }

    @Override
    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }

}
