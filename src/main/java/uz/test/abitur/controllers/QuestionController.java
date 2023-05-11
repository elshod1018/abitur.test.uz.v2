package uz.test.abitur.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.test.abitur.domains.Question;
import uz.test.abitur.dtos.ResponseDTO;
import uz.test.abitur.dtos.question.QuestionCreateDTO;
import uz.test.abitur.dtos.question.QuestionUpdateDTO;
import uz.test.abitur.services.QuestionService;

import static uz.test.abitur.utils.UrlUtils.BASE_QUESTIONS_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_QUESTIONS_URL)
@PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
@Tag(name = "Question Controller", description = "Question API")
public class QuestionController {
    private final QuestionService questionService;

    @Operation(summary = "For ADMINS,SUPER_ADMINS , This API is used for create new question"
//            , responses = {
//            @ApiResponse(responseCode = "201", description = "Question created", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
//            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))}
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO<Question>> create(@Valid @RequestBody QuestionCreateDTO dto) {
        Question question = questionService.create(dto);
        return ResponseEntity.status(201).body(new ResponseDTO<>(question, "Question created successfully"));
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'USER')")
    @Operation(summary = "For ADMINS,SUPER_ADMINS,USERS , This API is used for getting a question"
//            , responses = {
//            @ApiResponse(responseCode = "200", description = "Question returned", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
//            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))}
    )
    @GetMapping("/get/{id}")
    public ResponseEntity<ResponseDTO<Question>> getById(@PathVariable String id) {

        Question question = questionService.getById(id);
        return ResponseEntity.ok(new ResponseDTO<>(question));
    }


    @Operation(summary = "For ADMINS,SUPER_ADMINS , This API is used for updating a question"
//            , responses = {
//            @ApiResponse(responseCode = "200", description = "Question updated", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
//            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))}
    )
    @PutMapping("/update")
    public ResponseEntity<ResponseDTO<Question>> update( @RequestBody QuestionUpdateDTO dto) {
        Question question = questionService.update(dto);
        return ResponseEntity.ok(new ResponseDTO<>(question, "Question updated successfully"));
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'USER')")
    @Operation(summary = "For ADMINS,SUPER_ADMINS,USERS , This API is used for getting paged questions"
//            , responses = {
//            @ApiResponse(responseCode = "200", description = "Questions returned", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
//            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))}
    )
    @GetMapping("/get/all")
    public ResponseEntity<ResponseDTO<Page<Question>>> getAll(@RequestParam(required = false) Integer subjectId,
                                                              @RequestParam(required = false, defaultValue = "10") int size,
                                                              @RequestParam(required = false, defaultValue = "0") int page) {
        Sort sort = Sort.by(Sort.Direction.DESC, "subject.id")
                .and(Sort.by(Sort.Direction.DESC, "createdAt"));
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Question> questionPages = questionService.getAll(subjectId, pageable);
        return ResponseEntity.ok(new ResponseDTO<>(questionPages));
    }

    @Operation(summary = "For ADMINS , SUPER_ADMINS , This API is used for deleting a question"
//            , responses = {
//            @ApiResponse(responseCode = "200", description = "Question deleted", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
//            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))}
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO<Void>> delete(@PathVariable String id) {
        questionService.delete(id);
        return ResponseEntity.ok(new ResponseDTO<>(null, "Question deleted successfully"));
    }
}
