package com.edu.kobridge.module.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edu.kobridge.global.common.DataResponseDto;
import com.edu.kobridge.global.common.ResponseDto;
import com.edu.kobridge.global.enums.LangType;
import com.edu.kobridge.module.user.domain.entity.User;
import com.edu.kobridge.module.user.dto.req.SignUpReqDto;
import com.edu.kobridge.module.user.dto.res.LoginResDto;
import com.edu.kobridge.module.user.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserController implements UserControllerDocs {
	private final UserService userService;

	@GetMapping("/google-login")
	public ResponseEntity<ResponseDto> getGoogleLogin(@RequestHeader("id-token") String idToken) {
		LoginResDto resDto = userService.getGoogleLogin(idToken);
		return ResponseEntity.status(201).body(DataResponseDto.of(resDto, 201));
	}

	@GetMapping("/token")
	public ResponseEntity<ResponseDto> getAccessToken(@RequestHeader("Authorization-Refresh") String refreshToken) {
		LoginResDto resDto = userService.getAccessToken(refreshToken);
		return ResponseEntity.status(201).body(DataResponseDto.of(resDto, 201));
	}

	@PostMapping
	public ResponseEntity<ResponseDto> postSignUp(@RequestAttribute("user") User user,
		@RequestBody @Valid SignUpReqDto signUpReq) {
		userService.postSignUp(user, signUpReq);
		return ResponseEntity.ok(ResponseDto.of(200));
	}

	@GetMapping
	public ResponseEntity<ResponseDto> getUserInfo(@RequestAttribute("user") User user) {
		return ResponseEntity.status(200).body(DataResponseDto.of(userService.getUserInfo(user), 200));
	}

	@PatchMapping("/lang")
	public ResponseEntity<ResponseDto> patchLang(@RequestAttribute("user") User user, LangType lang) {
		userService.updateLang(user, lang);
		return ResponseEntity.ok(ResponseDto.of(200));
	}

	@PatchMapping("level")
	public ResponseEntity<ResponseDto> patchLevel(@RequestAttribute("user") User user) {
		userService.updateLevel(user);
		return ResponseEntity.ok(ResponseDto.of(200));
	}

	@DeleteMapping("/google-logout")
	public ResponseEntity<ResponseDto> deleteGoogleLogout(@RequestAttribute("user") User user) {
		userService.deleteGoogleLogout(user);
		return ResponseEntity.ok(ResponseDto.of(200));
	}

}
