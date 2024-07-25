package devsu.jquinonez.cuenta.service.dto;

import java.util.Date;
import java.util.List;

public class ReporteDTO {
    private ClienteDTO cliente;
    private List<CuentaDTO> cuentas;

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    public List<CuentaDTO> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<CuentaDTO> cuentas) {
        this.cuentas = cuentas;
    }

}
