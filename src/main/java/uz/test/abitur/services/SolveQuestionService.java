package uz.test.abitur.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.test.abitur.domains.*;
import uz.test.abitur.dtos.test.SolveQuestionResultDTO;
import uz.test.abitur.repositories.SolveQuestionRepository;
import uz.test.abitur.utils.BaseUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SolveQuestionService {
    private final SolveQuestionRepository solveQuestionRepository;
    private final QuestionService questionService;

    public void create(TestSession testSession) {
        Integer firstSubjectId = testSession.getFirstSubjectId();
        Integer secondSubjectId = testSession.getSecondSubjectId();
        Integer thirdSubjectId = testSession.getThirdSubjectId();
        Integer fourthSubjectId = testSession.getFourthSubjectId();
        Integer fifthSubjectId = testSession.getFifthSubjectId();
        Integer testSessionId = testSession.getId();
        List<String> subjectQuestions;//questions ids
        Integer mainSubject = BaseUtils.QUESTION_COUNT_MAP.get("mainSubject");
        if (!Objects.isNull(firstSubjectId)) {
            subjectQuestions = questionService.findNRandQuestionsBySubjectId(firstSubjectId, mainSubject);
            save(firstSubjectId, testSessionId, subjectQuestions);
        }
        if (!Objects.isNull(secondSubjectId)) {
            subjectQuestions = questionService.findNRandQuestionsBySubjectId(secondSubjectId, mainSubject);
            save(secondSubjectId, testSessionId, subjectQuestions);
        }
        Integer mandatorySubject = BaseUtils.QUESTION_COUNT_MAP.get("mandatorySubject");
        if (!Objects.isNull(thirdSubjectId)) {
            subjectQuestions = questionService.findNRandQuestionsBySubjectId(thirdSubjectId, mandatorySubject);
            save(thirdSubjectId, testSessionId, subjectQuestions);
        }
        if (!Objects.isNull(fourthSubjectId)) {
            subjectQuestions = questionService.findNRandQuestionsBySubjectId(fourthSubjectId, mandatorySubject);
            save(fourthSubjectId, testSessionId, subjectQuestions);
        }
        if (!Objects.isNull(fifthSubjectId)) {
            subjectQuestions = questionService.findNRandQuestionsBySubjectId(fifthSubjectId, mandatorySubject);
            save(fifthSubjectId, testSessionId, subjectQuestions);
        }
    }

    private void save(Integer subjectId, Integer testSessionId, List<String> questionsIds) {
        questionsIds.forEach(questionId ->
                solveQuestionRepository.save(SolveQuestion.builder()
                        .questionId(questionId)
                        .subjectId(subjectId)
                        .testSessionId(testSessionId)
                        .build()));
    }

    public int getCount(Integer testSessionId, Integer firstSubjectId) {
        return solveQuestionRepository.getCount(testSessionId, firstSubjectId);
    }

    public Page<SolveQuestionResultDTO> getResultDTO(Integer testSessionId, Integer subjectId, Pageable pageable) {
        Page<SolveQuestionResultDTO> all = solveQuestionRepository.getPageableSolveQuestionDTO(testSessionId, subjectId, pageable);
        all.get().forEach(solveQuestionResultDTO -> {
            Question question = solveQuestionResultDTO.getQuestion();
            setNull(question.getAnswers());
        });
        return all;
    }

    private void setNull(List<Answer> answers) {
        answers.forEach(answer -> answer.setIsTrue(null));
    }

    public void updateUserAnswer(Integer testSessionId, Integer subjectId, String questionId, String answerId) {
        solveQuestionRepository.findByTestSessionIdAndSubjectIdAndQuestionId(testSessionId, subjectId, questionId)
                .ifPresent(solveQuestion -> {
                    solveQuestion.setUserAnswerId(answerId);
                    solveQuestionRepository.save(solveQuestion);
                });
    }

    public List<Boolean> getListOfAnswerTrueOrFalse(Integer testSessionId, Integer subjectId) {
        List<SolveQuestion> list = solveQuestionRepository.findByTestSessionIdAndSubjectId(testSessionId, subjectId);
        List<Boolean> result = new ArrayList<>();
        list.forEach(solveQuestion -> {
            Question question = questionService.getById(solveQuestion.getQuestionId());
            List<Answer> answers = question.getAnswers();
            answers.forEach(answer -> {
                if (answer.getIsTrue()) {
                    result.add(answer.getId().equals(solveQuestion.getUserAnswerId()));
                }
            });
        });
        return result;
    }
}
