package devsu.jquinonez.cliente.service;

import devsu.jquinonez.cliente.entity.Cliente;
import devsu.jquinonez.cliente.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
public class ClienteServiceTest {

    @InjectMocks
    ClienteService clienteService;

    @Mock
    ClienteRepository clienteRepository;

    public ClienteServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindById() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        Optional<Cliente> found = clienteService.findById(1L);
        assertEquals(cliente.getId(), found.get().getId());
    }

}
