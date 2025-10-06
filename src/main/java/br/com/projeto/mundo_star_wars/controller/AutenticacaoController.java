package br.com.projeto.mundo_star_wars.controller;

import br.com.projeto.mundo_star_wars.dto.UsuarioRequestDto;
import br.com.projeto.mundo_star_wars.service.AutenticacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/autenticacao")
@Tag(name = "Autenticação", description = "Endpoints relacionado a autenticação de usuários")
public class AutenticacaoController {

  private final AutenticacaoService autenticacaoService;

  @PostMapping("/login")
  @Operation(summary = "Fazer login")
  public String login(@RequestBody UsuarioRequestDto usuarioDto) {

    return autenticacaoService.login(usuarioDto.getEmail(), usuarioDto.getSenha());
  }

}
