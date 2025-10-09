package br.com.projeto.mundo_star_wars.unitaries.service;

import static br.com.projeto.mundo_star_wars.enums.TipoUsuario.ADMIN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.projeto.mundo_star_wars.dto.UsuarioRequestDto;
import br.com.projeto.mundo_star_wars.dto.UsuarioResponseDto;
import br.com.projeto.mundo_star_wars.exception.BusinessException;
import br.com.projeto.mundo_star_wars.mapper.UsuarioMapper;
import br.com.projeto.mundo_star_wars.model.Usuario;
import br.com.projeto.mundo_star_wars.repository.UsuarioRepository;
import br.com.projeto.mundo_star_wars.service.UsuarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

  @Mock
  private UsuarioRepository usuarioRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Mock
  private UsuarioMapper mapper;

  @InjectMocks
  private UsuarioService usuarioService;

  @Test
  @DisplayName("Deve cadastrar usuário administrador com sucesso")
  void deveCadastrarUsuarioAdministradorComSucesso() {

    var idUsuario = "1";
    var senhaCriptografada = "senhaCriptografada";
    var tipoUsuario = ADMIN;

    var request = new UsuarioRequestDto();
    request.setEmail("admin@email.com");
    request.setSenha("senha123");

    var usuario = new Usuario();
    usuario.setId(idUsuario);
    usuario.setEmail(request.getEmail());
    usuario.setSenha(senhaCriptografada);
    usuario.setTipoUsuario(tipoUsuario);

    var usuarioSalvo = new Usuario();
    usuarioSalvo.setEmail(request.getEmail());
    usuarioSalvo.setSenha(senhaCriptografada);
    usuarioSalvo.setTipoUsuario(ADMIN);

    var responseDto = UsuarioResponseDto
        .builder()
        .id(idUsuario)
        .email(request.getEmail())
        .tipoUsuario(tipoUsuario)
        .build();

    when(usuarioRepository.existsByEmail(request.getEmail())).thenReturn(false);
    when(passwordEncoder.encode(anyString())).thenReturn("senhaCriptografada");
    when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioSalvo);
    when(mapper.toUsuarioResponse(usuarioSalvo)).thenReturn(responseDto);

    var resultado = usuarioService.cadastrarAdministrador(request);

    assertEquals(request.getEmail(), resultado.getEmail());
    verify(usuarioRepository).save(any(Usuario.class));
  }

  @Test
  @DisplayName("Deve lançar exceção quando email já estiver cadastrado")
  void deveLancarExcecaoQuandoEmailJaCadastrado() {
    var request = new UsuarioRequestDto();
    request.setEmail("admin@email.com");
    request.setSenha("senha123");

    when(usuarioRepository.existsByEmail(request.getEmail())).thenReturn(true);

    assertThrows(BusinessException.class, () -> usuarioService.cadastrarAdministrador(request));
    verify(usuarioRepository, never()).save(any());
  }

}
