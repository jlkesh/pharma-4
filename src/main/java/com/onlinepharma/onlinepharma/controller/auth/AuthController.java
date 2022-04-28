package com.onlinepharma.onlinepharma.controller.auth;

import com.onlinepharma.onlinepharma.controller.AbstractController;
import com.onlinepharma.onlinepharma.dto.auth.LoginDto;
import com.onlinepharma.onlinepharma.dto.auth.SessionDto;
import com.onlinepharma.onlinepharma.dto.response.AppErrorDto;
import com.onlinepharma.onlinepharma.dto.response.DataDto;
import com.onlinepharma.onlinepharma.service.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Auth Controller", description = "APIs for getting access and refresh tokens")
public class AuthController extends AbstractController<AuthService> {
    public AuthController(AuthService service) {
        super(service);
    }


    @Operation(summary = "Generate Access and Refresh token with expiratins")
    @ApiResponse(
            responseCode = "200",
            description = "If there is no errors",
            content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SessionDto.class))
            }
    )
    @ApiResponse(
            responseCode = "400",
            description = "Bad request",
            content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AppErrorDto.class))
            }
    )
    @RequestMapping(value = PATH + "/auth/access/token", method = RequestMethod.POST)
    public ResponseEntity<String> login(@RequestBody LoginDto dto) {
        return service.login(dto);
    }

}
