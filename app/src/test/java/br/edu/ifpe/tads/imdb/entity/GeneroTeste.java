package br.edu.ifpe.tads.imdb.entity;

import br.edu.ifpe.tads.imdb.Teste;
import br.edu.ifpe.tads.imdb.entity.Genero;
import jakarta.persistence.TypedQuery;
import jakarta.validation.ConstraintViolationException;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class GeneroTeste extends Teste {
    @Test
    public void naoPermitePersistirGeneroSemNome() {
        Genero genero = new Genero();
        genero.setNome("");

        assertThrows(ConstraintViolationException.class, () -> {
            entityManager.persist(genero);
        });

        entityManager.flush();

        assertNotNull(genero.getId());
    }
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

        query.setParameter("nome", "Comedia%");
        List<Filme> filmes = query.getResultList();

        assertEquals(1L, filmes.size());
    }

    @Test
    public void quantidadeDeFilmesEGeneros() {
        TypedQuery<Object[]> query;
        query = entityManager.createQuery(
                "SELECT g.nome, COUNT(f) " +
                        "FROM Genero g " +
                        "LEFT JOIN g.filmes f " +
                        "GROUP BY g.nome", Object[].class);

        List<Object[]> result = query.getResultList();
        assertEquals("Acao, 2", String.format("%s, %d", result.get(0)[0], result.get(0)[1]));
        assertEquals("Aventura, 3", String.format("%s, %d", result.get(1)[0], result.get(1)[1]));
        assertEquals("Comedia, 1", String.format("%s, %d", result.get(2)[0], result.get(2)[1]));
        assertEquals("Musical, 0", String.format("%s, %d", result.get(3)[0], result.get(3)[1]));
    }
}
