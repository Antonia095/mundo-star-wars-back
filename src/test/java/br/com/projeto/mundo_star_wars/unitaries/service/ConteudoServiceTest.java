package br.com.projeto.mundo_star_wars.unitaries.service;

import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.projeto.mundo_star_wars.dto.ConteudoRequestDto;
import br.com.projeto.mundo_star_wars.dto.ConteudoResponseDto;
import br.com.projeto.mundo_star_wars.exception.BusinessException;
import br.com.projeto.mundo_star_wars.exception.EntityNotFoundException;
import br.com.projeto.mundo_star_wars.mapper.ConteudoMapper;
import br.com.projeto.mundo_star_wars.model.Conteudo;
import br.com.projeto.mundo_star_wars.repository.ConteudoRepository;
import br.com.projeto.mundo_star_wars.service.ConteudoService;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class ConteudoServiceTest {

  @Mock
  private ConteudoRepository conteudoRepository;

  @Mock
  private ConteudoMapper mapper;

  @InjectMocks
  private ConteudoService conteudoService;

  private ConteudoRequestDto conteudoRequestDto;
  private ConteudoResponseDto conteudoResponseDto;
  private Conteudo conteudo;
  private String conteudoId;

  @BeforeEach
  void setUp() {
    conteudoId = "507f1f77bcf86cd799439011";

    conteudoRequestDto = new ConteudoRequestDto();
    conteudoRequestDto.setTitulo("Yoda");
    conteudoRequestDto.setDescricao("Mestre Jedi verde e sábio");
    conteudoRequestDto.setImagem("https://exemplo.com/yoda.jpg");

    conteudo = new Conteudo();
    conteudo.setId(conteudoId);
    conteudo.setTitulo("Yoda");
    conteudo.setDescricao("Mestre Jedi verde e sábio");
    conteudo.setImagem("https://exemplo.com/yoda.jpg");
    conteudo.setDataCriacao(now());
    conteudo.setDataAtualizacao(now());

    conteudoResponseDto = ConteudoResponseDto.builder()
        .id(conteudoId)
        .titulo("Yoda")
        .descricao("Mestre Jedi verde e sábio")
        .imagem("https://exemplo.com/yoda.jpg")
        .dataCriacao(now())
        .dataAtualizacao(now())
        .build();
  }

  @Test
  @DisplayName("Deve criar conteúdo com sucesso")
  void deveCriarConteudoComSucesso() {

      when(conteudoRepository.existsByTitulo(conteudoRequestDto.getTitulo()))
          .thenReturn(false);
      when(mapper.toEntity(conteudoRequestDto))
          .thenReturn(conteudo);
      when(conteudoRepository.save(conteudo))
          .thenReturn(conteudo);
      when(mapper.toConteudoResponse(conteudo))
          .thenReturn(conteudoResponseDto);

      var resultado = conteudoService.criarConteudo(conteudoRequestDto);

      assertNotNull(resultado);
      assertEquals(conteudoResponseDto.getTitulo(), resultado.getTitulo());
      assertEquals(conteudoResponseDto.getDescricao(), resultado.getDescricao());

      verify(conteudoRepository).existsByTitulo(conteudoRequestDto.getTitulo());
      verify(mapper).toEntity(conteudoRequestDto);
      verify(conteudoRepository).save(conteudo);
      verify(mapper).toConteudoResponse(conteudo);
    }

  @Test
  @DisplayName("Deve lançar exceção quando título já existe")
  void deveLancarExcecaoQuandoTituloJaExiste() {

    when(conteudoRepository.existsByTitulo(conteudoRequestDto.getTitulo()))
        .thenReturn(true);

    var exception = assertThrows(
        BusinessException.class,
        () -> conteudoService.criarConteudo(conteudoRequestDto)
    );

    assertEquals("Já existe um conteúdo com este título", exception.getMessage());

    verify(conteudoRepository).existsByTitulo(conteudoRequestDto.getTitulo());
    verify(mapper, never()).toEntity(any());
    verify(conteudoRepository, never()).save(any());
  }

 @Test
  @DisplayName("Deve buscar conteúdo por ID com sucesso")
  void deveBuscarConteudoPorIdComSucesso() {

    when(conteudoRepository.findById(conteudoId))
        .thenReturn(Optional.of(conteudo));
    when(mapper.toConteudoResponse(conteudo))
        .thenReturn(conteudoResponseDto);

    var resultado = conteudoService.buscarConteudoPorId(conteudoId);

    assertNotNull(resultado);
    assertEquals(conteudoId, resultado.getId());
    assertEquals(conteudoResponseDto.getTitulo(), resultado.getTitulo());

    verify(conteudoRepository).findById(conteudoId);
    verify(mapper).toConteudoResponse(conteudo);
  }

  @Test
  @DisplayName("Deve lançar exceção quando conteúdo não encontrado")
  void deveLancarExcecaoQuandoConteudoNaoEncontrado() {

    when(conteudoRepository.findById(conteudoId))
        .thenReturn(Optional.empty());

    var exception = assertThrows(
        EntityNotFoundException.class,
        () -> conteudoService.buscarConteudoPorId(conteudoId)
    );

    assertEquals("Conteúdo não encontrado com ID: " + conteudoId, exception.getMessage());

    verify(conteudoRepository).findById(conteudoId);
    verify(mapper, never()).toConteudoResponse(any());
  }

  @Test
  @DisplayName("Deve listar todos os conteúdos com paginação")
  void deveListarTodosConteudosComPaginacao() {

    var pageable = PageRequest.of(0, 10);
    var conteudos = Arrays.asList(conteudo);
    var conteudosPage = new PageImpl<>(conteudos, pageable, 1);
    var responseDTOs = Arrays.asList(conteudoResponseDto);

    when(conteudoRepository.findAll(pageable))
        .thenReturn(conteudosPage);
    when(mapper.toResponseList(conteudos))
        .thenReturn(responseDTOs);

    var resultado = conteudoService.listarTodos(pageable);

    assertNotNull(resultado);
    assertEquals(1, resultado.getTotalElements());
    assertEquals(1, resultado.getContent().size());
    assertEquals(conteudoResponseDto.getTitulo(), resultado.getContent().get(0).getTitulo());

    verify(conteudoRepository).findAll(pageable);
    verify(mapper).toResponseList(conteudos);
  }

  @Test
  @DisplayName("Deve retornar página vazia quando não há conteúdos")
  void deveRetornarPaginaVaziaQuandoNaoHaConteudos() {

    var pageable = PageRequest.of(0, 10);
    Page<Conteudo> conteudosPage = new PageImpl<>(Arrays.asList(), pageable, 0);
    List<ConteudoResponseDto> responseDTOs = Arrays.asList();

    when(conteudoRepository.findAll(pageable))
        .thenReturn(conteudosPage);
    when(mapper.toResponseList(anyList()))
        .thenReturn(responseDTOs);

    var resultado = conteudoService.listarTodos(pageable);

    assertNotNull(resultado);
    assertEquals(0, resultado.getTotalElements());
    assertTrue(resultado.getContent().isEmpty());

    verify(conteudoRepository).findAll(pageable);
    verify(mapper).toResponseList(anyList());
  }

  @Test
  @DisplayName("Deve atualizar conteúdo com sucesso")
  void deveAtualizarConteudoComSucesso() {

    var requestAtualizado = new ConteudoRequestDto();
    requestAtualizado.setTitulo("Yoda Atualizado");
    requestAtualizado.setDescricao("Descrição atualizada");
    requestAtualizado.setImagem("https://exemplo.com/yoda-novo.jpg");

    when(conteudoRepository.findById(conteudoId))
        .thenReturn(Optional.of(conteudo));
    when(conteudoRepository.existsByTitulo(requestAtualizado.getTitulo()))
        .thenReturn(false);
    when(conteudoRepository.save(conteudo))
        .thenReturn(conteudo);
    when(mapper.toConteudoResponse(conteudo))
        .thenReturn(conteudoResponseDto);

    var resultado = conteudoService.atualizarConteudo(conteudoId, requestAtualizado);

    assertNotNull(resultado);
    assertEquals(conteudoResponseDto.getId(), resultado.getId());

    verify(conteudoRepository).findById(conteudoId);
    verify(conteudoRepository).existsByTitulo(requestAtualizado.getTitulo());
    verify(mapper).updateEntity(requestAtualizado, conteudo);
    verify(conteudoRepository).save(conteudo);
    verify(mapper).toConteudoResponse(conteudo);
  }

  @Test
  @DisplayName("Deve atualizar conteúdo mantendo o mesmo título")
  void deveAtualizarConteudoMantendoMesmoTitulo() {

    when(conteudoRepository.findById(conteudoId))
        .thenReturn(Optional.of(conteudo));
    when(conteudoRepository.save(conteudo))
        .thenReturn(conteudo);
    when(mapper.toConteudoResponse(conteudo))
        .thenReturn(conteudoResponseDto);

    var resultado = conteudoService.atualizarConteudo(conteudoId, conteudoRequestDto);

    assertNotNull(resultado);

    verify(conteudoRepository).findById(conteudoId);
    verify(conteudoRepository, never()).existsByTitulo(any());
    verify(mapper).updateEntity(conteudoRequestDto, conteudo);
    verify(conteudoRepository).save(conteudo);
  }

  @Test
  @DisplayName("Deve lançar exceção quando conteúdo não encontrado para atualização")
  void deveLancarExcecaoQuandoConteudoNaoEncontradoParaAtualizacao() {

    when(conteudoRepository.findById(conteudoId))
        .thenReturn(Optional.empty());

    var exception = assertThrows(
        EntityNotFoundException.class,
        () -> conteudoService.atualizarConteudo(conteudoId, conteudoRequestDto)
    );

    assertEquals("Conteúdo não encontrado com ID: " + conteudoId, exception.getMessage());

    verify(conteudoRepository).findById(conteudoId);
    verify(conteudoRepository, never()).existsByTitulo(any());
    verify(mapper, never()).updateEntity(any(), any());
  }

  @Test
  @DisplayName("Deve lançar exceção quando novo título já existe")
  void deveLancarExcecaoQuandoNovoTituloJaExiste() {

    var requestComTituloExistente = new ConteudoRequestDto();
    requestComTituloExistente.setTitulo("Titulo Existente");
    requestComTituloExistente.setDescricao("Nova descrição");
    requestComTituloExistente.setImagem("https://exemplo.com/nova-imagem.jpg");

    when(conteudoRepository.findById(conteudoId))
        .thenReturn(Optional.of(conteudo));
    when(conteudoRepository.existsByTitulo(requestComTituloExistente.getTitulo()))
        .thenReturn(true);

    var exception = assertThrows(
        BusinessException.class,
        () -> conteudoService.atualizarConteudo(conteudoId, requestComTituloExistente)
    );

    assertEquals("Já existe um conteúdo com este título", exception.getMessage());

    verify(conteudoRepository).findById(conteudoId);
    verify(conteudoRepository).existsByTitulo(requestComTituloExistente.getTitulo());
    verify(mapper, never()).updateEntity(any(), any());
  }

  @Test
  @DisplayName("Deve excluir conteúdo com sucesso")
  void deveExcluirConteudoComSucesso() {

    when(conteudoRepository.existsById(conteudoId))
        .thenReturn(true);

    assertDoesNotThrow(() -> conteudoService.excluirConteudo(conteudoId));

    verify(conteudoRepository).existsById(conteudoId);
    verify(conteudoRepository).deleteById(conteudoId);
  }

  @Test
  @DisplayName("Deve lançar exceção quando conteúdo não encontrado para exclusão")
  void deveLancarExcecaoQuandoConteudoNaoEncontradoParaExclusao() {

    when(conteudoRepository.existsById(conteudoId))
        .thenReturn(false);

    var exception = assertThrows(
        EntityNotFoundException.class,
        () -> conteudoService.excluirConteudo(conteudoId)
    );

    assertEquals("Conteúdo não encontrado com ID: " + conteudoId, exception.getMessage());

    verify(conteudoRepository).existsById(conteudoId);
    verify(conteudoRepository, never()).deleteById(any());
  }

  @Test
  @DisplayName("Método privado buscarPorId deve ser testado indiretamente via excluirConteudo")
  void metodoPrivadoBuscarPorIdDeveSerTestadoIndiretamente() {

    when(conteudoRepository.existsById(conteudoId))
        .thenReturn(false);

    var exception = assertThrows(
        EntityNotFoundException.class,
        () -> conteudoService.excluirConteudo(conteudoId)
    );

    assertEquals("Conteúdo não encontrado com ID: " + conteudoId, exception.getMessage());
    verify(conteudoRepository).existsById(conteudoId);
  }

}
