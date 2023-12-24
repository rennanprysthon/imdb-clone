package br.edu.ifpe.tads.imdb.entity;

import br.edu.ifpe.tads.imdb.Teste;
import static org.junit.Assert.*;

import jakarta.persistence.TypedQuery;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UsuarioTest extends Teste {
    public Date getDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }

    @Test
    public void persistirUsuario() {
        Usuario usuario = new Usuario();
        usuario.setEmail("usuario@email.com");
        usuario.setNome("Usuario");
        usuario.setDataCriacao(getDate(2023, 1, 1));
        usuario.setLogin("usuariologin");
        usuario.setSenha("lala1234");

        entityManager.persist(usuario);
        entityManager.flush();

        assertNotNull(usuario.getId());
    }

    @Test
    public void encontrarUsuario() {
       Usuario usuario = entityManager.find(Usuario.class, 6L);

       assertNotNull(usuario);
       assertEquals("Usuario 1", usuario.getNome());
       assertEquals("usuario1@email.com", usuario.getEmail());
       assertEquals("usuario1", usuario.getLogin());
       assertEquals("2020-12-01", usuario.getDataCriacaoFormatada());
       assertEquals("usuario123", usuario.getSenha());
    }

    @Test
    public void atualizarUsuarioMerge() {
        Usuario usuario = entityManager.find(Usuario.class, 6L);
        usuario.setNome("Usuario editado 1");
        usuario.setEmail("usuario1@hotmail.com");

        entityManager.clear();
        entityManager.merge(usuario);
        entityManager.flush();
        entityManager.clear();

        usuario = entityManager.find(Usuario.class, 6L);

        assertEquals("Usuario editado 1", usuario.getNome());
        assertEquals("usuario1@hotmail.com", usuario.getEmail());
    }

    @Test
    public void atualizarUsuario() {
        Usuario usuario = entityManager.find(Usuario.class, 6L);
        usuario.setNome("Usuario editado 1");
        usuario.setEmail("usuario1@hotmail.com");

        entityManager.flush();

        usuario = entityManager.find(Usuario.class, 6L);

        assertEquals("Usuario editado 1", usuario.getNome());
        assertEquals("usuario1@hotmail.com", usuario.getEmail());
    }

    @Test
    public void excluirUsuario() {
        Usuario usuario = entityManager.find(Usuario.class, 7L);

        entityManager.remove(usuario);

        usuario = entityManager.find(Usuario.class, 7L);

        assertNull(usuario);
    }

    @Test
    public void encontrarUsuariosPeloNome() {
        TypedQuery<Usuario> query;
        query = entityManager.createQuery("SELECT u FROM Usuario u WHERE u.login = :login", Usuario.class);
        query.setParameter("login", "usuario1");

        Usuario result = query.getSingleResult();
        assertNotNull(result);
        assertEquals("Usuario 1", result.getNome());
    }

    @Test
    public void todosFilmesAvaliadosPeloUsuario() {
        Usuario conta = entityManager.find(Usuario.class, 8L);
        TypedQuery<String> query;
        query = entityManager.createQuery("SELECT f.titulo FROM Filme f INNER JOIN Avaliacao a ON f = a.filme WHERE a.conta = :conta", String.class);
        query.setParameter("conta", conta);

        List<String> result = query.getResultList();

        assertEquals(2, result.size());
    }
}