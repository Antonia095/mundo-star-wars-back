package br.com.projeto.mundo_star_wars.integration.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.projeto.mundo_star_wars.config.SecurityConfig;
import br.com.projeto.mundo_star_wars.controller.AutenticacaoController;
import br.com.projeto.mundo_star_wars.dto.UsuarioRequestDto;
import br.com.projeto.mundo_star_wars.service.AutenticacaoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AutenticacaoController.class)
@Import(SecurityConfig.class)
class AutenticacaoControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private AutenticacaoService autenticacaoService;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  @DisplayName("Deve retornar token quando login estiver válido")
  void deveRetornarTokenQuandoLoginValido() throws Exception {
    var usuarioDto = new UsuarioRequestDto();
    usuarioDto.setEmail("admin@email.com");
    usuarioDto.setSenha("senha123");

    var tokenEsperado = "token-jwt";

    when(autenticacaoService.login(usuarioDto.getEmail(), usuarioDto.getSenha()))
        .thenReturn(tokenEsperado);

    mockMvc.perform(post("/api/autenticacao/login")
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(usuarioDto))
            .with(csrf()))
        .andExpect(status().isOk())
        .andExpect(content().string(tokenEsperado));

    verify(autenticacaoService).login(usuarioDto.getEmail(), usuarioDto.getSenha());
  }
}
