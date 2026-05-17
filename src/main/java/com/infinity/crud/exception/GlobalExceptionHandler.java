package com.infinity.crud.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/*
 * @RestControllerAdvice intercepta exceções lançadas em qualquer controller
 * e converte em respostas HTTP com o status correto, em vez de retornar
 * um stack trace genérico de 500 para o cliente.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /*
     * Disparado pelo @Valid quando algum campo falha na validação.
     * Retorna um Map com { "campo": "mensagem de erro" } para cada campo inválido.
     * Ex: { "email": "Email inválido!", "senha": "A senha é obrigatória!" }
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return errors;
    }

    /*
     * Agrupa as três exceções de "recurso não encontrado" num único handler.
     * Todas retornam 404 com a mensagem definida em cada exceção.
     */
    @ExceptionHandler({
            ClientNotFoundException.class,
            UserNotFoundException.class,
            EquipNotFoundException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFoundExceptions(RuntimeException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return error;
    }


    /*
     * Problemas com refresh token — token inexistente, revogado ou expirado.
     * Todos retornam 401 UNAUTHORIZED porque indicam que a sessão não é válida
     * e o usuário precisa autenticar novamente.
     */
    @ExceptionHandler({
            RefreshTokenNaoEncontradoException.class,
            RefreshTokenRevogadoException.class,
            RefreshTokenExpiradoException.class
    })
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, String> handleRefreshTokenExceptions(RuntimeException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return error;
    }
}
