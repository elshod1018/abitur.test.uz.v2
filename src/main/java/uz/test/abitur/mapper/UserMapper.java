package uz.test.abitur.mapper;


import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import uz.test.abitur.domains.AuthUser;
import uz.test.abitur.dtos.auth.UserCreateDTO;
import uz.test.abitur.dtos.user.UserProfileUpdateDTO;
import uz.test.abitur.dtos.user.UserUpdateDTO;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);

    UserCreateDTO toDto(AuthUser user);

    AuthUser toEntity(UserCreateDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    void updateUsersFromDTO(UserUpdateDTO dto, @MappingTarget AuthUser user);
    @BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    void updateUsersProfileFromDTO(UserProfileUpdateDTO dto, @MappingTarget AuthUser user);
}
