package com.ms.users.api.assembler;

import com.ms.users.api.dto.user.output.UserDTO;
import com.ms.users.domain.model.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.record.RecordModule;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MapperDTO {

    private ModelMapper modelMapper;

    public MapperDTO(ModelMapper modelMapper) {

        this.modelMapper = modelMapper;
        this.modelMapper.registerModule(new RecordModule());
    }

    /**
     * Transforms from a source object to a determined class of type <T> and returns an object of <T> class .
     * e.g: Transform UserDTO into User
     * {
     *     new mapperDTO().transform(userDTO, User.class)
     * }
     *
     * The opposite way is surely also possible
     * {
     *     new mapperDTO().transform(user, UserDTO.class)
     * }
     *
     * Mainly used for transforming correlated classes mappings, you may need to adapt or add mappings through
     * ModelMapperConfig configuration class for custom behaviour on mapping fields
     * @param source
     * @param clazz
     * @return
     * @param <T> clazz
     */
    public<T> T transform(Object source, Class<T> clazz){
        return modelMapper.map(source, clazz);
    }

    /**
     * Copy properties from a source to a determined object type
     * @param source
     * @param domain
     */
    public void copyToDomain(Object source, Object domain){
        modelMapper.map(source, domain);
    }

    /**
     * Iterates over a list of types to be transformed from <T> type to <D> clazz
     * @param types
     * @param clazz
     * @return
     * @param <T>
     * @param <D>
     */
    public<T,D> List<D> toCollection(Collection<T> types, Class<D> clazz){
        return types.stream()
                .map(type -> transform(type, clazz))
                .collect(Collectors.toList());
    }
}
