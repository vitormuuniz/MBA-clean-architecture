package br.com.fullcycle.infrastructure.http;

import java.util.Optional;

public interface HttpRequest {

    <T> T body(Class<T> tClass);
    String pathParam(String name);
    Optional<String> queryParam(String name);
}
