package br.com.projeto.mundo_star_wars.controller;

import static org.springframework.http.HttpStatus.CREATED;

import br.com.projeto.mundo_star_wars.dto.ConteudoRequestDto;
import br.com.projeto.mundo_star_wars.dto.ConteudoResponseDto;
import br.com.projeto.mundo_star_wars.service.ConteudoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/conteudos")
@RequiredArgsConstructor
@Tag(name = "Conteúdos", description = "Operações relacionadas ao gerenciamento de conteúdos")
public class ConteudoController {

  private final ConteudoService conteudoService;

  @PostMapping
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  @Operation(summary = "Criar novo conteúdo", description = "Cria um novo conteúdo")
  @ApiResponse(responseCode = "201", description = "Conteúdo criado com sucesso")
  @ApiResponse(responseCode = "400", description = "Dados inválidos")
  @ApiResponse(responseCode = "403", description = "Acesso negado - apenas ADMIN")
  public ResponseEntity<ConteudoResponseDto> criarConteudo(
      @Valid @RequestBody ConteudoRequestDto conteudoRequest) {

    var responseDTO = conteudoService.criarConteudo(conteudoRequest);
    return ResponseEntity.status(CREATED).body(responseDTO);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Buscar conteúdo por ID", description = "Busca um conteúdo específico pelo ID")
  @ApiResponse(responseCode = "200", description = "Conteúdo encontrado")
  @ApiResponse(responseCode = "404", description = "Conteúdo não encontrado")
  public ResponseEntity<ConteudoResponseDto> buscarPorId(
      @Parameter(description = "ID do conteúdo") @PathVariable String id) {

    return ResponseEntity.ok(conteudoService.buscarConteudoPorId(id));
  }

  @GetMapping
  @Operation(summary = "Listar todos os conteúdos", description = "Lista todos os conteúdos com paginação")
  @ApiResponse(responseCode = "200", description = "Lista de conteúdos")
  public ResponseEntity<Page<ConteudoResponseDto>> listarTodos(
      @Parameter(description = "Número da página") @RequestParam(defaultValue = "0") int page,
      @Parameter(description = "Tamanho da página") @RequestParam(defaultValue = "10") int size,
      @Parameter(description = "Campo para ordenação") @RequestParam(defaultValue = "dataCriacao") String sortBy,
      @Parameter(description = "Direção da ordenação") @RequestParam(defaultValue = "desc") String sortDir) {

    var sort = sortDir.equalsIgnoreCase("desc") ?
        Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

    var pageable = PageRequest.of(page, size, sort);
    var conteudos = conteudoService.listarTodos(pageable);

    return ResponseEntity.ok(conteudos);
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  @Operation(summary = "Atualizar conteúdo", description = "Atualiza um conteúdo existente")
  @ApiResponse(responseCode = "200", description = "Conteúdo atualizado com sucesso")
  @ApiResponse(responseCode = "400", description = "Dados inválidos")
  @ApiResponse(responseCode = "403", description = "Acesso negado - apenas ADMIN")
  @ApiResponse(responseCode = "404", description = "Conteúdo não encontrado")
  public ResponseEntity<ConteudoResponseDto> atualizarConteudo(
      @Parameter(description = "ID do conteúdo") @PathVariable String id,
      @Valid @RequestBody ConteudoRequestDto requestDTO) {

    return ResponseEntity.ok(conteudoService.atualizarConteudo(id, requestDTO));
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  @Operation(summary = "Excluir conteúdo", description = "Exclui um conteúdo (apenas ADMIN)")
  @ApiResponse(responseCode = "204", description = "Conteúdo excluído com sucesso")
  @ApiResponse(responseCode = "403", description = "Acesso negado - apenas ADMIN")
  @ApiResponse(responseCode = "404", description = "Conteúdo não encontrado")
  public ResponseEntity<Void> excluirConteudo(
      @Parameter(description = "ID do conteúdo") @PathVariable String id) {

    conteudoService.excluirConteudo(id);
    return ResponseEntity.noContent().build();
  }
}
