package devsu.jquinonez.cuenta.repository;

import devsu.jquinonez.cuenta.entity.Movimientos;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import devsu.jquinonez.cuenta.entity.Cuenta;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
    @Query("SELECT c FROM Cuenta c WHERE c.clienteId = :clienteId")
    List<Cuenta> findByClienteId(@Param("clienteId") Long clienteId);
}