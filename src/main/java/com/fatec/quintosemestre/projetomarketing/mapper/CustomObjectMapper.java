package com.fatec.quintosemestre.projetomarketing.mapper;

import java.util.List;

import org.mapstruct.Mapper;

/*
 * Mappers são utilizados para definir uma regra de conversão entre as dto's,
 * que são os dados enviados pelo front end, e as nossas entidades
 * 
 * Essa conversão é feita para manter a integridade dos dados e simplificar entidades que possuem
 * relacionamentos e estruturas de dados complexas. Dessa forma, o retorno para o frontend fica
 * mais "limpo".
 * 
 * Quando utilizar um mapper?
 * 
 * Se a entidade for pequena (conter poucos atributos ou pouca/nenhuma chave estrangeira),
 * talvez não haja necessidade de realizar essa conversão. Vai depender de como o frontend prefere
 * consumir a informação. No contexto desse projeto, sempre que uma entidade tiver uma chave estrangeira
 * vinculada a ela, vamos criar um mapper para fins de simplificar a sua estrutura de dados
 */

@Mapper(componentModel = "spring")
public interface CustomObjectMapper<T, R> {
    
    R converterParaDto(T entity);

    T converterParaEntidade(R dto);

    List<R> converterParaListaDeDtos(List<T> entityList);

}
