package com.example.jogosdevideogame.Model;

import com.example.jogosdevideogame.Util.Mensagem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Jogo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NotBlank(message = Mensagem.ERRO_TITULO)
    @Size(min = 3, max = 20, message = Mensagem.ERRO_TITULO)
    String titulo;
    @NotBlank(message = Mensagem.ERRO_GENERO)
    @Size(min = 3, max = 10, message = Mensagem.ERRO_GENERO)
    String genero;
    @NotBlank(message = Mensagem.ERRO_FAIXA_ETARIA)
    @Size(min = 2, max = 3, message = Mensagem.ERRO_FAIXA_ETARIA)
    String faixa_etaria;
    @NotBlank(message = Mensagem.ERRO_SINOPSE)
    String sinopse;
    String ImageUri;
    //@Size(min = 2, max = 3, message = Mensagem.ERRO_CLASSIFICACAO)
    @Max(value = 21, message = Mensagem.ERRO_CLASSIFICACAO)
    @Min(value = 10, message = Mensagem.ERRO_CLASSIFICACAO)
    //@NotEmpty(message = Mensagem.ERRO_CLASSIFICACAO)
    Integer classificacao;
    //@NotEmpty(message = Mensagem.ERRO_VALOR)
    @PositiveOrZero(message = Mensagem.ERRO_VALOR)
    Integer valor;
    //@Size(min = 4, max = 4, message = Mensagem.ERRO_LANCAMENTO)
    //@NotEmpty(message = Mensagem.ERRO_LANCAMENTO)
    @Min(value = 1933, message = Mensagem.ERRO_LANCAMENTO)
    @Max(value = 2030, message = Mensagem.ERRO_LANCAMENTO)
    Integer lancamento;

}
