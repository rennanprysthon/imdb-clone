package br.edu.ifpe.tads.imdb.entity;

import br.edu.ifpe.tads.imdb.Teste;
import jakarta.persistence.TypedQuery;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
        Avaliacao avaliacao = entityManager.find(Avaliacao.class, 2L);

        avaliacao.setNota(Double.valueOf("1.5"));
        avaliacao.setResenha("Vi de novo e acho que nao foi tao ruim assim");

        entityManager.clear();
        entityManager.merge(avaliacao);
        entityManager.flush();
        entityManager.clear();

        avaliacao = entityManager.find(Avaliacao.class, 2L);

        assertEquals("Vi de novo e acho que nao foi tao ruim assim", avaliacao.getResenha());
        assertEquals(Double.valueOf("1.5"), avaliacao.getNota());
    }

    @Test
    public void updateAvaliacao() {
        Avaliacao avaliacao = entityManager.find(Avaliacao.class, 2L);

        avaliacao.setNota(Double.valueOf("0.5"));
        avaliacao.setResenha("Estava bebado. Eh ruim mesmo");

        entityManager.flush();
        entityManager.clear();

        avaliacao = entityManager.find(Avaliacao.class, 2L);

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

    @Test
    public void buscarAvaliacoesDeUmFilme() {
        TypedQuery<Avaliacao> query;
        query = entityManager.createQuery("SELECT f.avaliacoes FROM Filme f WHERE f.id = 6", Avaliacao.class);

        List<Avaliacao> avaliacaos = query.getResultList();

        assertEquals(2, avaliacaos.size());
        assertEquals("Ate que foi bom", avaliacaos.get(0).getResenha());
        assertEquals("Na verdade uma obra prima", avaliacaos.get(1).getResenha());
    }

    @Test
    public void listarTodasAvaliacoes() {
        TypedQuery<Object[]> query;
        query = entityManager.createQuery(
            "SELECT a.conta.nome, a.resenha, a.dataAvaliacao FROM Avaliacao a ORDER BY a.dataAvaliacao",
            Object[].class
        );
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        List<Object[]> result = query.getResultList();
        Object[] avaliacao1 = result.get(0);
        assertEquals("Usuario 1, Muito ruim, 01-01-2020", String.format("%s, %s, %s", avaliacao1[0], avaliacao1[1], sdf.format((Date) avaliacao1[2])));
        avaliacao1 = result.get(1);
        assertEquals("Usuario 3, Ate que foi bom, 11-04-2123", String.format("%s, %s, %s", avaliacao1[0], avaliacao1[1], sdf.format((Date) avaliacao1[2])));
        avaliacao1 = result.get(2);
        assertEquals("Usuario 3, Na verdade uma obra prima, 12-04-2123", String.format("%s, %s, %s", avaliacao1[0], avaliacao1[1], sdf.format((Date) avaliacao1[2])));
    }

    @Test
    public void mediaDeNotasDeUsuario() {
        Conta conta = entityManager.find(Conta.class, 8L);

        TypedQuery<Double> query;
        query = entityManager.createQuery(
            "SELECT avg(a.nota) FROM Avaliacao a WHERE a.conta = :conta",
            Double.class
        );
        query.setParameter("conta", conta);

        double result = query.getSingleResult();
        assertEquals(4.0, result, 0);
    }
}