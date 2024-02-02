package br.com.fullcycle.infrastructure.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.function.HandlerFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

public class SpringHttpRouter implements HttpRouter {

    private static final Logger LOG = LoggerFactory.getLogger(SpringHttpRouter.class);

    private final RouterFunctions.Builder router;

    public SpringHttpRouter() {
        this.router = RouterFunctions.route();
    }

    public RouterFunctions.Builder router() {
        return router;
    }

    @Override
    public <T> void POST(String pattern, HttpHandler<T> handler) {
        this.router.POST(pattern, wrapHandler(pattern, handler));
    }

    @Override
    public <T> void GET(String pattern, HttpHandler<T> handler) {
        this.router.GET(pattern, wrapHandler(pattern, handler));
    }

    private static <T> HandlerFunction<ServerResponse> wrapHandler(String pattern, HttpHandler<T> handler) {
        return req -> {
            try {
                var res = handler.handle(new SpringHttpRequest(req));
                return ServerResponse.status(res.statusCode())
                        .headers(headers -> res.headers().forEach(headers::add))
                        .body(res.body());
            } catch (final Throwable t) {
                LOG.error("Unexpected error was observed at %s".formatted(pattern), t);
                return ServerResponse.status(500).body("Unexpected error was observed.");
            }
        };
    }
}
