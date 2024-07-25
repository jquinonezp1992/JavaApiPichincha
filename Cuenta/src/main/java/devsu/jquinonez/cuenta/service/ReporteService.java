package devsu.jquinonez.cuenta.service;

import devsu.jquinonez.cuenta.client.ClienteClient;
import devsu.jquinonez.cuenta.entity.Cuenta;
import devsu.jquinonez.cuenta.entity.Movimientos;
import devsu.jquinonez.cuenta.repository.CuentaRepository;
import devsu.jquinonez.cuenta.repository.MovimientosRepository;
import devsu.jquinonez.cuenta.service.dto.ClienteDTO;
import devsu.jquinonez.cuenta.service.dto.CuentaDTO;
import devsu.jquinonez.cuenta.service.dto.MovimientoDTO;
import devsu.jquinonez.cuenta.service.dto.ReporteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReporteService {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private MovimientosRepository movimientosRepository;

    @Autowired
    private ClienteClient clienteClient;

    public ReporteDTO generarReporte(Long clienteId, Date fechaInicio, Date fechaFin) {
        ClienteDTO cliente = clienteClient.getClienteById(clienteId);
        List<Cuenta> cuentas = cuentaRepository.findByClienteId(clienteId);

        ReporteDTO reporte = new ReporteDTO();
        reporte.setCliente(cliente);
        reporte.setCuentas(cuentas.stream().map(cuenta -> mapToCuentaDTO(cuenta, fechaInicio, fechaFin)).collect(Collectors.toList()));

        return reporte;
    }

    private CuentaDTO mapToCuentaDTO(Cuenta cuenta, Date fechaInicio, Date fechaFin) {
        CuentaDTO cuentaDTO = new CuentaDTO();
        cuentaDTO.setCuentaId(cuenta.getCuentaId());
        cuentaDTO.setNumeroCuenta(cuenta.getNumeroCuenta());
        cuentaDTO.setTipoCuenta(cuenta.getTipo());
        cuentaDTO.setSaldoInicial(cuenta.getSaldoInicial());
        cuentaDTO.setEstado(cuenta.isEstado());

        List<Movimientos> movimientos = movimientosRepository.findByCuentaIdAndFechaBetween(cuenta.getCuentaId(), fechaInicio, fechaFin);
        cuentaDTO.setMovimientos(movimientos.stream().map(this::mapToMovimientoDTO).collect(Collectors.toList()));

        return cuentaDTO;
    }

    private MovimientoDTO mapToMovimientoDTO(Movimientos movimiento) {
        MovimientoDTO movimientoDTO = new MovimientoDTO();
        movimientoDTO.setId(movimiento.getId());
        movimientoDTO.setFecha(movimiento.getFecha());
        movimientoDTO.setTipo(movimiento.getTipo());
        movimientoDTO.setValor(movimiento.getValor());
        movimientoDTO.setSaldo(movimiento.getSaldo());
        return movimientoDTO;
    }
}
