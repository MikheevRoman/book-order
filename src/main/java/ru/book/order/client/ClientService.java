package ru.book.order.client;

import java.util.List;
import java.util.UUID;

public interface ClientService {

    List<Client> getAllClients();

    Client getClient(UUID id);

    Client saveClient(Client client);

}
