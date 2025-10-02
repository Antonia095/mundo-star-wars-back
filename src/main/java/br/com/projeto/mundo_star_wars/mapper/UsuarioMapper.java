package br.com.projeto.mundo_star_wars.mapper;

import br.com.projeto.mundo_star_wars.dto.UsuarioResponseDto;
import br.com.projeto.mundo_star_wars.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

  UsuarioResponseDto toUsuarioResponse(Usuario usuario);
}
