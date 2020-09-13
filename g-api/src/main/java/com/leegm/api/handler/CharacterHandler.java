package com.leegm.api.handler;

import com.leegm.api.domain.Account;
import com.leegm.api.domain.Character;
import com.leegm.api.flatbuffer.FbCharacter;
import com.leegm.api.flatbuffer.FbMethod;
import com.leegm.api.flatbuffer.FbObject;
import com.leegm.api.flatbuffer.FbPayload;
import com.leegm.api.repository.AccountRepository;
import com.leegm.api.util.Const;
import com.leegm.api.util.FbConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.Optional;

@Component
public class CharacterHandler extends AbstractHandler<FbCharacter> {

    @Autowired
    AccountRepository accountRepository;

    @PostConstruct
    public void init() {
        cls = FbCharacter.class;
        demoHandlerFactory.register(FbPayload.FbCharacter, this);
    }

    @Override
    public byte[] handle(String sid, FbCharacter request, byte method) {
        Optional<Account> account = accountRepository.findById(Objects.requireNonNull(request.uid()));

        if (account.isPresent()) {
            switch (method) {
                case FbMethod.C:
                    return addCharacter(account.get(), request.objects(0));
                case FbMethod.D:
                    return delCharacter(account.get(), request.objects(0));
                case FbMethod.R:
                    return FbConverter.toCharacter(account.get());
            }
        }

        return empty();
    }

    private byte[] addCharacter(Account account, FbObject object) {
        if (account.getCharacters().size() <= Const.MAX_CHARACTER_COUNT) {
            account.addCharacter(Character.builder()
                    .name(object.name())
                    .build());

            Account savedAccount = accountRepository.save(account);
            return FbConverter.toCharacter(savedAccount);
        }

        return empty();
    }

    private byte[] delCharacter(Account account, FbObject object) {
        Optional<Character> target = account.getCharacters().stream().filter(x -> x.getId().equals(object.oid())).findFirst();

        if (target.isPresent()) {
            account.getCharacters().remove(target.get());
            Account savedAccount = accountRepository.save(account);
            return FbConverter.toCharacter(savedAccount);
        }

        return empty();
    }
}
