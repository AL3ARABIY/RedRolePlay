package org.data.redroleplay.dtos.whiteListRequest;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.data.redroleplay.annotations.EnumValue;
import org.data.redroleplay.enums.WhitelistRequestStatus;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VerifyWhitelistRequestDto {
    @NotNull(message = "{Field.required}")
    @EnumValue(enumClass = WhitelistRequestStatus.class, message = "{NotValidEnum.whitelistRequest.status}")
    private String status;

    @NotNull(message = "{Field.required}")
    @Size(min = 10, max = 5000, message = "{Field.size}")
    private String reason;

    @Size(min = 0, max = 5000, message = "{Field.size}")
    private String note;
}
