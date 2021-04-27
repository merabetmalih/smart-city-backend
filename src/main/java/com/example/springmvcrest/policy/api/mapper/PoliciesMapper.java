package com.example.springmvcrest.policy.api.mapper;

import com.example.springmvcrest.policy.api.dto.PoliciesDto;
import com.example.springmvcrest.policy.domain.Policies;
import com.example.springmvcrest.store.service.StoreService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {StoreService.class })

public interface PoliciesMapper {

    PoliciesDto toDto(Policies policies);

    @Mapping(source = "policiesDto.providerId", target = "store")
    Policies toModel(PoliciesDto policiesDto);

}
