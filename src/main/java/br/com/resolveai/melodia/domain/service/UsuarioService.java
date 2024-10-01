package br.com.resolveai.melodia.domain.service;

import br.com.resolveai.melodia.domain.exception.*;
import br.com.resolveai.melodia.domain.model.HistoricoEstadoEmocionalUsuario;
import br.com.resolveai.melodia.domain.model.Usuario;
import br.com.resolveai.melodia.domain.model.enums.EstadoEmocional;
import br.com.resolveai.melodia.domain.model.enums.Plano;
import br.com.resolveai.melodia.domain.repository.HistoricoEstadoEmocionalUsuarioRepository;
import br.com.resolveai.melodia.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final HistoricoEstadoEmocionalUsuarioRepository historicoEstadoEmocionalUsuarioRepository;

    public Page<Usuario> listarTodosUsuariosPaginados(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }
    public Usuario buscarUsuarioPorId(Long usuarioId) {
        return usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
    }

    @Transactional
    public Usuario salvarUsuario(Usuario usuario) {
        verificarEmailDuplicado(usuario);
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario alterarPlano(Long usuarioId, String novoPlanoStr) {
        Plano novoPlano;

        try {
            novoPlano = Plano.valueOf(novoPlanoStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new PlanoInvalidoException(novoPlanoStr);
        }

        Usuario usuario = buscarUsuarioPorId(usuarioId);
        usuario.setPlano(novoPlano);
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario alterarEstadoEmocional(Long usuarioId, String estadoEmocionalStr) {
        EstadoEmocional novoEstadoEmocional;

        try {
            novoEstadoEmocional = EstadoEmocional.valueOf(estadoEmocionalStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new EstadoEmocionalInvalidoException(estadoEmocionalStr);
        }

        Usuario usuario = buscarUsuarioPorId(usuarioId);
        usuario.alterarEstadoEmocional(novoEstadoEmocional);
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void seguirUsuario(Long usuarioId, Long seguirId) {
        Usuario usuario = buscarUsuarioPorId(usuarioId);
        Usuario usuarioSeguir = buscarUsuarioPorId(seguirId);

        usuario.seguirUsuario(usuarioSeguir);
        usuarioRepository.saveAll(List.of(usuario, usuarioSeguir));
    }

    @Transactional
    public void deixarDeSeguirUsuario(Long usuarioId, Long deixarDeSeguirId) {
        Usuario usuario = buscarUsuarioPorId(usuarioId);
        Usuario usuarioDeixarDeSeguir = buscarUsuarioPorId(deixarDeSeguirId);

        usuario.deixarDeSeguirUsuario(usuarioDeixarDeSeguir);
        usuarioRepository.saveAll(List.of(usuario, usuarioDeixarDeSeguir));
    }

    @Transactional
    public void removerUsuarioPorId(Long usuarioId) {
        Usuario usuario = buscarUsuarioPorId(usuarioId);

        try {
            usuarioRepository.delete(usuario);
        } catch (DataIntegrityViolationException e) {
            throw new UsuarioEmUsoException(usuarioId);
        }
    }

    public EstadoEmocional obterEstadoEmocionalAtual(Long usuarioId) {
        return historicoEstadoEmocionalUsuarioRepository.findTopByUsuarioIdOrderByDataDesc(usuarioId)
            .map(HistoricoEstadoEmocionalUsuario::getEstadoEmocional)
            .orElse(null);
    }

    private void verificarEmailDuplicado(Usuario usuario) {
        boolean emailExistente = usuarioRepository.findByEmail(usuario.getEmail())
            .stream()
            .anyMatch(usuarioExistente -> !usuarioExistente.equals(usuario));

        if (emailExistente) {
            throw new EmailJaCadastradoException(usuario.getEmail());
        }
    }

}
