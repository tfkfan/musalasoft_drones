package com.tfkfan.service.mapper;

import com.tfkfan.domain.Model;
import com.tfkfan.service.dto.ModelDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Model} and its DTO {@link ModelDTO}.
 */
@Mapper(componentModel = "spring")
public interface ModelMapper extends EntityMapper<ModelDTO, Model> {}
