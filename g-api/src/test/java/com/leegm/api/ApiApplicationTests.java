package com.leegm.api;


import com.leegm.api.domain.Account;
import com.leegm.api.domain.Character;
import com.leegm.api.repository.AccountRepository;
import com.leegm.api.repository.CharacterRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

@SpringBootTest
public class ApiApplicationTests {

    private static final Logger logger = LoggerFactory.getLogger(ApiApplicationTests.class);

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CharacterRepository characterRepository;

    @Test
    void testInsertAccount() throws InterruptedException {
        accountRepository.deleteAll().block();
        characterRepository.deleteAll().block();
        ArrayList<Account> accounts = new ArrayList<>();
        ArrayList<Character> characters = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Account account = Account.builder().platformId("testpid" + i).build();
            accounts.add(accountRepository.save(account).block());
        }

        accounts.forEach(account -> {
            for (int i = 0; i < 2; i++) {
                Character character = Character.builder().Account(account).build();
                characters.add(characterRepository.save(character).block());
            }
        });

        accountRepository.findAll().subscribe(account -> logger.info(account.toString()));
        characterRepository.findAll().subscribe(character -> logger.info(character.toString()));
    }
}
