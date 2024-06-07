package org.data.redroleplay.mappers;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.data.redroleplay.dtos.whitelistrequest.WhitelistRequestDisplayForUserDto;
import org.data.redroleplay.entities.website.WhitelistRequest;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@NoArgsConstructor
public class WhitelistRequestDisplayForUserDtoMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    public WhitelistRequestDisplayForUserDto map(WhitelistRequest whitelistRequest) {
        return modelMapper.map(whitelistRequest, WhitelistRequestDisplayForUserDto.class);
    }

    public List<WhitelistRequestDisplayForUserDto> map(List<WhitelistRequest> whitelistRequests) {
        return whitelistRequests.stream().map(this::map).toList();
    }

}
