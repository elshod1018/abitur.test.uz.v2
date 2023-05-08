package uz.test.abitur.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import uz.test.abitur.domains.AuthUser;
import uz.test.abitur.dtos.auth.UserCreateDTO;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);

    UserCreateDTO toDto(AuthUser user);

    AuthUser toEntity(UserCreateDTO dto);
}
