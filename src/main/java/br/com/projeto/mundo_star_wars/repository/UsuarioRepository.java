package br.com.projeto.mundo_star_wars.repository;

import br.com.projeto.mundo_star_wars.model.Usuario;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {

  Optional<Usuario> findByEmail(String email);

  boolean existsByEmail(String email);
}
