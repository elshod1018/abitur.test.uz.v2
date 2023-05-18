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
import org.springframework.security.access.prepost.PreAuthorize;
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

import java.io.IOException;

import static uz.test.abitur.utils.UrlUtils.BASE_NEWS_URL;
import static uz.test.abitur.utils.UrlUtils.BASE_USERS_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_USERS_URL)
@PreAuthorize("isAuthenticated()")
@Tag(name = "Users Controller", description = "Users API")
public class UserController {

    private final AuthUserService authUserService;

    @Operation(summary = "For ADMINS , USERS , SUPER_ADMINS , This API is used for get a User for profile ", responses = {
            @ApiResponse(responseCode = "200", description = "User returned", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @GetMapping("/get/{id:.*}")
    public ResponseEntity<ResponseDTO<AuthUser>> get(@PathVariable String id) {
        AuthUser user = authUserService.findById(id);
        return ResponseEntity.ok(new ResponseDTO<>(user));
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @Operation(summary = "For SUPER_ADMINS , This API is used for get paged users", responses = {
            @ApiResponse(responseCode = "200", description = "Users returned", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @GetMapping("/get/all")
    public ResponseEntity<ResponseDTO<Page<AuthUser>>> getAll(@RequestParam(required = false) Status status,
                                                              @RequestParam(required = false, defaultValue = "15") Integer size,
                                                              @RequestParam(required = false, defaultValue = "0") Integer page) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<AuthUser> usersList = authUserService.getAll(status, pageable);
        return ResponseEntity.ok(new ResponseDTO<>(usersList));
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @Operation(summary = "For SUPER_ADMINS , This API is used for update users", responses = {
            @ApiResponse(responseCode = "200", description = "User updated", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @PutMapping("/update")
    public ResponseEntity<ResponseDTO<AuthUser>> updateByAdmin(@RequestBody UserUpdateDTO dto) {
        AuthUser user = authUserService.update(dto);
        return ResponseEntity.ok(new ResponseDTO<>(user, "User Updated Successfully"));
    }

    @Operation(summary = "For ADMINS , USERS , SUPER_ADMINS , This API is used for update profile", responses = {
            @ApiResponse(responseCode = "200", description = "Profile updated", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @PutMapping(value = "/profile/update")
    public ResponseEntity<ResponseDTO<AuthUser>> updateProfile(@RequestBody UserProfileUpdateDTO dto) throws IOException {
        AuthUser user = authUserService.updateProfile(dto);
        return ResponseEntity.ok(new ResponseDTO<>(user, "User Updated Successfully"));
    }

    @Operation(summary = "For ADMINS , USERS , SUPER_ADMINS , This API is used for update profile photo", responses = {
            @ApiResponse(responseCode = "200", description = "Profile updated", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @PutMapping(value = "/profile/photo", consumes = "multipart/form-data")
    public ResponseEntity<ResponseDTO<AuthUser>> updateProfilePhoto(@RequestParam MultipartFile file) throws IOException {
        AuthUser user = authUserService.updateProfilePhoto(file);
        return ResponseEntity.ok(new ResponseDTO<>(user, "User Updated Successfully"));
    }

    @Operation(summary = "For ADMINS , USERS , SUPER_ADMINS , This API is used for delete their accounts", responses = {
            @ApiResponse(responseCode = "200", description = "User deleted", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDTO<Void>> delete() {
        authUserService.delete();
        return ResponseEntity.ok(new ResponseDTO<>(null, "User Deleted Successfully"));
    }

}

