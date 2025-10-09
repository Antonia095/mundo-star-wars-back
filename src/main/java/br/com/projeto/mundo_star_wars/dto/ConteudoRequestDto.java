package br.com.projeto.mundo_star_wars.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConteudoRequestDto {

  @NotBlank
  @Size(max = 100, message = "O título não pode exceder 100 caracteres")
  private String titulo;

  @NotBlank
  @Size(max = 500, message = "A descrição não pode exceder 500 caracteres")
  private String descricao;

  @NotBlank(message = "URL da imagem é obrigatória")
  private String imagem;
}
