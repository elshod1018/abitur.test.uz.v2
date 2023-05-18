package uz.test.abitur.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.test.abitur.domains.Document;
import uz.test.abitur.dtos.ResponseDTO;
import uz.test.abitur.services.DocumentService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static uz.test.abitur.utils.UrlUtils.BASE_DOCUMENTS_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_DOCUMENTS_URL)
@PreAuthorize("isAuthenticated()")
@Tag(name = "Documents Controller", description = "Documents API")
public class DocumentController {
    private final DocumentService documentService;

    @Operation(summary = "For AUTHENTICATED users, This API is used for download documents", responses = {
            @ApiResponse(responseCode = "200", description = "Document returned", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @GetMapping("/download/{name:.*}")
    public ResponseEntity<InputStreamResource> download(@PathVariable String name) throws IOException {
        File file = documentService.downloadFile(name);
        Document document = documentService.findByGeneratedName(name);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + document.getOriginalName())
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @Operation(summary = "For AUTHENTICATED users, This API is used for get documents", responses = {
            @ApiResponse(responseCode = "200", description = "Document returned", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @GetMapping("/get/{id:.*}")
    public ResponseEntity<ResponseDTO<Document>> download(@PathVariable Integer id) throws IOException {
        Document document = documentService.getById(id);
        return ResponseEntity.ok(new ResponseDTO<>(document));
    }
}

