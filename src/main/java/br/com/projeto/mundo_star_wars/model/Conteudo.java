package br.com.projeto.mundo_star_wars.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "conteudos")
public class Conteudo {

  @Id
  private String id;

  private String titulo;

  private String descricao;

  private String imagem;

  @CreatedDate
  private LocalDateTime dataCriacao;

  @LastModifiedDate
  private LocalDateTime dataAtualizacao;
}
