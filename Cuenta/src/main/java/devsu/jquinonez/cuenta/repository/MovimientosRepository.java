package devsu.jquinonez.cuenta.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import devsu.jquinonez.cuenta.entity.Movimientos;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface MovimientosRepository extends JpaRepository<Movimientos, Long> {
    @Query("SELECT m FROM Movimientos m WHERE m.cuenta.cuentaId = :cuentaId ORDER BY m.id DESC")
    List<Movimientos> findByCuentaIdOrderByIdDesc(@Param("cuentaId") Long cuentaId, Pageable pageable);

    default Movimientos findTopByCuentaIdOrderByIdDesc(Long cuentaId) {
        Pageable pageable = Pageable.ofSize(1);
        List<Movimientos> movimientos = findByCuentaIdOrderByIdDesc(cuentaId, pageable);
        return movimientos.isEmpty() ? null : movimientos.get(0);
    }

    @Query("SELECT m FROM Movimientos m WHERE m.cuenta.cuentaId = :cuentaId AND DATE(m.fecha) >= :fechaInicio AND DATE(m.fecha) <= :fechaFin")
    List<Movimientos> findByCuentaIdAndFechaBetween(
            @Param("cuentaId") Long cuentaId,
            @Param("fechaInicio") Date fechaInicio,
            @Param("fechaFin") Date fechaFin
    );
}