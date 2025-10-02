package br.com.projeto.mundo_star_wars.unitaries.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import br.com.projeto.mundo_star_wars.exception.EntityNotFoundException;
import br.com.projeto.mundo_star_wars.model.Usuario;
import br.com.projeto.mundo_star_wars.repository.UsuarioRepository;
import br.com.projeto.mundo_star_wars.service.AutenticacaoService;
import br.com.projeto.mundo_star_wars.service.JWTService;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class AutenticacaoServiceTest {

  @Mock
  private UsuarioRepository usuarioRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Mock
  private JWTService jwtService;

  @InjectMocks
  private AutenticacaoService autenticacaoService;

  @Test
  @DisplayName("Deve retornar token quando as credencias forem válidas")
  void deveRetornarTokenQuandoCredenciaisValidas() {
    var email = "teste@email.com";
    var senha = "123456";

    var usuario = new Usuario();
    usuario.setEmail(email);
    usuario.setSenha("senhaCriptografada");

    when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuario));
    when(passwordEncoder.matches(senha, usuario.getSenha())).thenReturn(true);
    when(jwtService.gerarToken(email)).thenReturn("token-jwt");

    var token = autenticacaoService.login(email, senha);

    assertEquals("token-jwt", token);
  }

  @Test
  @DisplayName("Deve lançar exceção quando o usuário não for encontrado")
  void deveLancarExcecaoQuandoUsuarioNaoForEncontrado() {
    var email = "naoexiste@email.com";
    var senha = "qualquer";

    when(usuarioRepository.findByEmail(email)).thenReturn(Optional.empty());

    var exception = assertThrows(EntityNotFoundException.class, () -> {
      autenticacaoService.login(email, senha);
    });

    assertEquals("Usuário não encontrado!", exception.getMessage());
  }

  @Test
  @DisplayName("Deve lançar exceção quando a senha for inválida")
  void deveLancarExcecaoQuandoSenhaForInvalida() {
    var email = "teste@email.com";
    var senha = "senhaErrada";

    var usuario = new Usuario();
    usuario.setEmail(email);
    usuario.setSenha("senhaCriptografada");

    when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuario));
    when(passwordEncoder.matches(senha, usuario.getSenha())).thenReturn(false);

    var excecao = assertThrows(RuntimeException.class, () -> {
      autenticacaoService.login(email, senha);
    });

    assertEquals("Senha inválida!", excecao.getMessage());
  }

}
