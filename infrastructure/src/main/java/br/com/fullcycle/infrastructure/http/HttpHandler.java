package br.com.fullcycle.infrastructure.http;

public interface HttpHandler<T> {

    HttpResponse<T> handle(HttpRequest request);
}
