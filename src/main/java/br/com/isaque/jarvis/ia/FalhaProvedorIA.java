
package br.com.isaque.jarvis.ia;

public class FalhaProvedorIA extends RuntimeException {

    private final boolean permiteFallback;

    public FalhaProvedorIA(String mensagem) {
        this(mensagem, null, true);
    }

    public FalhaProvedorIA(String mensagem, Throwable causa) {
        this(mensagem, causa, true);
    }

    public FalhaProvedorIA(
            String mensagem,
            boolean permiteFallback) {
        this(mensagem, null, permiteFallback);
    }

    public FalhaProvedorIA(
            String mensagem,
            Throwable causa,
            boolean permiteFallback) {
        super(mensagem, causa);
        this.permiteFallback = permiteFallback;
    }

    public boolean permiteFallback() {
        return permiteFallback;
    }
}
