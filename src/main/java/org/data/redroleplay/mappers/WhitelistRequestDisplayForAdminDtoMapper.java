package org.data.redroleplay.mappers;

import lombok.Getter;
import org.data.redroleplay.dtos.whitelistrequest.WhitelistRequestDisplayForAdminDto;
import org.data.redroleplay.entities.website.WhitelistRequest;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
public class WhitelistRequestDisplayForAdminDtoMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    public WhitelistRequestDisplayForAdminDtoMapper() {
        addMappings();
    }

    public WhitelistRequestDisplayForAdminDto map(WhitelistRequest whitelistRequest) {
        return modelMapper.map(whitelistRequest, WhitelistRequestDisplayForAdminDto.class);
    }

    public List<WhitelistRequestDisplayForAdminDto> map(List<WhitelistRequest> whitelistRequests) {
        return whitelistRequests.stream().map(this::map).toList();
    }

    private void addMappings() {
        modelMapper.typeMap(WhitelistRequest.class, WhitelistRequestDisplayForAdminDto.class)
                .addMappings(mapper -> mapper.map( src -> src.getUser().getFullName(), WhitelistRequestDisplayForAdminDto::setUserFullName))
                .addMappings(mapper -> mapper.map( src -> src.getUser().getId(), WhitelistRequestDisplayForAdminDto::setUserId))
                .addMappings(mapper -> mapper.map( src -> src.getVerifiedBy().getFullName(), WhitelistRequestDisplayForAdminDto::setVerifiedByFullName))
                .addMappings(mapper -> mapper.map( src -> src.getVerifiedBy().getId(), WhitelistRequestDisplayForAdminDto::setVerifiedById));

    }
}
