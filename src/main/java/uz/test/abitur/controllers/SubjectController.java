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
import uz.test.abitur.domains.Subject;
import uz.test.abitur.dtos.ResponseDTO;
import uz.test.abitur.dtos.question.QuestionCountDTO;
import uz.test.abitur.dtos.subject.SubjectCreateDTO;
import uz.test.abitur.dtos.subject.SubjectUpdateDTO;
import uz.test.abitur.services.SubjectService;

import static uz.test.abitur.utils.UrlUtils.BASE_SUBJECTS_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_SUBJECTS_URL)
@PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
@Tag(name = "Subject Controller", description = "Subject API")
public class SubjectController {
    private final SubjectService subjectService;

    @Operation(summary = "For ADMINS , SUPER_ADMINS , This API is used for create a Subject"
//            , responses = {
//            @ApiResponse(responseCode = "200", description = "Subject created", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
//            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))}
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO<Subject>> create(@Valid @RequestBody SubjectCreateDTO dto) {
        Subject news = subjectService.create(dto);
        return ResponseEntity.ok(new ResponseDTO<>(news, "Subject Created Successfully"));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'USER')")
    @Operation(summary = "For ADMINS , SUPER_ADMINS , USERS , This API is used for get an existing subject"
//            , responses = {
//            @ApiResponse(responseCode = "200", description = "Subject Returned", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
//            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))}
    )
    @GetMapping("/get/{id:.*}")
    public ResponseEntity<ResponseDTO<Subject>> get(@PathVariable Integer id) {
        Subject subject = subjectService.findById(id);
        return ResponseEntity.ok(new ResponseDTO<>(subject));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'USER')")
    @Operation(summary = "For ADMINS , SUPER_ADMINS , USERS , This API is used for get paged Subjects"
//            , responses = {
//            @ApiResponse(responseCode = "200", description = "Subjects returned", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
//            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))}
    )
    @GetMapping("/get/all")
    public ResponseEntity<ResponseDTO<Page<Subject>>> getAll(@RequestParam(required = false, defaultValue = "10") Integer size,
                                                             @RequestParam(required = false, defaultValue = "0") Integer page) {
        Sort sort = Sort.by(Sort.Direction.DESC, "mandatory")
                .and(Sort.by(Sort.Direction.DESC, "name"));
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Subject> subjectsList = subjectService.getAll(pageable);
        return ResponseEntity.ok(new ResponseDTO<>(subjectsList));
    }

    @Operation(summary = "For ADMINS , SUPER_ADMINS , This API is used for update Subjects"
//            , responses = {
//            @ApiResponse(responseCode = "200", description = "Subject updated", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
//            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))}
    )
    @PutMapping("/update")
    public ResponseEntity<ResponseDTO<Subject>> update(@RequestBody SubjectUpdateDTO dto) {
        Subject subject = subjectService.update(dto);
        return ResponseEntity.ok(new ResponseDTO<>(subject, "Subject Updated Successfully"));
    }

    @Operation(summary = "For ADMINS , SUPER_ADMINS , This API is used for delete Subject"
//            , responses = {
//            @ApiResponse(responseCode = "200", description = "Subject deleted", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
//            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))}
    )
    @DeleteMapping("/delete/{id:.*}")
    public ResponseEntity<ResponseDTO<Void>> delete(@PathVariable Integer id) {
        subjectService.delete(id);
        return ResponseEntity.ok(new ResponseDTO<>(null, "Subject Deleted Successfully"));
    }
    @Operation(summary = "For ADMINS , SUPER_ADMINS , This API is used to add count of questions for Subject for solve test"
//            , responses = {
//            @ApiResponse(responseCode = "200", description = "Added", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
//            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))}
    )
    @PostMapping("/add/count")
    public ResponseEntity<ResponseDTO<Void>> addCount(QuestionCountDTO dto) {
        subjectService.addCount(dto);
        return ResponseEntity.ok(new ResponseDTO<>(null, "Count added Successfully"));
    }

}

