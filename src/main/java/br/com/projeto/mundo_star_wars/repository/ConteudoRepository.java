package br.com.projeto.mundo_star_wars.repository;

import br.com.projeto.mundo_star_wars.model.Conteudo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConteudoRepository extends MongoRepository<Conteudo, String> {
  boolean existsByTitulo(String titulo);
}
