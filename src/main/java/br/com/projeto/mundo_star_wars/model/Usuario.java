package br.com.projeto.mundo_star_wars.model;

import br.com.projeto.mundo_star_wars.enums.TipoUsuario;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collation = "usuarios")
public class Usuario {

  @Id
  private Long id;

  private String email;

  private String senha;

  private TipoUsuario tipoUsuario;
}
