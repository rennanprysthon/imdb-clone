package br.edu.ifpe.tads.imdb.entity;

import br.edu.ifpe.tads.imdb.Teste;
import jakarta.persistence.Query;
import jakarta.persistence.TemporalType;
import jakarta.persistence.TypedQuery;
import org.junit.Test;


import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
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
        Diretor diretor = entityManager.find(Diretor.class, 1L);
        Genero genero1 = entityManager.find(Genero.class, 1L);
        Genero genero2 = entityManager.find(Genero.class, 2L);
        Ator ator = entityManager.find(Ator.class, 2L);

        assertEquals("Velozes e furiosos", filme.getTitulo());
        assertEquals(140L, filme.getDuracao());
        assertEquals(2, filme.getGenero().size());
        assertEquals("Melhores efeitos especiais", filme.getPremiacao().getCategoria());
        assertEquals(TipoPremiacao.OSCAR, filme.getPremiacao().getTipoPremiacao());
        assertTrue(filme.getAtores().contains(ator));
        assertTrue(filme.getGenero().containsAll(List.of(genero1, genero2)));
        assertEquals(diretor, filme.getDiretor());
        assertTrue(filme.getNomesCreditos().containsAll(
            List.of(
                "Fulano 1/cinegrafista",
                "Fulano 2/cinegrafista",
                "Fulano 3/cafezinho"
            )
        ));
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

    @Test
    public void filmeMaisAntigoEMaisNovo() {
        Query query = entityManager.createQuery(
                "SELECT MAX(f.dataLancamento), MIN(f.dataLancamento) FROM Filme f");

        Object[] resultado = (Object[]) query.getSingleResult();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String maiorData = dateFormat.format((Date) resultado[0]);
        String menorData = dateFormat.format((Date) resultado[1]);

        assertEquals("01-01-2091", maiorData);
        assertEquals("01-01-2000", menorData);
    }

    @Test
    public void recuperarFilmesEMedias() {
        TypedQuery<Object[]> query;
        query = entityManager.createQuery(
            "SELECT f.titulo, avg(a.nota) FROM Filme f INNER JOIN Avaliacao a ON a member of f.avaliacoes GROUP BY f.titulo", Object[].class
        );
        List<Object[]> filmes = query.getResultList();
        assertEquals(2, filmes.size());
        assertEquals("A volta dos que nao foram: o despertar:4.0", filmes.get(0)[0] + ":" + filmes.get(0)[1]);
        assertEquals("Velozes e furiosos:1.0", filmes.get(1)[0] + ":" + filmes.get(1)[1]);
    }

   @Test
   public void recuperarFilmesDeUmRange() {
       TypedQuery<Filme> query = entityManager.createQuery("SELECT f FROM Filme f WHERE f.dataLancamento BETWEEN ?1 AND ?2 ORDER BY f.dataLancamento", Filme.class);
       query.setParameter(1, getDate(2091, 0, 1));
       query.setParameter(2, getDate(2091, 11, 30));

       List<Filme> filme = query.getResultList();

       assertEquals(3, filme.size());
   }

   @Test
    public void recuperarFilmesQueTenhamMaisAvaliacoes() {
       TypedQuery<Filme> query = entityManager.createQuery("SELECT f FROM Filme f WHERE f.avaliacoes IS NOT EMPTY", Filme.class);
       List<Filme> filmes = query.getResultList();

       assertEquals(2, filmes.size());
}

    @Test
    public void recuperarFilmesQueNaoTenhamAvaliacoes() {
        TypedQuery<Filme> query = entityManager.createQuery("SELECT f FROM Filme f WHERE f.avaliacoes IS EMPTY", Filme.class);
        List<Filme> filmes = query.getResultList();

        assertEquals(4, filmes.size());
    }

    @Test
    public void recuperaFilmesEDiretores() {
        TypedQuery<Object[]> query;
        query = entityManager.createQuery("SELECT d.nome, f.titulo FROM Filme f LEFT JOIN Diretor d ON f.diretor = d", Object[].class);

        List<Object[]> result = query.getResultList();
        assertEquals(6, result.size());
        assertEquals("Diretor 1:Velozes e furiosos", result.get(0)[0] + ":" + result.get(0)[1]);
    }

    @Test
    public void recuperarQuantidadeDeFilmesPorAno() {
       TypedQuery<Long> query;
       query = entityManager.createQuery("SELECT count(f.id) FROM Filme f WHERE f.dataLancamento BETWEEN :dataInicio AND :dataFim", Long.class);

       query.setParameter("dataInicio", getDate(2091, 0, 1), TemporalType.DATE);
       query.setParameter("dataFim", getDate(2091, 11, 30), TemporalType.DATE);
       long result = query.getSingleResult();

       assertEquals(3, result);
    }

    @Test
    public void buscarFilmePorTermo() {
        TypedQuery<Filme> query;
        query = entityManager.createQuery("SELECT f FROM Filme f WHERE f.titulo LIKE :termo", Filme.class);

        query.setParameter("termo", "%Velozes%");

        List<Filme> result = query.getResultList();
        assertEquals(2, result.size());
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
        // assertEquals("Acao, 2", String.format("%s, %d", result.get(0)[0], result.get(0)[1])); Esse filme eh excluido em um dos testes acima
        assertEquals("Aventura, 3", String.format("%s, %d", result.get(1)[0], result.get(1)[1]));
        assertEquals("Comedia, 1", String.format("%s, %d", result.get(2)[0], result.get(2)[1]));
        assertEquals("Musical, 0", String.format("%s, %d", result.get(3)[0], result.get(3)[1]));
    }
}
