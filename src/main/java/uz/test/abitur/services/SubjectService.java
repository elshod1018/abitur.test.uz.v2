package uz.test.abitur.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.test.abitur.domains.Subject;
import uz.test.abitur.dtos.subject.SubjectCreateDTO;
import uz.test.abitur.dtos.subject.SubjectUpdateDTO;
import uz.test.abitur.ex_handlers.exeptions.NotFoundException;
import uz.test.abitur.repositories.SubjectRepository;

import java.util.Optional;

import static uz.test.abitur.mapper.SubjectMapper.SUBJECT_MAPPER;

@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;

    public Subject create(SubjectCreateDTO dto) {
        dto.setCode(dto.getCode().toUpperCase());
        findByCodeAndMandatory(dto.getCode(), dto.isMandatory());
        Subject subject = SUBJECT_MAPPER.toEntity(dto);
        return subjectRepository.save(subject);
    }

    public Subject findById(Integer id) {
        return subjectRepository.findSubjectById(id)
                .orElseThrow(() -> new NotFoundException("Subject not found with id: " + id));
    }

    public Page<Subject> getAll(Pageable pageable) {
        return subjectRepository.getAll(pageable);
    }

    public Subject update(SubjectUpdateDTO dto) {
        Subject subject = findById(dto.getId());
        findByCodeAndMandatory(dto.getCode(), dto.isMandatory());
        SUBJECT_MAPPER.updateNewsFromDTO(dto, subject);
        return subjectRepository.save(subject);
    }

    public void delete(Integer id) {
        Subject subject = findById(id);
        subject.setDeleted(true);
        subjectRepository.save(subject);
    }

    private void findByCodeAndMandatory(String code, boolean mandatory) {
        Optional<Subject> subject = subjectRepository.findByCodeAndMandatory(code, mandatory);
        subject.ifPresent(value -> {
            throw new NotFoundException("Subject already exist with code '%s' and mandatory '%s'"
                    .formatted(value.getCode(), value.isMandatory()));
        });
    }
}
