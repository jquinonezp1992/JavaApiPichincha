package devsu.jquinonez.cuenta.service;

import devsu.jquinonez.cuenta.entity.Cuenta;
import devsu.jquinonez.cuenta.entity.Movimientos;
import devsu.jquinonez.cuenta.exception.InvalidSaldoInicialException;
import devsu.jquinonez.cuenta.exception.ResourceNotFoundException;
import devsu.jquinonez.cuenta.repository.CuentaRepository;
import devsu.jquinonez.cuenta.repository.MovimientosRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class CuentaServiceIntegrationTest {

    @Autowired
    private CuentaService cuentaService;

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private MovimientosRepository movimientosRepository;

    @Test
    public void testSaveCuenta() {
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta("1234567890");
        cuenta.setTipo("Ahorros");
        cuenta.setSaldoInicial(8000.75);
        cuenta.setEstado(true);
        cuenta.setClienteId(1L);  // Ajusta según tus necesidades

        Cuenta savedCuenta = cuentaService.save(cuenta);

        assertNotNull(savedCuenta);
        assertNotNull(savedCuenta.getCuentaId());

    }

    @Test
    public void testUpdateCuenta() {
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta("1234567890");
        cuenta.setTipo("Ahorros");
        cuenta.setSaldoInicial(8000.75);
        cuenta.setEstado(true);
        cuenta.setClienteId(1L);

        Cuenta savedCuenta = cuentaService.save(cuenta);

        savedCuenta.setTipo("Corriente");
        savedCuenta.setSaldoInicial(9000.00);

        Cuenta updatedCuenta = cuentaService.update(savedCuenta);

        assertNotNull(updatedCuenta);
        assertEquals("Corriente", updatedCuenta.getTipo());
        assertEquals(9000.00, updatedCuenta.getSaldoInicial());
    }

    @Test
    public void testDeleteCuenta() {
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta("1234567890");
        cuenta.setTipo("Ahorros");
        cuenta.setSaldoInicial(8000.75);
        cuenta.setEstado(true);
        cuenta.setClienteId(1L);

        Cuenta savedCuenta = cuentaService.save(cuenta);
        cuentaService.deleteById(savedCuenta.getCuentaId());
        assertFalse(cuentaRepository.findById(savedCuenta.getCuentaId()).isPresent());
    }

    @Test
    public void testSaveCuentaWithInvalidSaldo() {
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta("1234567890");
        cuenta.setTipo("Ahorros");
        cuenta.setSaldoInicial(-8000.75);
        cuenta.setEstado(true);
        cuenta.setClienteId(1L);

        assertThrows(InvalidSaldoInicialException.class, () -> {
            cuentaService.save(cuenta);
        });
    }
}
