package com.seek.cliente.mappers;

import org.mapstruct.Mapper;
import com.seek.cliente.dto.AuthDto;
import com.seek.cliente.entity.Auth;

@Mapper
public interface AuthMapper {
    
    Auth dtoToEntity(AuthDto cliente);

    AuthDto entityToDto(Auth cliente);



}