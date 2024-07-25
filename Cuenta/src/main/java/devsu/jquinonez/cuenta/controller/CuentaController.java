package devsu.jquinonez.cuenta.controller;

import devsu.jquinonez.cuenta.entity.Cuenta;
import devsu.jquinonez.cuenta.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {
    @Autowired
    private CuentaService cuentaService;

    @GetMapping
    public List<Cuenta> findAll() {
        return cuentaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cuenta> findById(@PathVariable Long id) {
        Optional<Cuenta> cuenta = cuentaService.findById(id);
        return cuenta.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Cuenta create(@RequestBody Cuenta cuenta) {
        return cuentaService.save(cuenta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cuenta> update(@PathVariable Long id, @RequestBody Cuenta cuentaDetails) {
        Optional<Cuenta> cuenta = cuentaService.findById(id);
        if (cuenta.isPresent()) {
            cuentaDetails.setCuentaId(id);
            return ResponseEntity.ok(cuentaService.update(cuentaDetails));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (cuentaService.findById(id).isPresent()) {
            cuentaService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}