package br.com.fullcycle.application;

public abstract class UseCase<INPUT, OUTPUT> {

    //1 - Cada caso de uso tem um INPUT e um OUTPUT próprio. Não retorna a entidade, o agregado ou o objeto de valor
    //2 - O caso de uso implementa o padrão Command (cada classe deve ter um único método público chamado execute)

    public abstract OUTPUT execute(INPUT input);

    public <T> T execute(INPUT input, Presenter<OUTPUT, T> presenter) {
        try {
            return presenter.present(execute(input));
        } catch (Throwable t) {
            return presenter.present(t);
        }
    }
}
