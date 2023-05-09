package uz.test.abitur.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import uz.test.abitur.domains.Subject;
import uz.test.abitur.dtos.subject.SubjectCreateDTO;
import uz.test.abitur.dtos.subject.SubjectUpdateDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-09T22:16:43+0500",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.6 (Private Build)"
)
@Component
public class SubjectMapperImpl implements SubjectMapper {

    @Override
    public SubjectCreateDTO toDto(Subject subject) {
        if ( subject == null ) {
            return null;
        }

        SubjectCreateDTO.SubjectCreateDTOBuilder subjectCreateDTO = SubjectCreateDTO.builder();

        subjectCreateDTO.name( subject.getName() );
        subjectCreateDTO.code( subject.getCode() );
        subjectCreateDTO.mandatory( subject.isMandatory() );

        return subjectCreateDTO.build();
    }

    @Override
    public Subject toEntity(SubjectCreateDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Subject.SubjectBuilder subject = Subject.childBuilder();

        subject.name( dto.getName() );
        subject.code( dto.getCode() );
        subject.mandatory( dto.isMandatory() );

        return subject.build();
    }

    @Override
    public void updateNewsFromDTO(SubjectUpdateDTO dto, Subject subject) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            subject.setId( dto.getId() );
        }
        if ( dto.getName() != null ) {
            subject.setName( dto.getName() );
        }
        if ( dto.getCode() != null ) {
            subject.setCode( dto.getCode() );
        }
        subject.setMandatory( dto.isMandatory() );
    }
}
