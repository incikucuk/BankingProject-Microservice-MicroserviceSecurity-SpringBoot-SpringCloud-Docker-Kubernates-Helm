package com.bankingSystem.Loans.audit;

//bu class kullanım amacı cratebydate-createby vb. gibi denetimleri kullanıcının kendi yapması yerine denetimi spring'e vererek rest apilerimizi ve mikroseris içinde de takip edebilmeyi sagliyor.

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditAwareImpl")
public class AuditAwareImpl implements AuditorAware<String> {   //createdvby createddata gibi ifadeler(baseEntitiy fiedlarından birkaci) string tründe oldugu icin donus tipi string verildi.

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("ACCOUNTS_MS");  //ACCOUNTS_MS mikroservis oldugu belirtildi cunku createdby gibi degerler tutulmak isteniyor.
    }
}
