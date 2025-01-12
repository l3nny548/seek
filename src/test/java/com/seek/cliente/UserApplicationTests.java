package com.seek.cliente;
import com.seek.cliente.dto.ClienteDto;
import com.seek.cliente.entity.Cliente;
import com.seek.cliente.repository.ClienteRepository;
import com.seek.cliente.service.ClienteService;
import com.seek.cliente.mappers.ClienteMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserApplicationTests {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteMapper clienteMapper;

    @SuppressWarnings("removal")
    @MockBean
    private ClienteRepository clienteRepository;

    @Test
    void testRegistrarCliente() {
        ClienteDto.ClienteDtoBuilder clienteBuilder = ClienteDto.builder();
        ClienteDto clienteDto = clienteBuilder.id(null)
        .nombre("Juan")
        .apellido("Perez")
        .edad(30)
        .fechaNacimiento(LocalDate.of(1993, 5, 15))
        .build();

        Cliente clienteEntity = clienteMapper.dtoToEntity(clienteDto);
        when(clienteRepository.save(clienteEntity)).thenReturn(clienteEntity);
        ClienteDto result = clienteService.registrarCliente(clienteDto);
        verify(clienteRepository, times(1)).save(clienteEntity);
        assertEquals(clienteDto.getNombre(), result.getNombre());
        assertEquals(clienteDto.getApellido(), result.getApellido());
    }

    @Test
    void testObtenerMetricas() {
            Cliente.ClienteBuilder cliente1 = Cliente.builder();
            cliente1.id(1L)
            .nombre("Juan")
            .apellido("Perez")
            .edad(30)
            .fechaNacimiento(LocalDate.of(1993, 5, 15));

            Cliente.ClienteBuilder cliente2 = Cliente.builder();
            cliente2.id(2L)
            .nombre("Ana")
            .apellido("Gomez")
            .edad(40)
            .fechaNacimiento(LocalDate.of(1983, 7, 10));

            List<Cliente> clientes = Arrays.asList(cliente1.build(), cliente2.build());
			when(clienteRepository.findAll()).thenReturn(clientes);
			Map<String, Object> metricas = clienteService.obtenerMetricas();
			assertEquals(35.0, metricas.get("promedioEdad"));
			assertEquals(5.0, metricas.get("desviacionEstandar"));
    }
}
