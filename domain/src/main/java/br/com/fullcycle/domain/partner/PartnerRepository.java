package br.com.fullcycle.domain.partner;

import java.util.Optional;

import br.com.fullcycle.domain.person.Cnpj;
import br.com.fullcycle.domain.person.Email;

public interface PartnerRepository {

    Optional<Partner> partnerOfId(PartnerId partnerId);
    Optional<Partner> partnerOfCNPJ(Cnpj cnpj);
    Optional<Partner> partnerOfEmail(Email email);
    Partner create(Partner partner);
    Partner update(Partner partner);
    void deleteAll();
}
