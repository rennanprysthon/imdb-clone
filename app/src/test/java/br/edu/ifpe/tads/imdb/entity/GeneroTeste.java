package br.edu.ifpe.tads.imdb.entity;

import br.edu.ifpe.tads.imdb.Teste;
import br.edu.ifpe.tads.imdb.entity.Genero;
import jakarta.persistence.TypedQuery;
import org.junit.Test;

import java.util.List;

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

    @Test
    public void buscarGeneroPorNome() {
        TypedQuery<Filme> query = entityManager.createNamedQuery(
            "Genero.FilmesPorNome",
            Filme.class
        );

        query.setParameter("nome", "Acao%");
        List<Filme> filmes = query.getResultList();

        assertEquals(1L, filmes.size());
    }
}
