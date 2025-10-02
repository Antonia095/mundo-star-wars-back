package br.com.projeto.mundo_star_wars.dto;

import br.com.projeto.mundo_star_wars.enums.TipoUsuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioResponseDto {

  Long id;

  String email;

  TipoUsuario tipoUsuario;
}
