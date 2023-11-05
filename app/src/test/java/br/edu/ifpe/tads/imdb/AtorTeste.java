package br.edu.ifpe.tads.imdb;

import br.edu.ifpe.tads.imdb.entity.Ator;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class AtorTeste extends Teste {
    private Date getDate(int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day, 0, 0,0);

        return c.getTime();
    }
    @Test
    public void persistirAtor() {
        Ator ator = new Ator();

        ator.setNome("Vin Gasolina");
        ator.setEmail("vin@diesel.com");
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
}
