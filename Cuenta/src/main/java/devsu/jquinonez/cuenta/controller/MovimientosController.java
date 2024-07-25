package devsu.jquinonez.cuenta.controller;

import devsu.jquinonez.cuenta.entity.Movimientos;
import devsu.jquinonez.cuenta.service.MovimientosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movimientos")
public class MovimientosController {
    @Autowired
    private MovimientosService movimientosService;

    @GetMapping
    public List<Movimientos> findAll() {
        return movimientosService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movimientos> findById(@PathVariable Long id) {
        Optional<Movimientos> movimientos = movimientosService.findById(id);
        return movimientos.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Movimientos create(@RequestBody Movimientos movimientos) {
        return movimientosService.save(movimientos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movimientos> update(@PathVariable Long id, @RequestBody Movimientos movimientosDetails) {
        Optional<Movimientos> movimientos = movimientosService.findById(id);
        if (movimientos.isPresent()) {
            movimientosDetails.setId(id);
            return ResponseEntity.ok(movimientosService.save(movimientosDetails));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (movimientosService.findById(id).isPresent()) {
            movimientosService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
