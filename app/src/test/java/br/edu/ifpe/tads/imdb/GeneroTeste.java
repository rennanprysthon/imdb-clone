package br.edu.ifpe.tads.imdb;

import br.edu.ifpe.tads.imdb.entity.Genero;
import org.junit.Test;
import static org.junit.Assert.*;

public class GeneroTeste extends Teste {
    @Test
    public void persistirGenero() {
        Genero genero = new Genero();
        genero.setNome("Acao");

        entityManager.persist(genero);
        entityManager.flush();

        assertNotNull(genero.getId());
    }

    @Test
    public void consultar() {
        Genero genero = entityManager.find(Genero.class, 1L);

        assertEquals("Acao", genero.getNome());
    }

    @Test
    public void updateGeneroMerge() {
        Genero genero = entityManager.find(Genero.class, 3L);
        genero.setNome("Comedia Editado");

        entityManager.clear();
        entityManager.merge(genero);
        entityManager.flush();
        entityManager.clear();

        genero = entityManager.find(Genero.class, 3L);

        assertEquals("Comedia Editado", genero.getNome());
    }

    @Test
    public void updateGenero() {
        Genero genero = entityManager.find(Genero.class, 3L);
        genero.setNome("Comedia Editado 2");

        entityManager.flush();
        entityManager.clear();

        genero = entityManager.find(Genero.class, 3L);

        assertEquals("Comedia Editado 2", genero.getNome());
    }

    @Test
    public void removeGenero() {
        Genero genero = entityManager.find(Genero.class, 4L);

        entityManager.remove(genero);

        genero = entityManager.find(Genero.class, 4L);

        assertNull(genero);
    }

    // testar orphan remove no update. Remover um elemento e depois verificar depois do clear e verificar se ele sumiu
}
