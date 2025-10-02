package br.com.projeto.mundo_star_wars.service;

import static br.com.projeto.mundo_star_wars.enums.TipoUsuario.ADMIN;

import br.com.projeto.mundo_star_wars.dto.UsuarioRequestDto;
import br.com.projeto.mundo_star_wars.dto.UsuarioResponseDto;
import br.com.projeto.mundo_star_wars.exception.BusinessException;
import br.com.projeto.mundo_star_wars.mapper.UsuarioMapper;
import br.com.projeto.mundo_star_wars.model.Usuario;
import br.com.projeto.mundo_star_wars.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioService {

  private final UsuarioRepository usuarioRepository;
  private final PasswordEncoder passwordEncoder;
  private final UsuarioMapper mapper;

  public UsuarioResponseDto cadastrarAdministrador(UsuarioRequestDto usuarioRequest) {

    log.info("Cadastrando usuário administrador: {}", usuarioRequest.getEmail());

    if (usuarioRepository.existsByEmail(usuarioRequest.getEmail())) {
      throw new BusinessException("Email já cadastrado!");
    }

    var usuario = new Usuario();
    usuario.setEmail(usuarioRequest.getEmail());
    usuario.setSenha(passwordEncoder.encode(usuarioRequest.getSenha()));
    usuario.setTipoUsuario(ADMIN);

    var usuarioSalvo = usuarioRepository.save(usuario);

    return mapper.toUsuarioResponse(usuarioSalvo);
  }
}
