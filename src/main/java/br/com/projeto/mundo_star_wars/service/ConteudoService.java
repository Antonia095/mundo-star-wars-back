package br.com.projeto.mundo_star_wars.service;

import br.com.projeto.mundo_star_wars.dto.ConteudoRequestDto;
import br.com.projeto.mundo_star_wars.dto.ConteudoResponseDto;
import br.com.projeto.mundo_star_wars.exception.BusinessException;
import br.com.projeto.mundo_star_wars.exception.EntityNotFoundException;
import br.com.projeto.mundo_star_wars.mapper.ConteudoMapper;
import br.com.projeto.mundo_star_wars.repository.ConteudoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConteudoService {

  private final ConteudoRepository conteudoRepository;
  private final ConteudoMapper mapper;

  public ConteudoResponseDto criarConteudo(ConteudoRequestDto conteudoRequest) {

    log.info("Criando conteúdo: {}", conteudoRequest.getTitulo());


    if (conteudoRepository.existsByTitulo(conteudoRequest.getTitulo())) {
      throw new BusinessException("Já existe um conteúdo com este título");
    }

    var conteudo = mapper.toEntity(conteudoRequest);
    var conteudoSalvo = conteudoRepository.save(conteudo);

    log.info("Conteúdo criado com sucesso. ID: {}", conteudoSalvo.getId());

    return mapper.toConteudoResponse(conteudoSalvo);
  }

  public ConteudoResponseDto buscarConteudoPorId(String id) {

    log.info("Buscando conteúdo por ID: {}", id);

    var conteudo = conteudoRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Conteúdo não encontrado com ID: " + id));

    return mapper.toConteudoResponse(conteudo);
  }

  public Page<ConteudoResponseDto> listarTodos(Pageable pageable) {

    log.info("Listando todos os conteúdos - Página: {}, Tamanho: {}",
        pageable.getPageNumber(), pageable.getPageSize());

    var conteudosPage = conteudoRepository.findAll(pageable);
    var responseDTOs = mapper.toResponseList(conteudosPage.getContent());

    return new PageImpl<>(responseDTOs, pageable, conteudosPage.getTotalElements());
  }

  public ConteudoResponseDto atualizarConteudo(String id, ConteudoRequestDto conteudoRequest) {

    log.info("Atualizando conteúdo com ID: {}", id);

    var conteudo = conteudoRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Conteúdo não encontrado com ID: " + id));

    if (!conteudo.getTitulo().equals(conteudoRequest.getTitulo()) &&
        conteudoRepository.existsByTitulo(conteudoRequest.getTitulo())) {
      throw new BusinessException("Já existe um conteúdo com este título");
    }

    mapper.updateEntity(conteudoRequest, conteudo);
    var conteudoAtualizado = conteudoRepository.save(conteudo);

    log.info("Conteúdo atualizado com sucesso. ID: {}", id);

    return mapper.toConteudoResponse(conteudoAtualizado);
  }

  public void excluirConteudo(String id) {

    log.info("Excluindo conteúdo com ID: {}", id);

    buscarPorId(id);

    conteudoRepository.deleteById(id);

    log.info("Conteúdo excluído com sucesso. ID: {}", id);
  }

  private void buscarPorId(String id) {

    if (!conteudoRepository.existsById(id)) {
      throw new EntityNotFoundException("Conteúdo não encontrado com ID: " + id);
    }
  }

}
