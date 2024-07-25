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
public class MovimientosService {
    @Autowired
    private MovimientosRepository movimientosRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

    public List<Movimientos> findAll() {
        return movimientosRepository.findAll();
    }

    public Optional<Movimientos> findById(Long id) {
        return movimientosRepository.findById(id);
    }

    public Movimientos save(Movimientos movimientos) {
        Movimientos ultimoMovimiento = movimientosRepository.findTopByCuentaIdOrderByIdDesc(movimientos.getCuenta().getCuentaId());
        Optional<Cuenta> cuenta = cuentaRepository.findById(movimientos.getCuenta().getCuentaId());
        if (cuenta.isEmpty())
        {
            throw new ResourceNotFoundException("No se puede insertar el movimiento porque no existe la cuenta.");
        }
        movimientos.setCuenta(cuenta.orElse(null));
        if (movimientos.getValor() == 0) {
            throw new InvalidSaldoInicialException("El valor debe ser un debito(menor a 0) o un credito(mayor a 0)");
        }

        if (movimientos.getValor() < 0) {
            double valor = Math.abs(movimientos.getValor());
            if (valor > ultimoMovimiento.getSaldo()) {
                throw new InvalidSaldoInicialException("Saldo No Disponible");
            }
        }

        double saldoFinal = ultimoMovimiento.getSaldo() + movimientos.getValor();
        movimientos.setSaldo(saldoFinal);
        movimientos.setFecha(new Date());
        return movimientosRepository.save(movimientos);
    }

    public void deleteById(Long id) {
        movimientosRepository.deleteById(id);
    }
}