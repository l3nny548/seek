package com.seek.cliente.service;

import com.seek.cliente.dto.ClienteDto;
import com.seek.cliente.entity.Cliente;
import com.seek.cliente.mappers.ClienteMapper;
import com.seek.cliente.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ClienteMapper clienteMapper;

    public ClienteDto registrarCliente(ClienteDto cliente) {
        try{
            Cliente clienteEntity = clienteMapper.dtoToEntity(cliente);
            Cliente clienteResult= clienteRepository.save(clienteEntity);
            return clienteMapper.entityToDto(clienteResult);
        }catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al registrar cliente.", ex);
        }
    }

    public List<ClienteDto> listarClientes() {
        try{
            List<Cliente> clientes = clienteRepository.findAll();
            double esperanzaVidaPromedio = 80;
            List<ClienteDto> clienteDtos = clienteMapper.dtosToEntitys(clientes);

            for (ClienteDto clienteDto : clienteDtos) {
                double esperanzaDeVida = esperanzaVidaPromedio - clienteDto.getEdad();
                clienteDto.setEsperanzaDeVida(esperanzaDeVida);
            }
    
            return clienteDtos;
        }catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al listar clientes.", ex);
        }
        
    }

    public Map<String, Object> obtenerMetricas() {
        try{
            List<Cliente> clientes = clienteRepository.findAll();
            double promedio = clientes.stream().mapToInt(Cliente::getEdad).average().orElse(0.0);
            double desviacion = Math.sqrt(clientes.stream().mapToDouble(c -> Math.pow(c.getEdad() - promedio, 2)).average().orElse(0.0));
            Map<String, Object> metricas = new HashMap<>();
            metricas.put("promedioEdad", promedio);
            metricas.put("desviacionEstandar", desviacion);
            return metricas;
        }catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al calcular métricas.", ex);
        }

       
    }
}
