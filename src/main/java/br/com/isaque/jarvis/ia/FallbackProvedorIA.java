package br.com.isaque.jarvis.ia;

public class FallbackProvedorIA implements ProvedorIA {

    private final ProvedorIA principal;
    private final ProvedorIA alternativa;

    public FallbackProvedorIA(
            ProvedorIA principal,
            ProvedorIA alternativa) {

        this.principal = principal;
        this.alternativa = alternativa;
    }

    @Override
    public String perguntar(String pergunta) {
        try {
            return principal.perguntar(pergunta);

        } catch (FalhaProvedorIA erroPrincipal) {
            try {
                return alternativa.perguntar(pergunta);

            } catch (FalhaProvedorIA erroAlternativo) {
                throw new FalhaProvedorIA(
                        "Os provedores de IA estão indisponíveis.",
                        erroAlternativo
                );
            }
        }
    }
}