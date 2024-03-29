package br.com.fullcycle.application.partner;

import java.util.Objects;
import java.util.Optional;

import br.com.fullcycle.application.UseCase;
import br.com.fullcycle.domain.partner.PartnerId;
import br.com.fullcycle.domain.partner.PartnerRepository;

//Port
public class GetPartnerByIdUseCase extends UseCase<GetPartnerByIdUseCase.Input, Optional<GetPartnerByIdUseCase.Output>> {

    private final PartnerRepository partnerRepository;

    public GetPartnerByIdUseCase(PartnerRepository partnerRepository) {
        this.partnerRepository = Objects.requireNonNull(partnerRepository);
    }

    @Override
    public Optional<Output> execute(Input input) {
        return partnerRepository.partnerOfId(PartnerId.with(input.id))
                .map(partner -> new Output(
                                    partner.partnerId().value(),
                                    partner.cnpj().value(),
                                    partner.email().value(),
                                    partner.name().value())
                );
    }

    public record Input(String id) {}

    public record Output(String id, String cnpj, String email, String name) {}
}
