package uz.test.abitur.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import uz.test.abitur.domains.AuthUser;
import uz.test.abitur.dtos.auth.UserCreateDTO;
import uz.test.abitur.dtos.user.UserProfileUpdateDTO;
import uz.test.abitur.dtos.user.UserUpdateDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-09T22:16:43+0500",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.6 (Private Build)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserCreateDTO toDto(AuthUser user) {
        if ( user == null ) {
            return null;
        }

        UserCreateDTO.UserCreateDTOBuilder userCreateDTO = UserCreateDTO.builder();

        userCreateDTO.firstName( user.getFirstName() );
        userCreateDTO.lastName( user.getLastName() );
        userCreateDTO.phoneNumber( user.getPhoneNumber() );
        userCreateDTO.password( user.getPassword() );

        return userCreateDTO.build();
    }

    @Override
    public AuthUser toEntity(UserCreateDTO dto) {
        if ( dto == null ) {
            return null;
        }

        AuthUser.AuthUserBuilder authUser = AuthUser.childBuilder();

        authUser.firstName( dto.getFirstName() );
        authUser.lastName( dto.getLastName() );
        authUser.phoneNumber( dto.getPhoneNumber() );
        authUser.password( dto.getPassword() );

        return authUser.build();
    }

    @Override
    public void updateUsersFromDTO(UserUpdateDTO dto, AuthUser user) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            user.setId( dto.getId() );
        }
        if ( dto.getRole() != null ) {
            user.setRole( dto.getRole() );
        }
        if ( dto.getStatus() != null ) {
            user.setStatus( dto.getStatus() );
        }
    }

    @Override
    public void updateUsersProfileFromDTO(UserProfileUpdateDTO dto, AuthUser user) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getFirstName() != null ) {
            user.setFirstName( dto.getFirstName() );
        }
        if ( dto.getMiddleName() != null ) {
            user.setMiddleName( dto.getMiddleName() );
        }
        if ( dto.getLastName() != null ) {
            user.setLastName( dto.getLastName() );
        }
    }
}
