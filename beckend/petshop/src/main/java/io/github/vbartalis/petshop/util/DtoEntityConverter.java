package io.github.vbartalis.petshop.util;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This class contains methods that handle conversion between DTOs and Entities.
 */
@Component
public class DtoEntityConverter {

    @Autowired
    ModelMapper modelMapper;


    /**
     * This method converts an Entity to a Data Transfer Object.
     *
     * @param entity   The Entity that will be converted to a Data Transfer Object.
     * @param dtoClass The Class of the Data Transfer Object into which the Entity will be converted.
     * @param <D>      The type of object that will be returned.
     * @param <T>      The type of object that will be converted.
     * @return A Data Transfer Object converted from an Entity.
     */
    public <D, T> D convertToDto(T entity, Class<D> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }

    /**
     * This method converts a Page of Entities to a Page of Data Transfer Objects.
     *
     * @param entities The Page of Entities that will be converted to a Page of Data Transfer Objects.
     * @param dtoClass The Data Transfer Object Class into which the Page of Entities will be converted.
     * @param <D>      The type of objects in result page.
     * @param <T>      The type of objects that will be converted.
     * @return A Page of Data Transfer Objects converted from a Page of Entities.
     */
    public <D, T> Page<D> convertToPageDto(Page<T> entities, Class<D> dtoClass) {
        return entities.map(entity -> convertToDto(entity, dtoClass));
    }

    /**
     * This method converts a List of Entities to a List of Data Transfer Objects.
     *
     * @param entities The List of Entities that will be converted to a List of Data Transfer Objects.
     * @param dtoClass The Data Transfer Object Class into which the List of Entities will be converted.
     * @param <D>      The type of objects in result page.
     * @param <T>      The type of objects that will be converted.
     * @return A List of Data Transfer Objects converted from a List of Entities.
     */
    public <D, T> List<D> convertToListDto(List<T> entities, Class<D> dtoClass) {
        return entities.stream()
                .map(entity -> convertToDto(entity, dtoClass))
                .collect(Collectors.toList());
    }

    /**
     * This method converts a Data Transfer Object to an Entity.
     *
     * @param dto         The Data Transfer Object that will be converted to an Entity.
     * @param entityClass The Entity Class into which the Data Transfer Object will be converted.
     * @param <T>         The type of object that will be returned.
     * @param <D>         The type of object that will be converted.
     * @return An Entity converted from a Data Transfer Object.
     */
    public <T, D> D convertToEntity(T dto, Class<D> entityClass) {
        return modelMapper.map(dto, entityClass);
    }
}
