package br.com.projeto.mundo_star_wars.integration.controller;

import static br.com.projeto.mundo_star_wars.enums.TipoUsuario.ADMIN;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.projeto.mundo_star_wars.config.SecurityConfig;
import br.com.projeto.mundo_star_wars.controller.UsuarioController;
import br.com.projeto.mundo_star_wars.dto.UsuarioRequestDto;
import br.com.projeto.mundo_star_wars.dto.UsuarioResponseDto;
import br.com.projeto.mundo_star_wars.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UsuarioController.class)
@Import(SecurityConfig.class)
class UsuarioControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UsuarioService usuarioService;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  @DisplayName("Deve cadastrar usuário administrador com sucesso")
  void deveCadastrarUsuarioAdministradorComSucesso() throws Exception {
    var usuarioDto = new UsuarioRequestDto();
    usuarioDto.setEmail("admin@email.com");
    usuarioDto.setSenha("senha123");

    var responseDto = UsuarioResponseDto.builder()
        .id(1L)
        .email("admin@email.com")
        .tipoUsuario(ADMIN)
        .build();

    when(usuarioService.cadastrarAdministrador(usuarioDto)).thenReturn(responseDto);

    mockMvc.perform(post("/api/usuarios/cadastro")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(usuarioDto))
            .with(csrf()))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.email").value("admin@email.com"))
        .andExpect(jsonPath("$.tipoUsuario").value("ADMIN"));

    verify(usuarioService).cadastrarAdministrador(usuarioDto);
  }
}
