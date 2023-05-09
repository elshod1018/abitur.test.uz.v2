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
import uz.test.abitur.domains.News;
import uz.test.abitur.dtos.ResponseDTO;
import uz.test.abitur.dtos.news.NewsCreateDTO;
import uz.test.abitur.dtos.news.NewsUpdateDTO;
import uz.test.abitur.services.NewsService;

import java.util.List;

import static uz.test.abitur.utils.UrlUtils.BASE_NEWS_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_NEWS_URL)
@PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
@Tag(name = "News Controller", description = "News API")
public class NewsController {
    private final NewsService newsService;

    @Operation(summary = "This API is used for create a News", responses = {
            @ApiResponse(responseCode = "200", description = "News created", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO<News>> create(@Valid @RequestBody NewsCreateDTO dto) {
        News news = newsService.create(dto);
        return ResponseEntity.ok(new ResponseDTO<>(news, "News Created Successfully"));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'USER')")
    @Operation(summary = "This API is used for get an existing news", responses = {
            @ApiResponse(responseCode = "200", description = "Get News", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @GetMapping("/get/{id:.*}")
    public ResponseEntity<ResponseDTO<News>> get(@PathVariable Integer id) {
        News news = newsService.findById(id);
        return ResponseEntity.ok(new ResponseDTO<>(news));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'USER')")
    @Operation(summary = "This API is used for get paged news", responses = {
            @ApiResponse(responseCode = "200", description = "Returned news", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @GetMapping("/get/all")
    public ResponseEntity<ResponseDTO<Page<News>>> getAll(@RequestParam(required = false, defaultValue = "15") Integer size,
                                                          @RequestParam(required = false, defaultValue = "0") Integer page) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<News> newsList = newsService.getAll(pageable);
        return ResponseEntity.ok(new ResponseDTO<>(newsList));
    }

    @Operation(summary = "This API is used for update news", responses = {
            @ApiResponse(responseCode = "200", description = "News updated", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @PutMapping("/update")
    public ResponseEntity<ResponseDTO<News>> update(@RequestBody NewsUpdateDTO dto) {
        News news = newsService.update(dto);
        return ResponseEntity.ok(new ResponseDTO<>(news, "News Updated Successfully"));
    }

    @Operation(summary = "This API is used for delete news", responses = {
            @ApiResponse(responseCode = "200", description = "News delete", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @DeleteMapping("/delete/{id:.*}")
    public ResponseEntity<ResponseDTO<Void>> delete(@PathVariable Integer id) {
        newsService.delete(id);
        return ResponseEntity.ok(new ResponseDTO<>(null, "News Deleted Successfully"));
    }

}

