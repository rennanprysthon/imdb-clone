package br.edu.ifpe.tads.imdb.entity;

import br.edu.ifpe.tads.imdb.Teste;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class AvaliacaoTest extends Teste {
    private Date getDate(Integer year, Integer month, Integer day) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day, 0, 0, 0);

        return c.getTime();
    }
    @Test
    public void inserirAvaliacao() {
        Filme filme = entityManager.find(Filme.class, 2L );
        Usuario usuario = entityManager.find(Usuario.class, 6L);

        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setResenha("Bom demais. Obra prima");
        avaliacao.setNota(Double.valueOf("5.0"));
        avaliacao.setDataAvaliacao(getDate(2023, 10, 1));
        avaliacao.setFilme(filme);
        avaliacao.setConta(usuario);

        entityManager.persist(avaliacao);
        entityManager.flush();

        assertNotNull(avaliacao.getId());
    }

    @Test
    public void recuperarAvaliacao() {
        Avaliacao avaliacao = entityManager.find(Avaliacao.class, 1L);

        assertNotNull(avaliacao);
        assertEquals("Muito ruim", avaliacao.getResenha());
        assertEquals(Double.valueOf("1.0"), avaliacao.getNota());
        assertEquals("2020-01-01", avaliacao.getDataAvaliacaoFormatada());
    }

    @Test
    public void updateAvaliacaoMerge() {
        Avaliacao avaliacao = entityManager.find(Avaliacao.class, 1L);

        avaliacao.setNota(Double.valueOf("1.5"));
        avaliacao.setResenha("Vi de novo e acho que nao foi tao ruim assim");

        entityManager.clear();
        entityManager.merge(avaliacao);
        entityManager.flush();
        entityManager.clear();

        avaliacao = entityManager.find(Avaliacao.class, 1L);

        assertEquals("Vi de novo e acho que nao foi tao ruim assim", avaliacao.getResenha());
        assertEquals(Double.valueOf("1.5"), avaliacao.getNota());
    }

    @Test
    public void updateAvaliacao() {
        Avaliacao avaliacao = entityManager.find(Avaliacao.class, 1L);

        avaliacao.setNota(Double.valueOf("0.5"));
        avaliacao.setResenha("Estava bebado. Eh ruim mesmo");

        entityManager.flush();
        entityManager.clear();

        avaliacao = entityManager.find(Avaliacao.class, 1L);

        assertEquals("Estava bebado. Eh ruim mesmo", avaliacao.getResenha());
        assertEquals(Double.valueOf("0.5"), avaliacao.getNota());
    }


    @Test
    public void removerAvaliacao() {
        Avaliacao avaliacao = entityManager.find(Avaliacao.class, 1L);
        Filme filme = entityManager.find(Filme.class, 1L);

        assertEquals(1, filme.getAvaliacoes().size());

        entityManager.remove(avaliacao);
        entityManager.flush();
        entityManager.clear();

        avaliacao = entityManager.find(Avaliacao.class, 1L);
        filme = entityManager.find(Filme.class, 1L);

        assertNull(avaliacao);
        assertEquals(0, filme.getAvaliacoes().size());
    }
}