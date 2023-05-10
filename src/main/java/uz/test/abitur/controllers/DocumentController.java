package uz.test.abitur.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.test.abitur.domains.AuthUser;
import uz.test.abitur.domains.Document;
import uz.test.abitur.dtos.ResponseDTO;
import uz.test.abitur.dtos.user.UserProfileUpdateDTO;
import uz.test.abitur.dtos.user.UserUpdateDTO;
import uz.test.abitur.services.AuthUserService;
import uz.test.abitur.services.DocumentService;

import java.io.File;
import java.io.IOException;

import static uz.test.abitur.utils.UrlUtils.BASE_DOCUMENTS_URL;
import static uz.test.abitur.utils.UrlUtils.BASE_USERS_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_DOCUMENTS_URL)
@PreAuthorize("isAuthenticated()")
@Tag(name = "Documents Controller", description = "Documents API")
public class DocumentController {
    private final DocumentService documentService;

    @Operation(summary = "This API is used for download documents", responses = {
            @ApiResponse(responseCode = "200", description = "Document returned", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @GetMapping("/download/{name:.*}")
    public ResponseEntity<ResponseDTO<File>> download(@PathVariable String name) throws IOException {
        File file = documentService.downloadFile(name);
        return ResponseEntity.ok(new ResponseDTO<>(file));
    }
    @Operation(summary = "This API is used for get documents", responses = {
            @ApiResponse(responseCode = "200", description = "Document returned", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @GetMapping("/get/{id:.*}")
    public ResponseEntity<ResponseDTO<Document>> download(@PathVariable Integer id) throws IOException {
        Document document = documentService.getFile(id);
        return ResponseEntity.ok(new ResponseDTO<>(document));
    }
}

