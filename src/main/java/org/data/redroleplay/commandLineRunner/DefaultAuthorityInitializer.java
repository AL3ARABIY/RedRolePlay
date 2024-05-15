package org.data.redroleplay.commandLineRunner;

import lombok.RequiredArgsConstructor;
import org.data.redroleplay.entities.website.Authority;
import org.data.redroleplay.enums.BaseAuthority;
import org.data.redroleplay.repositories.website.AuthorityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DefaultAuthorityInitializer implements CommandLineRunner {

    private final AuthorityRepository authorityRepository;

    @Override
    public void run(String... args) throws Exception {
        List<Authority> authorities = Arrays.stream(BaseAuthority.values()).map(Authority::new).toList();
        authorityRepository.saveAll(authorities);
    }
}
