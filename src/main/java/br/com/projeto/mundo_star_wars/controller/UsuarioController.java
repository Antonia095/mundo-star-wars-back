package br.com.projeto.mundo_star_wars.controller;

import static org.springframework.http.HttpStatus.CREATED;

import br.com.projeto.mundo_star_wars.dto.UsuarioRequestDto;
import br.com.projeto.mundo_star_wars.dto.UsuarioResponseDto;
import br.com.projeto.mundo_star_wars.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/usuarios")
public class UsuarioController {

  private final UsuarioService usuarioService;

  @PostMapping("/cadastro")
  public ResponseEntity<UsuarioResponseDto> cadastrarUsuario(@Valid @RequestBody UsuarioRequestDto usuarioDto) {

    var usuarioCriado = usuarioService.cadastrarAdministrador(usuarioDto);

    return ResponseEntity.status(CREATED).body(usuarioCriado);
  }
}
