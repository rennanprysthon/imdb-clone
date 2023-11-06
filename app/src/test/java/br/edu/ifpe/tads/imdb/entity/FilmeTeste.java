package br.edu.ifpe.tads.imdb.entity;

import br.edu.ifpe.tads.imdb.Teste;
import org.junit.Test;

import static org.junit.Assert.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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


    @Test
    public void atualizarFilmeMerge() {
        Filme filme = entityManager.find(Filme.class, 5L);
        Genero genero = entityManager.find(Genero.class, 1L);
        assertEquals(1, filme.getGenero().size());

        filme.addGenero(genero);

        entityManager.clear();
        entityManager.merge(filme);
        entityManager.flush();
        entityManager.clear();

        filme = entityManager.find(Filme.class, 5L);
        assertEquals(2, filme.getGenero().size());
    }

    @Test
    public void atualizarFilme() {
        Filme filme = entityManager.find(Filme.class, 5L);

        filme.setTitulo("UM NOVO TITULO");

        entityManager.flush();
        entityManager.clear();

        filme = entityManager.find(Filme.class, 5L);
        assertEquals("UM NOVO TITULO", filme.getTitulo());
    }

    @Test
    public void removerFilme() {
        Filme filme = entityManager.find(Filme.class, 6L);
        Ator ator = entityManager.find(Ator.class, 2L);
        Genero genero1 = entityManager.find(Genero.class, 1L);
        Genero genero2 = entityManager.find(Genero.class, 2L);
        Genero genero3 = entityManager.find(Genero.class, 3L);
        Premiacao premiacao = entityManager.find(Premiacao.class, 4L);
        Avaliacao avaliacao1 = entityManager.find(Avaliacao.class, 2L);
        Avaliacao avaliacao2 = entityManager.find(Avaliacao.class, 3L);

        assertTrue(filme.getGenero().containsAll(List.of(genero1, genero2, genero3)));
        assertTrue(filme.getAvaliacoes().containsAll(List.of(avaliacao1, avaliacao2)));
        assertEquals(premiacao, filme.getPremiacao());
        assertTrue(filme.getAtores().contains(ator));
        assertNotNull(filme);

        entityManager.remove(filme);

        filme = entityManager.find(Filme.class, 6L);
        ator = entityManager.find(Ator.class, 2L);
        genero1 = entityManager.find(Genero.class, 1L);
        genero2 = entityManager.find(Genero.class, 2L);
        genero3 = entityManager.find(Genero.class, 3L);
        premiacao = entityManager.find(Premiacao.class, 4L);
        avaliacao1 = entityManager.find(Avaliacao.class, 2L);
        avaliacao2 = entityManager.find(Avaliacao.class, 3L);

        assertNull(filme);
        assertNotNull(ator);
        assertNotNull(genero1);
        assertNotNull(genero2);
        assertNotNull(genero3);
        assertNull(premiacao);
        assertNull(avaliacao1);
        assertNull(avaliacao2);
    }
}
