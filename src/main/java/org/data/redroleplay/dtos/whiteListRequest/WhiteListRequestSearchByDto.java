package org.data.redroleplay.dtos.whiteListRequest;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.data.redroleplay.enums.SearchFieldsWhiteListRequest;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WhiteListRequestSearchByDto {
        @NotNull(message = "{Field.required}")
        private SearchFieldsWhiteListRequest searchField;
        @NotNull(message = "{Field.required}")
        private String searchValue;
}
