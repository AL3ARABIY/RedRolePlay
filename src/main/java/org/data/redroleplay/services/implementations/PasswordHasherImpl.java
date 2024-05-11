package org.data.redroleplay.services.implementations;

import org.apache.commons.codec.digest.DigestUtils;
import org.data.redroleplay.services.PasswordHasher;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.stream.IntStream;

@Service
public class PasswordHasherImpl implements PasswordHasher {

        @Override
        public String generateHashedPassword(String password, String salt) {
            String step1 = md5(password) + salt;
            String step2 = step1.toLowerCase();
            String step3 = md5(step2);
            return step3.toLowerCase();
        }

        @Override
        public boolean verify(String hashedPassword, String salt, String password) {
            return generateHashedPassword(password, salt).equals(hashedPassword);
        }

        @Override
        public String generateSalt() {
            StringBuilder encryptionRule = new StringBuilder();
            Random random = new Random();
            IntStream.range(0, 10).forEach(i -> encryptionRule.append(random.nextInt(10)));
            return encryptionRule.toString();
        }

        private String md5(String password) {
            return DigestUtils.md5Hex(password);
        }
}
