package devsu.jquinonez.cuenta.client;

import devsu.jquinonez.cuenta.service.dto.ClienteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/*Cliente para consumir el micro de Cliente*/
@FeignClient(name = "cliente-servicio", url = "${microservicio.cliente.url}")
public interface ClienteClient {

    @GetMapping("/clientes/{id}")
    ClienteDTO getClienteById(@PathVariable("id") Long id);
}
