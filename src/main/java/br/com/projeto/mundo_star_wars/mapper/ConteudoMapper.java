package br.com.projeto.mundo_star_wars.mapper;

import br.com.projeto.mundo_star_wars.dto.ConteudoRequestDto;
import br.com.projeto.mundo_star_wars.dto.ConteudoResponseDto;
import br.com.projeto.mundo_star_wars.model.Conteudo;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ConteudoMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "dataCriacao", ignore = true)
  @Mapping(target = "dataAtualizacao", ignore = true)
  Conteudo toEntity(ConteudoRequestDto conteudoRequestDto);

  ConteudoResponseDto toConteudoResponse(Conteudo conteudo);

  List<ConteudoResponseDto> toResponseList(List<Conteudo> conteudos);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "dataCriacao", ignore = true)
  @Mapping(target = "dataAtualizacao", ignore = true)
  void updateEntity(ConteudoRequestDto request, @MappingTarget Conteudo conteudo);

}
