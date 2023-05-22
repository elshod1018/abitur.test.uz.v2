package uz.test.abitur.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.test.abitur.domains.TestHistory;
import uz.test.abitur.domains.TestSession;
import uz.test.abitur.dtos.ResponseDTO;
import uz.test.abitur.dtos.test.SolveQuestionResultDTO;
import uz.test.abitur.dtos.test.TestSessionCreateDTO;
import uz.test.abitur.services.SolveQuestionService;
import uz.test.abitur.services.TestHistoryService;
import uz.test.abitur.services.TestSessionService;

import java.time.LocalDateTime;
import java.util.Objects;

import static uz.test.abitur.utils.UrlUtils.BASE_TESTS_URL;
import static uz.test.abitur.utils.UrlUtils.BASE_TEST_HISTORIES_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_TEST_HISTORIES_URL)
@PreAuthorize("isAuthenticated()")
@Tag(name = "Test History Controller", description = "Test History API")
public class TestHistoryController {
    private final TestHistoryService testHistoryService;

    @Operation(summary = "For USERS , This API is used for test history", responses = {
            @ApiResponse(responseCode = "200", description = "Test Histories", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))}
    )
    @PostMapping("/all")
    public ResponseEntity<ResponseDTO<Page<TestHistory>>> getAll(@RequestParam(required = false, defaultValue = "15") Integer size,
                                                                 @RequestParam(required = false, defaultValue = "0") Integer page) {
        PageRequest pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(new ResponseDTO<>(testHistoryService.findAll(pageable)));
    }
}

