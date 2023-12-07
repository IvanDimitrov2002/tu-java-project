package tu.social.project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponse> register(@RequestBody RegisterUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.userService.register(request));
    }

    @GetMapping("/login")
    public ResponseEntity<LoginUserResponse> login(@RequestBody LoginUserRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.userService.login(request));
    }
}
