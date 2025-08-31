package ru.book.order.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.book.order.client.service.ClientServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientServiceImplTest {

    private ClientRepository clientRepository;
    private ClientServiceImpl clientService;

    @BeforeEach
    void setUp() {
        clientRepository = mock(ClientRepository.class);
        clientService = new ClientServiceImpl(clientRepository);
    }

    @Test
    void testGetAllClients() {
        Client client1 = new Client();
        client1.setFullName("Иван Иванов");
        Client client2 = new Client();
        client2.setFullName("Пётр Петров");

        when(clientRepository.findAll()).thenReturn(Arrays.asList(client1, client2));

        List<Client> clients = clientService.getAllClients();
        assertEquals(2, clients.size());
        assertEquals("Иван Иванов", clients.getFirst().getFullName());
        verify(clientRepository, times(1)).findAll();
    }

    @Test
    void testGetClient_Found() {
        UUID id = UUID.randomUUID();
        Client client = new Client();
        client.setId(id);
        client.setFullName("Мария Смирнова");

        when(clientRepository.findById(id)).thenReturn(Optional.of(client));

        Client result = clientService.getClient(id);
        assertNotNull(result);
        assertEquals("Мария Смирнова", result.getFullName());
        verify(clientRepository, times(1)).findById(id);
    }

    @Test
    void testGetClient_NotFound() {
        UUID id = UUID.randomUUID();
        when(clientRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> clientService.getClient(id));
        verify(clientRepository, times(1)).findById(id);
    }

    @Test
    void testSaveClient() {
        Client client = new Client();
        client.setFullName("Новая Клиентка");

        when(clientRepository.save(client)).thenReturn(client);

        Client saved = clientService.saveClient(client);
        assertNotNull(saved);
        assertEquals("Новая Клиентка", saved.getFullName());
        verify(clientRepository, times(1)).save(client);
    }
}
