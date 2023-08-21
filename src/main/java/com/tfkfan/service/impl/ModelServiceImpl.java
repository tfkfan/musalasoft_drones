package com.tfkfan.service.impl;

import com.tfkfan.domain.Model;
import com.tfkfan.repository.ModelRepository;
import com.tfkfan.service.ModelService;
import com.tfkfan.service.dto.ModelDTO;
import com.tfkfan.service.mapper.ModelMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Model}.
 */
@Service
@Transactional
public class ModelServiceImpl implements ModelService {

    private final Logger log = LoggerFactory.getLogger(ModelServiceImpl.class);

    private final ModelRepository modelRepository;

    private final ModelMapper modelMapper;

    public ModelServiceImpl(ModelRepository modelRepository, ModelMapper modelMapper) {
        this.modelRepository = modelRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ModelDTO save(ModelDTO modelDTO) {
        log.debug("Request to save Model : {}", modelDTO);
        Model model = modelMapper.toEntity(modelDTO);
        model = modelRepository.save(model);
        return modelMapper.toDto(model);
    }

    @Override
    public ModelDTO update(ModelDTO modelDTO) {
        log.debug("Request to update Model : {}", modelDTO);
        Model model = modelMapper.toEntity(modelDTO);
        model = modelRepository.save(model);
        return modelMapper.toDto(model);
    }

    @Override
    public Optional<ModelDTO> partialUpdate(ModelDTO modelDTO) {
        log.debug("Request to partially update Model : {}", modelDTO);

        return modelRepository
            .findById(modelDTO.getId())
            .map(existingModel -> {
                modelMapper.partialUpdate(existingModel, modelDTO);

                return existingModel;
            })
            .map(modelRepository::save)
            .map(modelMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ModelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Models");
        return modelRepository.findAll(pageable).map(modelMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ModelDTO> findOne(Long id) {
        log.debug("Request to get Model : {}", id);
        return modelRepository.findById(id).map(modelMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Model : {}", id);
        modelRepository.deleteById(id);
    }
}
