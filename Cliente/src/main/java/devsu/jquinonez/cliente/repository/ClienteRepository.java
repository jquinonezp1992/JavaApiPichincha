package devsu.jquinonez.cliente.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import devsu.jquinonez.cliente.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}