package br.com.fullcycle.infrastructure.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;

import br.com.fullcycle.infrastructure.http.SpringHttpRouter;
import br.com.fullcycle.infrastructure.rest.PartnerFnController;

//Frameworks and Drivers
@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<?> routes(
            final PartnerFnController partnerFnController
    ) {
        final var router = new SpringHttpRouter();
        partnerFnController.bind(router);
        return router.router().build();
    }
}
