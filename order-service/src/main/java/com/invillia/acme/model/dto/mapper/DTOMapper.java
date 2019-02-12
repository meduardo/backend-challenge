package com.invillia.acme.model.dto.mapper;


/**
 * Abstração de mapper de entidade para DTO
 * 
 * @author <a href="mailto:m.eduardo5@gmail.com"> Mario Eduardo Giolo</a>
 *
 */
public interface DTOMapper<D, E> {
	
	D toDTO(E entity);
}
