package uz.test.abitur.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.test.abitur.domains.TestSession;
import uz.test.abitur.dtos.ResponseDTO;
import uz.test.abitur.dtos.subject.SubjectCreateDTO;
import uz.test.abitur.dtos.test.SolveQuestionResultDTO;
import uz.test.abitur.dtos.test.TestSessionCreateDTO;
import uz.test.abitur.services.SolveQuestionService;
import uz.test.abitur.services.TestSessionService;
import uz.test.abitur.utils.BaseUtils;

import java.time.LocalDateTime;
import java.util.Objects;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;
import static uz.test.abitur.utils.UrlUtils.BASE_TESTS_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_TESTS_URL)
@PreAuthorize("hasAnyRole('USER','SUPER_ADMIN')")
@Tag(name = "Test Controller", description = "Test API")
public class TestController {
    private final TestSessionService testSessionService;
    private final SolveQuestionService solveQuestionService;

    @Operation(summary = "For USERS , This API is used for test"
            , responses = {
            @ApiResponse(responseCode = "200", description = "Test Started", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))}
    )
    @PostMapping("/start")
    public ResponseEntity<ResponseDTO<TestSession>> create(@Valid @RequestBody TestSessionCreateDTO dto) {
        if ((dto.getFirstSubjectId() == null || dto.getFirstSubjectId() == -1)
                && (dto.getSecondSubjectId() == null || dto.getSecondSubjectId() == -1)
                && (!dto.isWithMandatory())) {
            throw new RuntimeException("You should choose at least one subject");
        }
        TestSession testSession = testSessionService.findActiveTestSession();
        if (!Objects.isNull(testSession)) {
            throw new RuntimeException("You have already started test");
        }
        testSession = testSessionService.create(dto);
        return ResponseEntity.ok(new ResponseDTO<>(testSession, "Test Session started"));
    }

    @Operation(summary = "For USERS , This API is used for get test questions"
            , responses = {
            @ApiResponse(responseCode = "200", description = "Returned questions", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))}
    )
    @GetMapping(value = "/get/{testSessionId:.*}/{subjectId:.*}")
    public ResponseEntity<ResponseDTO<Page<SolveQuestionResultDTO>>> get(@PathVariable Integer testSessionId,
                                                                         @PathVariable Integer subjectId,
                                                                         @RequestParam(name = "questionNumber", defaultValue = "0")
                                                                         @Min(value = 1) @Max(50) Integer questionNumber) {
        Pageable pageable = PageRequest.of(questionNumber - 1, 1);
        Page<SolveQuestionResultDTO> solveQuestionResultDTOS = solveQuestionService.getResultDTO(testSessionId, subjectId, pageable);
        return ResponseEntity.ok(new ResponseDTO<>(solveQuestionResultDTOS));
    }

    @Operation(summary = "For USERS , This API is used for update test question's user answer "
            , responses = {
            @ApiResponse(responseCode = "200", description = "Updated", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))}
    )
    @PutMapping(value = "/choose/{testSessionId:.*}/{subjectId:.*}/{questionId:.*}/{answerId:.*}")
    public ResponseEntity<ResponseDTO<Page<Void>>> get(@PathVariable Integer testSessionId,
                                                       @PathVariable Integer subjectId,
                                                       @PathVariable String questionId,
                                                       @PathVariable String answerId) {
        TestSession testSession = testSessionService.findById(testSessionId);
        if (testSession.getFinishedAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Test session is finished");
        }
        solveQuestionService.updateUserAnswer(testSessionId, subjectId, questionId, answerId);
        return ResponseEntity.ok(new ResponseDTO<>(null, "User Answer updated"));
    }

    @Operation(summary = "For USERS , This API is used for test"
            , responses = {
            @ApiResponse(responseCode = "200", description = "Test Started", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))}
    )
    @PostMapping("/finish")
    public ResponseEntity<ResponseDTO<Void>> create() {
        TestSession testSession = testSessionService.findActiveTestSession();
        if (Objects.isNull(testSession)) {
            throw new RuntimeException("You don't have started test");
        }
        testSessionService.finishTest(testSession);
        return ResponseEntity.ok(new ResponseDTO<>(null, "Test Session Finished"));
    }
}

