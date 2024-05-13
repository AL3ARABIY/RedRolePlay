package org.data.redroleplay.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile {

    private String discordAvatarUrl;

    private String mtaUsername;

    private String email;
}
