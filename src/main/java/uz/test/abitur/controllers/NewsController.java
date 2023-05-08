package uz.test.abitur.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.test.abitur.domains.News;
import uz.test.abitur.dtos.ResponseDTO;
import uz.test.abitur.dtos.news.NewsCreateDTO;
import uz.test.abitur.dtos.news.NewsUpdateDTO;
import uz.test.abitur.services.NewsService;

import static uz.test.abitur.utils.UrlUtils.BASE_NEWS_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_NEWS_URL)
@Tag(name = "News Controller", description = "News API")
public class NewsController {
    private final NewsService newsService;

    @Operation(summary = "This API is used for create a News", responses = {
            @ApiResponse(responseCode = "200", description = "News created", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO<News>> create(@Valid NewsCreateDTO dto) {
        News news = newsService.create(dto);
        return ResponseEntity.ok(new ResponseDTO<>(news, "News Created Successfully"));
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
            @ApiResponse(responseCode = "200", description = "News deleted", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDTO<Void>> update(@Valid Integer id) {
        newsService.delete(id);
        return ResponseEntity.ok(new ResponseDTO<>(null, "News Deleted Successfully"));
    }
}

