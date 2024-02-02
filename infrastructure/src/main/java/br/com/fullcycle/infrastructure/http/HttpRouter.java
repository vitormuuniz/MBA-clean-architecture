package br.com.fullcycle.infrastructure.http;

public interface HttpRouter {

    <T> void POST(String pattern, HttpHandler<T> handler);
    <T> void GET(String pattern, HttpHandler<T> handler);
}
