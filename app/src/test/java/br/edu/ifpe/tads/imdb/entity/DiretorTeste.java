package br.edu.ifpe.tads.imdb.entity;

import br.edu.ifpe.tads.imdb.Teste;
import br.edu.ifpe.tads.imdb.entity.Diretor;
import jakarta.persistence.TypedQuery;
import org.junit.Test;

import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;

public class DiretorTeste extends Teste {

    @Test
    public void persistirDiretor() {
       Calendar c = Calendar.getInstance();
        c.set(1991, Calendar.OCTOBER, 12, 0, 0, 0);
        Diretor diretor = new Diretor();
        diretor.setNome("Fulano");
        diretor.setEmail("fulano@email.com");
        diretor.setSenha("batata");
        diretor.setDataCriacao(c.getTime());

        entityManager.persist(diretor);
        entityManager.flush();

        assertNotNull(diretor.getId());
    }

    @Test
    public void encontrarDiretor() {
        Diretor diretor = entityManager.find(Diretor.class, 1L);

        assertEquals("Fulano Sicraners", diretor.getNome());
        assertEquals("2023-10-17", diretor.getDataCriacaoFormatada());
        assertEquals("sicrano", diretor.getLogin());
        assertEquals("sicrano@email.com", diretor.getEmail());
        assertEquals("teste123", diretor.getSenha());
    }

    @Test
    public void alterarDiretor() {
        Diretor diretor = entityManager.find(Diretor.class, 4L);
        diretor.setNome("Spielberg alterado");

        entityManager.flush();

        Diretor diretorUpdated = entityManager.find(Diretor.class, 4L);

        assertEquals("Spielberg alterado", diretorUpdated.getNome());
    }

    @Test
    public void alterarDiretorMerge() {
        Diretor diretor = entityManager.find(Diretor.class, 4L);
        diretor.setNome("Spielberg alterado de novo");

        entityManager.clear();
        entityManager.merge(diretor);
        entityManager.flush();
        entityManager.clear();

        Diretor diretorUpdated = entityManager.find(Diretor.class, 4L);

        assertEquals("Spielberg alterado de novo", diretorUpdated.getNome());
    }

    @Test
    public void removeDiretor() {
        Diretor diretor = entityManager.find(Diretor.class, 5L);

        entityManager.remove(diretor);

        diretor = entityManager.find(Diretor.class, 5L);

        assertNull(diretor);
    }
}
