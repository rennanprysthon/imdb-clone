package br.edu.ifpe.tads.imdb.entity;

import br.edu.ifpe.tads.imdb.Teste;
import static org.junit.Assert.*;
import org.junit.Test;

public class PremiacaoTest extends Teste {

    @Test
    public void persistirPremiacao() {
        Filme filme = entityManager.find(Filme.class, 1L);

        Premiacao premiacao = new Premiacao();
        premiacao.setCategoria("Melhores efeitos especiais");
        premiacao.setTipoPremiacao(TipoPremiacao.GLOBO_DE_OURO);

        filme.setPremiacao(premiacao);

        entityManager.persist(premiacao);

        filme = entityManager.find(Filme.class, 1L);

        assertEquals("Melhores efeitos especiais", filme.getPremiacao().getCategoria());
        assertEquals(TipoPremiacao.GLOBO_DE_OURO, filme.getPremiacao().getTipoPremiacao());
    }

    @Test
    public void procurarPremiacao() {
        Premiacao premiacao = entityManager.find(Premiacao.class, 1L);

        assertNotNull(premiacao);
        assertEquals("Melhor filme", premiacao.getCategoria());
        assertEquals(TipoPremiacao.OSCAR, premiacao.getTipoPremiacao());
    }

    @Test
    public void updatePremiacaoMerge() {
        Premiacao premiacao = entityManager.find(Premiacao.class, 2L);

        premiacao.setCategoria("Pior filme");

        entityManager.clear();
        entityManager.merge(premiacao);
        entityManager.flush();
        entityManager.clear();

        premiacao = entityManager.find(Premiacao.class, 2L);

        assertNotNull(premiacao);
        assertEquals("Pior filme", premiacao.getCategoria());
    }

    @Test
    public void updatePremiacao() {
        Premiacao premiacao = entityManager.find(Premiacao.class, 2L);

        premiacao.setCategoria("Pior filme");

        premiacao = entityManager.find(Premiacao.class, 2L);

        assertNotNull(premiacao);
        assertEquals("Pior filme", premiacao.getCategoria());
    }

    @Test
    public void removerPremiacao() {
        Premiacao premiacao = entityManager.find(Premiacao.class, 3L);

        entityManager.remove(premiacao);

        premiacao = entityManager.find(Premiacao.class, 3L);

        assertNull(premiacao);
    }
}