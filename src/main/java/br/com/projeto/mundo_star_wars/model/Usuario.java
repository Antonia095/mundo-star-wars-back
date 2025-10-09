package br.com.projeto.mundo_star_wars.model;

import br.com.projeto.mundo_star_wars.enums.TipoUsuario;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "usuarios")
public class Usuario {

  @Id
  private String id;

  private String email;

  private String senha;

  private TipoUsuario tipoUsuario;
}
