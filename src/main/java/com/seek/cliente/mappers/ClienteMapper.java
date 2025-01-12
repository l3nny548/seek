package com.seek.cliente.mappers;
import java.util.List;

import org.mapstruct.Mapper;

import com.seek.cliente.dto.ClienteDto;
import com.seek.cliente.entity.Cliente;

@Mapper
public interface ClienteMapper {
    
    Cliente dtoToEntity(ClienteDto cliente);

    ClienteDto entityToDto(Cliente cliente);

    List<ClienteDto> dtosToEntitys(List<Cliente> clientes);


}