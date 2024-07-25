package devsu.jquinonez.cuenta.service;

import devsu.jquinonez.cuenta.entity.Cuenta;
import devsu.jquinonez.cuenta.entity.Movimientos;
import devsu.jquinonez.cuenta.exception.InvalidSaldoInicialException;
import devsu.jquinonez.cuenta.exception.ResourceNotFoundException;
import devsu.jquinonez.cuenta.repository.CuentaRepository;
import devsu.jquinonez.cuenta.repository.MovimientosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CuentaService {
    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private MovimientosRepository movimientosRepository;

    public List<Cuenta> findAll() {
        return cuentaRepository.findAll();
    }

    public Optional<Cuenta> findById(Long id) {
        return cuentaRepository.findById(id);
    }

    public Cuenta save(Cuenta cuenta) {
        if (cuenta.getSaldoInicial() <= 0) {
            throw new InvalidSaldoInicialException("El saldo inicial debe ser mayor que 0");
        }

        Cuenta savedCuenta = cuentaRepository.save(cuenta);

        Movimientos movimientos = new Movimientos();
        movimientos.setFecha(new Date());
        movimientos.setTipo(cuenta.getTipo());
        movimientos.setValor(cuenta.getSaldoInicial());
        movimientos.setSaldo(cuenta.getSaldoInicial());
        movimientos.setCuenta(savedCuenta);

        movimientosRepository.save(movimientos);

        return savedCuenta;
    }

    public Cuenta update(Cuenta cuenta) {
        if (cuenta.getSaldoInicial() <= 0) {
            throw new InvalidSaldoInicialException("El saldo inicial debe ser mayor que 0");
        }

        if (cuenta.getCuentaId() != null) {
            Cuenta existingCuenta = cuentaRepository.findById(cuenta.getCuentaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Cuenta no encontrada"));

            existingCuenta.setNumeroCuenta(cuenta.getNumeroCuenta());
            existingCuenta.setTipo(cuenta.getTipo());
            existingCuenta.setSaldoInicial(cuenta.getSaldoInicial());
            existingCuenta.setEstado(cuenta.isEstado());
            existingCuenta.setClienteId(cuenta.getClienteId());

            cuenta = existingCuenta;
        }

        Cuenta savedCuenta = cuentaRepository.save(cuenta);

        Movimientos movimientos = new Movimientos();
        movimientos.setFecha(new Date());
        movimientos.setTipo(cuenta.getTipo());
        movimientos.setValor(cuenta.getSaldoInicial());
        movimientos.setSaldo(cuenta.getSaldoInicial());
        movimientos.setCuenta(savedCuenta);

        movimientosRepository.save(movimientos);

        return savedCuenta;
    }

    public void deleteById(Long id) {
        cuentaRepository.deleteById(id);
    }
}