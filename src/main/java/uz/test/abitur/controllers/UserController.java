package uz.test.abitur.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.http.auth.AUTH;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.test.abitur.domains.AuthUser;
import uz.test.abitur.domains.News;
import uz.test.abitur.dtos.ResponseDTO;
import uz.test.abitur.dtos.news.NewsCreateDTO;
import uz.test.abitur.dtos.news.NewsUpdateDTO;
import uz.test.abitur.dtos.user.UserProfileUpdateDTO;
import uz.test.abitur.dtos.user.UserUpdateDTO;
import uz.test.abitur.enums.Status;
import uz.test.abitur.services.AuthUserService;
import uz.test.abitur.services.NewsService;

import static uz.test.abitur.utils.UrlUtils.BASE_NEWS_URL;
import static uz.test.abitur.utils.UrlUtils.BASE_USERS_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_USERS_URL)
@Tag(name = "Users Controller", description = "Users API")
public class UserController {

    private final AuthUserService authUserService;

    @Operation(summary = "This API is used for get a User", responses = {
            @ApiResponse(responseCode = "200", description = "User returned", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @GetMapping("/get/{id:.*}")
    public ResponseEntity<ResponseDTO<AuthUser>> get(@PathVariable String id) {
        AuthUser user = authUserService.findById(id);
        return ResponseEntity.ok(new ResponseDTO<>(user));
    }

    @Operation(summary = "This API is used for get paged users", responses = {
            @ApiResponse(responseCode = "200", description = "Users returned", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @GetMapping("/get/all")
    public ResponseEntity<ResponseDTO<Page<AuthUser>>> getAll(@RequestParam(required = false, defaultValue = "15") Integer size,
                                                              @RequestParam(required = false, defaultValue = "0") Integer page) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<AuthUser> usersList = authUserService.getAll(pageable);
        return ResponseEntity.ok(new ResponseDTO<>(usersList));
    }

    @Operation(summary = "This API is used for update users", responses = {
            @ApiResponse(responseCode = "200", description = "User updated", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @PutMapping("/update")
    public ResponseEntity<ResponseDTO<AuthUser>> updateByAdmin(@RequestBody UserUpdateDTO dto) {
        AuthUser user = authUserService.update(dto);
        return ResponseEntity.ok(new ResponseDTO<>(user, "User Updated Successfully"));
    }

    @Operation(summary = "This API is used for update profile", responses = {
            @ApiResponse(responseCode = "200", description = "Profile updated", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @PutMapping("/profile/update")
    public ResponseEntity<ResponseDTO<AuthUser>> updateProfile(@RequestBody UserProfileUpdateDTO dto, MultipartFile file) {
        AuthUser user = authUserService.updateProfile(dto);
        return ResponseEntity.ok(new ResponseDTO<>(user, "User Updated Successfully"));
    }

    @Operation(summary = "This API is used for delete users", responses = {
            @ApiResponse(responseCode = "200", description = "User deleted", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @DeleteMapping("/delete/{id:.*}")
    public ResponseEntity<ResponseDTO<Void>> delete(@PathVariable String id) {
        authUserService.delete(id);
        return ResponseEntity.ok(new ResponseDTO<>(null, "User Deleted Successfully"));
    }

}

