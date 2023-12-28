package com.springboot.blog.controller;

import com.springboot.blog.payload.JwtAuthResponse;
import com.springboot.blog.payload.LoginDto;
import com.springboot.blog.payload.RegisterDto;
import com.springboot.blog.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(
        name="CRUD Rest Apis for Auth Login Resource"
)
public class AuthController {


  private AuthService authService;

    public AuthController(AuthService authService) {

        this.authService = authService;
    }


    //Build Login Rest Api

    @Operation(
            summary = "  Login or signin  Rest Api",
            description = "Login and Sign in  Rest Api is Used to Authenticated username and password present in database or not "
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )

    @PostMapping(value={"/login","/signin"})
    public ResponseEntity<JwtAuthResponse> login( @RequestBody LoginDto loginDto){

       String token = authService.login(loginDto);
       JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
       jwtAuthResponse.setAccessToken(token);
       return ResponseEntity.ok(jwtAuthResponse);

    }

    //Build Register Rest Api
    @Operation(
            summary = "  Register or Signup   Rest Api",
            description = "Register and Signup  Rest Api is Used to Register or add new User in database "
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 OK"
    )
   @PostMapping(value={"/register","/signup"})
    public ResponseEntity<String> registerUser(@RequestBody RegisterDto registerDto){


       String response=  authService.register(registerDto);

       return   new ResponseEntity<>(response,HttpStatus.CREATED);

    }


}
