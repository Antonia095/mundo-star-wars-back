package br.com.projeto.mundo_star_wars.integration.controller;

import static java.time.LocalDateTime.now;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.projeto.mundo_star_wars.config.SecurityConfig;
import br.com.projeto.mundo_star_wars.controller.ConteudoController;
import br.com.projeto.mundo_star_wars.dto.ConteudoRequestDto;
import br.com.projeto.mundo_star_wars.dto.ConteudoResponseDto;
import br.com.projeto.mundo_star_wars.exception.GlobalExceptionHandler;
import br.com.projeto.mundo_star_wars.service.ConteudoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ConteudoController.class)
@Import({SecurityConfig.class, GlobalExceptionHandler.class})
class ConteudoControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;
  @MockBean
  private ConteudoService conteudoService;
  private ConteudoRequestDto conteudoRequest;
  private ConteudoResponseDto conteudoResponse;
  private String baseUrl = "/api/conteudos";

  @BeforeEach
  void setUp() {

    conteudoRequest = ConteudoRequestDto.builder()
        .imagem("https://swuniverse.weebly.com/uploads/6/9/3/8/6938918/3408833.jpg")
        .titulo("Jedi")
        .descricao("Um membro da Ordem Jedi, uma organização de guerreiros que protegem a galáxia e mantêm a paz usando a Força.")
        .build();

    conteudoResponse = ConteudoResponseDto.builder()
        .id("507f1f77bcf86cd799439012")
        .imagem("https://swuniverse.weebly.com/uploads/6/9/3/8/6938918/3408833.jpg")
        .titulo("Jedi")
        .descricao("Um membro da Ordem Jedi, uma organização de guerreiros que protegem a galáxia e mantêm a paz usando a Força.")
        .dataCriacao(now())
        .dataAtualizacao(now())
        .build();
  }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    @DisplayName("Deve criar conteúdo com sucesso quando usuário é ADMIN")
    void deveCriarConteudoComSucessoQuandoUsuarioEhAdmin() throws Exception {

    when(conteudoService.criarConteudo(conteudoRequest)).thenReturn(conteudoResponse);

    mockMvc.perform(post(baseUrl)
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(conteudoRequest))
              .with(csrf()))
          .andDo(print())
          .andExpect(status().isCreated())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("$.id").exists())
          .andExpect(jsonPath("$.imagem").value(conteudoRequest.getImagem()))
          .andExpect(jsonPath("$.titulo").value(conteudoRequest.getTitulo()))
          .andExpect(jsonPath("$.descricao").value(conteudoRequest.getDescricao()))
          .andExpect(jsonPath("$.dataCriacao").exists())
          .andExpect(jsonPath("$.dataAtualizacao").exists());

    verify(conteudoService).criarConteudo(conteudoRequest);
    }

  @Test
  @WithMockUser(authorities = "ROLE_PADRAO")
  @DisplayName("Deve retornar 403 quando usuário não é ADMIN")
  void deveRetornar403QuandoUsuarioNaoEhAdmin() throws Exception {

    mockMvc.perform(post(baseUrl)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(conteudoRequest)))
        .andExpect(status().isForbidden());

    verifyNoInteractions(conteudoService);
  }

    @Test
    @WithMockUser(authorities = "ROLE_ADMIN")
    @DisplayName("Deve retornar 400 quando título está vazio")
    void deveRetornar400QuandoTituloEstaVazio() throws Exception {

      var requestInvalido = ConteudoRequestDto.builder()
          .imagem("https://exemplo.com/imagem.jpg")
          .titulo("")
          .descricao("Descrição válida aqui")
          .build();

      mockMvc.perform(post(baseUrl)
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(requestInvalido)))
          .andExpect(status().isBadRequest());

      verifyNoInteractions(conteudoService);
    }

}
