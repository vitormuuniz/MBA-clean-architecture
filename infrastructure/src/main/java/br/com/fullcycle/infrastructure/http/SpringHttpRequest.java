package br.com.fullcycle.infrastructure.http;

import java.util.Optional;

import org.springframework.web.servlet.function.ServerRequest;

public record SpringHttpRequest(ServerRequest request) implements HttpRequest {

    @Override
    public <T> T body(final Class<T> tClass) {
        try {
            return request.body(tClass);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String pathParam(String name) {
        return request.pathVariable(name);
    }

    @Override
    public Optional<String> queryParam(String name) {
        return request.param(name);
    }
}
