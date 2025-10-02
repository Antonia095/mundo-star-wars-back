package br.com.projeto.mundo_star_wars.exception;

public class EntityNotFoundException extends RuntimeException{

  public EntityNotFoundException(String message) {
    super(message);
  }

}
