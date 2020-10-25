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

import java.util.ArrayList;

@SpringBootTest
public class ApiApplicationTests {

    private static final Logger logger = LoggerFactory.getLogger(ApiApplicationTests.class);

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CharacterRepository characterRepository;

    @Test
    void testInsertAccount() {
        accountRepository.deleteAll();
        characterRepository.deleteAll();

        ArrayList<Account> accounts = new ArrayList<>();
        ArrayList<Character> characters = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            accounts.add(Account.builder().platformId("testpid" + i).build());
        }

        accounts.forEach(account -> {
            for (int i = 0; i < 2; i++) {
                characters.add(Character.builder().AccountId(account.getId()).build());
            }
        });

        accountRepository.saveAll(accounts).then().subscribe(unused -> {
            characterRepository.saveAll(characters).then().subscribe(unused1 -> {
                accountRepository.findAll().subscribe(account -> logger.info(account.toString()));
                characterRepository.findAll().subscribe(character -> logger.info(character.toString()));
            });
        });
    }
}
