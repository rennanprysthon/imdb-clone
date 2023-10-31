package br.edu.ifpe.tads.imdb;

import br.edu.ifpe.tads.imdb.entity.Ator;
import br.edu.ifpe.tads.imdb.entity.Diretor;
import br.edu.ifpe.tads.imdb.entity.Filme;
import br.edu.ifpe.tads.imdb.entity.Genero;
import org.junit.Test;

import static org.junit.Assert.*;
import java.util.Calendar;
import java.util.Date;

public class FilmeTeste extends Teste {

    private Date getDate(Integer year, Integer month, Integer day) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day, 0, 0, 0);

        return c.getTime();
    }

    @Test
    public void persistirFilme() {
        Ator ator = new Ator();
        ator.setNome("Ator 1");
        ator.setCidadeNatal("Recife");
        ator.setDataNascimento(getDate(1987, Calendar.OCTOBER, 1));
        entityManager.persist(ator);
        entityManager.flush();

        Diretor diretor = new Diretor();
        diretor.setNome("Fulano");
        diretor.setEmail("fulano@email.com");
        diretor.setSenha("batata");
        diretor.setDataCriacao(getDate(1991, Calendar.OCTOBER, 2));
        entityManager.persist(diretor);
        entityManager.flush();

        Genero genero = new Genero();
        genero.setNome("Acao");
        entityManager.persist(genero);
        entityManager.flush();

        Filme filme = new Filme();
        filme.addAtor(ator);
        filme.setDuracao(20L);
        filme.setTitulo("Filme megaboga");
        filme.setDiretor(diretor);
        filme.addGenero(genero);
        entityManager.persist(filme);
        entityManager.flush();

        assertNotNull(filme.getId());
    }

    @Test
    public void recuperarFilme() {
        Filme filme = entityManager.find(Filme.class, 1L);

        assertEquals("Velozes e furiosos", filme.getTitulo());
        assertEquals(140L, filme.getDuracao());
        assertEquals(2, filme.getGenero().size());
    }
}
