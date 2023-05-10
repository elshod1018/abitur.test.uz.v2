package uz.test.abitur.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.test.abitur.dtos.ResponseDTO;
import uz.test.abitur.dtos.subject.SubjectCreateDTO;

import static uz.test.abitur.utils.UrlUtils.BASE_TESTS_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_TESTS_URL)
@Tag(name = "Test Controller", description = "Test API")
public class TestController {
    @Operation(summary = "This API is used for test", responses = {
            @ApiResponse(responseCode = "200", description = "Test Started", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @PostMapping("/start")
    public ResponseEntity<ResponseDTO<Void>> create(@Valid @RequestBody SubjectCreateDTO dto) {

        return ResponseEntity.ok(new ResponseDTO<>(null, "Subject Created Successfully"));
    }
}

