package tu.social.project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import tu.social.project.entity.CategoryEntity;
import tu.social.project.payload.request.LoginUserRequest;
import tu.social.project.payload.request.RegisterUserRequest;
import tu.social.project.payload.response.LoginUserResponse;
import tu.social.project.payload.response.RegisterUserResponse;
import tu.social.project.service.UserService;

@RequestMapping("/user")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Created user", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryEntity.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid email, username or password", content = @Content),
    })
    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponse> register(@RequestBody RegisterUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.userService.register(request));
    }

    @Operation(summary = "Login into an existing user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logged into a user", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryEntity.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid email, username or password", content = @Content),
    })
    @GetMapping("/login")
    public ResponseEntity<LoginUserResponse> login(@RequestBody LoginUserRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.userService.login(request));
    }
}
