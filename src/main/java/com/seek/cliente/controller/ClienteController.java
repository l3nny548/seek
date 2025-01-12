package com.seek.cliente.controller;

import com.seek.cliente.dto.ClienteDto;
import com.seek.cliente.service.ClienteService;
import com.seek.cliente.service.ValidationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private ValidationService validationService;

    @PutMapping
    public ResponseEntity<ClienteDto> registrarCliente(@RequestBody ClienteDto cliente) {
        validationService.validate(cliente);
        ClienteDto nuevoCliente = clienteService.registrarCliente(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCliente);
    }

    @GetMapping
    public ResponseEntity<List<ClienteDto>> listarClientes() {
        return ResponseEntity.ok(clienteService.listarClientes());
    }

    @GetMapping("/metricas")
    public ResponseEntity<Map<String, Object>> obtenerMetricas() {
        return ResponseEntity.ok(clienteService.obtenerMetricas());
    }
}

