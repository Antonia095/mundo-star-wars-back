package br.com.projeto.mundo_star_wars.controller;

import br.com.projeto.mundo_star_wars.dto.UsuarioRequestDto;
import br.com.projeto.mundo_star_wars.service.AutenticacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/autenticacao")
public class AutenticacaoController {

  private final AutenticacaoService autenticacaoService;

  @PostMapping("/login")
  public String login(@RequestBody UsuarioRequestDto usuarioDto) {

    return autenticacaoService.login(usuarioDto.getEmail(), usuarioDto.getSenha());
  }

}
