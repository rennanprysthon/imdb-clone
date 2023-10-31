package br.edu.ifpe.tads.imdb;

import br.edu.ifpe.tads.imdb.entity.Diretor;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

public class DiretorTeste extends Teste {

    @Test
    public void persistirUsuario() {
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
}
