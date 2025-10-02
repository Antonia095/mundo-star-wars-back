package br.com.projeto.mundo_star_wars.service;

import br.com.projeto.mundo_star_wars.exception.EntityNotFoundException;
import br.com.projeto.mundo_star_wars.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AutenticacaoService {

  private final UsuarioRepository usuarioRepository;
  private final PasswordEncoder passwordEncoder;
  private final JWTService jwtService;

  public String login(String email, String senha) {

    log.info("Fazendo login na aplicação com email: {}", email);

    var usuario = usuarioRepository.findByEmail(email)
        .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado!"));

    if (!passwordEncoder.matches(senha, usuario.getSenha())) {
      throw new RuntimeException("Senha inválida!");
    }

    return jwtService.gerarToken(email);
  }

}
