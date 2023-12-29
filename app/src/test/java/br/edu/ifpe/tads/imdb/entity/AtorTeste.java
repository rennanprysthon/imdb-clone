package br.edu.ifpe.tads.imdb.entity;

import br.edu.ifpe.tads.imdb.Teste;
import jakarta.persistence.TypedQuery;
import jakarta.validation.ConstraintViolationException;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class AtorTeste extends Teste {
    private Date getDate(int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day, 0, 0,0);

        return c.getTime();
    }

    @Test
    public void naoPermitePersistirAtorSemNomeArtistico() {
        Ator ator = new Ator();

        ator.setNome("Vin");
        ator.setNomeArtistico("");
        ator.setEmail("vingasosa@email.com");
        ator.setLogin("vingasolina");
        ator.setSenha("12345678911");
        ator.setDataCriacao(getDate(2024, Calendar.JANUARY, 2));
        ator.setCidadeNatal("Americano");
        ator.setDataNascimento(getDate(1991, Calendar.OCTOBER, 12));

        assertThrows(ConstraintViolationException.class, () -> {
            entityManager.persist(ator);
        });
        entityManager.flush();

        assertNotNull(ator.getId());
    }
    @Test
    public void persistirAtor() {
        Ator ator = new Ator();

        ator.setNome("Vin");
        ator.setNomeArtistico("Vin Diesel");
        ator.setEmail("vingasosa@email.com");
        ator.setLogin("vingasolina");
        ator.setSenha("12345678911");
        ator.setDataCriacao(getDate(2024, Calendar.JANUARY, 2));
        ator.setCidadeNatal("Americano");
        ator.setDataNascimento(getDate(1991, Calendar.OCTOBER, 12));

        entityManager.persist(ator);
        entityManager.flush();

        assertNotNull(ator.getId());
    }

    @Test
    public void recuperarAtor() {
        Ator ator = entityManager.find(Ator.class, 2L);

        assertEquals("vin@email.com", ator.getEmail());
        assertEquals("diesel123", ator.getSenha());
        assertEquals("Vin Gasolina", ator.getNome());
        assertEquals("diesel", ator.getLogin());
        assertEquals("1980-10-17", ator.getDataCriacaoFormatada());
        assertEquals("Los Santos", ator.getCidadeNatal());
        assertEquals("americano", ator.getNacionalidade());
    }

    @Test
    public void updateAtorMerge() {
        Ator ator = entityManager.find(Ator.class, 2L);
        ator.setNome("LA LA");

        entityManager.clear();
        entityManager.merge(ator);
        entityManager.flush();
        entityManager.clear();

        ator = entityManager.find(Ator.class, 2L);

        assertEquals("LA LA", ator.getNome());
    }

    @Test
    public void updateAtor() {
        Ator ator = entityManager.find(Ator.class, 2L);

        ator.setCidadeNatal("Olinda");
        ator.setDataNascimento(getDate(2022, Calendar.JANUARY, 1));

        entityManager.flush();
        entityManager.clear();

        ator = entityManager.find(Ator.class, 2L);

        assertEquals("Olinda", ator.getCidadeNatal());
        assertEquals("2022-01-01", ator.getDataNascimentoFormatada());
    }

    @Test
    public void removeAtor() {
        Ator ator = entityManager.find(Ator.class, 3L);

        entityManager.remove(ator);

        ator = entityManager.find(Ator.class, 3L);

        assertNull(ator);
    }

    @Test
    public void consultarAtoresSemFilmes() {
        TypedQuery<Long> query = entityManager.createNamedQuery("Ator.SemFilmes", Long.class);

        long quantidadeAtoresSemFilmes = query.getSingleResult();

        assertEquals(1L, quantidadeAtoresSemFilmes);
    }

    @Test
    public void consultarFilmesDeUmAtor() {
        TypedQuery<String> query;

        query = entityManager.createQuery(
        "SELECT f.titulo FROM Ator a JOIN FETCH a.filmes f ON a MEMBER OF f.atores WHERE a.nome LIKE :termo", String.class
        );
        query.setParameter("termo", "Vin Gasolina");

        List<String> filmes = query.getResultList();
        assertTrue(filmes.containsAll(List.of("Velozes e furiosos", "A volta dos que nao foram: o despertar")));
    }

    @Test
    public void consultarDadosAtor() {
        TypedQuery<Object[]> query;
        query = entityManager.createQuery("SELECT a.nome, count(f.id) FROM Ator a JOIN FETCH a.filmes f ON a MEMBER OF f.atores WHERE a.nome LIKE :termo GROUP BY a.nome", Object[].class);
        query.setParameter("termo", "Vin Gasolina");

        Object[] result = query.getSingleResult();

        assertEquals("Vin Gasolina", result[0]);
        assertEquals(2L, result[1]);
        assertNotNull(result);
    }

}