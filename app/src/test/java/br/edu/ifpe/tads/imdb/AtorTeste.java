package br.edu.ifpe.tads.imdb;

import br.edu.ifpe.tads.imdb.entity.Ator;
import br.edu.ifpe.tads.imdb.entity.Diretor;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AtorTeste extends Teste {
    @Test
    public void persistirAtor() {
        Ator ator = new Ator();
        Calendar c = Calendar.getInstance();
        c.set(1991, Calendar.OCTOBER, 12, 0, 0, 0);

        ator.setNome("Vin Gasolina");
        ator.setEmail("vin@diesel.com");
        ator.setCidadeNatal("Americano");
        ator.setDataNascimento(c.getTime());

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
}
