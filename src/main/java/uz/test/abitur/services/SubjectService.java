package uz.test.abitur.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.test.abitur.domains.Subject;
import uz.test.abitur.dtos.question.QuestionCountDTO;
import uz.test.abitur.dtos.subject.SubjectCreateDTO;
import uz.test.abitur.dtos.subject.SubjectUpdateDTO;
import uz.test.abitur.repositories.SubjectRepository;
import uz.test.abitur.utils.BaseUtils;

import java.util.List;
import java.util.Optional;

import static uz.test.abitur.mapper.SubjectMapper.SUBJECT_MAPPER;

@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final QuestionService questionService;

    public Subject create(SubjectCreateDTO dto) {
        dto.setCode(dto.getCode().toUpperCase());
        findByCodeAndMandatory(dto.getCode(), dto.isMandatory());
        if (dto.isMandatory()) {
            List<Subject> mandatorySubjects = getMandatorySubjects();
            if (mandatorySubjects.size() == 3) {
                throw new RuntimeException("You can't add more than 3 mandatory subjects");
            }
        }
        Subject subject = SUBJECT_MAPPER.toEntity(dto);
        return subjectRepository.save(subject);
    }

    public Subject findById(Integer id) {
        return subjectRepository.findSubjectById(id)
                .orElseThrow(() -> new RuntimeException("Subject not found with id: " + id));
    }

    public Page<Subject> getAll(Pageable pageable) {
        return subjectRepository.getAll(pageable);
    }

    public Subject update(SubjectUpdateDTO dto) {
        if (dto.isMandatory()) {
            List<Subject> mandatorySubjects = getMandatorySubjects();
            if (mandatorySubjects.size() == 3) {
                throw new RuntimeException("You can't add more than 3 mandatory subjects");
            }
        }
        Subject subject = findById(dto.getId());
        dto.setCode(dto.getCode().toUpperCase());
        findByCodeAndMandatory(dto.getCode(), dto.isMandatory());
        SUBJECT_MAPPER.updateNewsFromDTO(dto, subject);
        return subjectRepository.save(subject);
    }

    public void delete(Integer id) {
        boolean existsBySubjectId = questionService.existsBySubjectId(id);
        if (existsBySubjectId) {
            throw new RuntimeException("You can't delete this subject, because it has questions");
        }
        Subject subject = findById(id);
        subject.setDeleted(true);
        subjectRepository.save(subject);
    }

    private void findByCodeAndMandatory(String code, boolean mandatory) {
        Optional<Subject> subject = subjectRepository.findByCodeAndMandatory(code, mandatory);
        subject.ifPresent(value -> {
            throw new RuntimeException("Subject already exist with code '%s' and mandatory '%s'"
                    .formatted(value.getCode(), value.isMandatory()));
        });
    }

    public List<Subject> getMandatorySubjects() {
        return subjectRepository.findMandatorySubjects();
    }

    public void addCount(QuestionCountDTO dto) {
        Integer mainSubject = dto.getMainSubject();
        Integer mandatorySubject = dto.getMandatorySubject();
        if (mainSubject != null) {
            BaseUtils.QUESTION_COUNT_MAP.put("mainSubject", mainSubject);
        }
        if (mandatorySubject != null)
            BaseUtils.QUESTION_COUNT_MAP.put("mandatorySubject", mandatorySubject);
    }

    public QuestionCountDTO getCount() {
        return new QuestionCountDTO(
                BaseUtils.QUESTION_COUNT_MAP.get("mainSubject"),
                BaseUtils.QUESTION_COUNT_MAP.get("mandatorySubject"));
    }
}
