package br.com.projeto.mundo_star_wars.dto;

import br.com.projeto.mundo_star_wars.enums.TipoUsuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UsuarioRequestDto {

  @NotBlank
  private String email;

  @NotBlank
  private String senha;

  @NotNull
  private TipoUsuario tipoUsuario;
}
