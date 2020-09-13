package com.leegm.api.handler;

import com.leegm.api.domain.Account;
import com.leegm.api.flatbuffer.FbPayload;
import com.leegm.api.flatbuffer.FbSignIn;
import com.leegm.api.repository.AccountRepository;
import com.leegm.api.util.FbConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Component
public class SignInHandler extends AbstractHandler<FbSignIn> {

    @Autowired
    AccountRepository accountRepository;

    @PostConstruct
    public void init() {
        cls = FbSignIn.class;
        demoHandlerFactory.register(FbPayload.FbSignIn, this);
    }

    @Override
    public byte[] handle(String sid, FbSignIn request, byte method) {
        Optional<Account> account = accountRepository.findByPid(request.pid());

        Account response = account.orElseGet(() -> accountRepository.save(
                Account.builder()
                        .pid(request.pid())
                        .type(0)
                        .build()
        ));

        return FbConverter.toSignIn(response);
    }
}
