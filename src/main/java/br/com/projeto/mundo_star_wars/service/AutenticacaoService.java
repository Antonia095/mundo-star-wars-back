package br.com.projeto.mundo_star_wars.service;

import br.com.projeto.mundo_star_wars.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService {

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JWTService jwtService;

  public String login(String email, String senha) {

    var usuario = usuarioRepository.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

    if (!passwordEncoder.matches(senha, usuario.getSenha())) {
      throw new RuntimeException("Senha inválida!");
    }

    return jwtService.gerarToken(email);
  }

}
