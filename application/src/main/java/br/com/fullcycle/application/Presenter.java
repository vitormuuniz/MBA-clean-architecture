package br.com.fullcycle.application;

//Modifica a resposta do caso de uso para a desejada
public interface Presenter<IN, OUT> {

    OUT present(IN input);
    OUT present(Throwable error);
}
