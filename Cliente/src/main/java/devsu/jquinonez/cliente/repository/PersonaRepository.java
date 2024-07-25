package devsu.jquinonez.cliente.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import devsu.jquinonez.cliente.entity.Persona;

public interface PersonaRepository extends JpaRepository<Persona, Long> {
}