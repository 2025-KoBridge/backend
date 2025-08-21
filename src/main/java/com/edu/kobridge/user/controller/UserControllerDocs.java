package com.edu.kobridge.user.controller;

import org.springframework.http.ResponseEntity;

import com.edu.kobridge.global.common.ResponseDto;
import com.edu.kobridge.global.enums.LangType;
import com.edu.kobridge.user.domain.entity.User;
import com.edu.kobridge.user.dto.req.SignUpReqDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "User", description = "사용자 관련 API")
public interface UserControllerDocs {

	@Operation(summary = "로그인", description = "구글 로그인을 진행합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "201", description = "Created",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ResponseDto.class),
				examples =
				@ExampleObject(value = "{ \"code\": 201, \"message\": \"Created\" }")
			)
		),
		@ApiResponse(responseCode = "400", description = "잘못된 요청입니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ResponseDto.class),
				examples = @ExampleObject(value = "{ \"code\": 400, \"message\": \"Id Token이 필요합니다.\" },"
				)
			)
		),
		@ApiResponse(responseCode = "401", description = "인증에 실패하였습니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ResponseDto.class),
				examples = @ExampleObject(value = "[" +
					"{ \"code\": 401, \"message\": \"인증에 실패하였습니다.\" }," +
					"{ \"code\": 401, \"message\": \"Token이 유효하지 않습니다.\" }," +
					"{ \"code\": 401, \"message\": \"Access Token이 필요합니다.\" }," +
					"{ \"code\": 404, \"message\": \"해당하는 사용자를 찾을 수 없습니다.\" }" +
					"]"
				)
			)
		),
		@ApiResponse(responseCode = "403", description = "접근이 허용되지 않습니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ResponseDto.class),
				examples = @ExampleObject(value = "{ \"code\": 403, \"message\": \"Token이 만료되었습니다.\" }")
			)
		),
		@ApiResponse(responseCode = "404", description = "해당 자원을 찾을 수 없습니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ResponseDto.class),
				examples = @ExampleObject(value = "{ \"code\": 404, \"message\": \"해당하는 사용자를 찾을 수 없습니다.\" }")
			)
		)
	})
	ResponseEntity<ResponseDto> getGoogleLogin(String idToken);

	@Operation(summary = "accessToken 발급", description = "리프레시 토큰을 이용해 엑세스 토큰을 발급합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "201", description = "Created",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ResponseDto.class),
				examples =
				@ExampleObject(value = "{ \"code\": 201, \"message\": \"Created\" }")
			)
		),
		@ApiResponse(responseCode = "401", description = "인증에 실패하였습니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ResponseDto.class),
				examples = @ExampleObject(value = "[" +
					"{ \"code\": 401, \"message\": \"인증에 실패하였습니다.\" }," +
					"{ \"code\": 401, \"message\": \"Token이 유효하지 않습니다.\" }," +
					"{ \"code\": 401, \"message\": \"Refresh Token이 필요합니다.\" }" +
					"]"
				)
			)
		),
		@ApiResponse(responseCode = "403", description = "접근이 허용되지 않습니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ResponseDto.class),
				examples = @ExampleObject(value = "{ \"code\": 403, \"message\": \"Token이 만료되었습니다.\" }")
			)
		)
	})
	ResponseEntity<ResponseDto> getAccessToken(String refreshToken);

	@Operation(summary = "회원가입", description = "사용자 회원가입을 진행합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Ok",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ResponseDto.class),
				examples =
				@ExampleObject(value = "{ \"code\": 200, \"message\": \"Ok\" }")
			)
		),
		@ApiResponse(responseCode = "400", description = "잘못된 요청입니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ResponseDto.class),
				examples = @ExampleObject(value = "[" +
					"{ \"code\": 400, \"message\": \"이름은 필수 값입니다.\" }, " +
					"{ \"code\": 400, \"message\": \"이름은 2자이상 50자 미만이어야 합니다.\" }," +
					"{ \"code\": 400, \"message\": \"나이는 1 이상이어야 합니다.\" }," +
					"{ \"code\": 400, \"message\": \"나이는 100 이하여야 합니다.\" }," +
					"{ \"code\": 400, \"message\": \"역할은 필수 값입니다.\" }," +
					"{ \"code\": 400, \"message\": \"학교는 필수 값입니다.\" }," +
					"{ \"code\": 400, \"message\": \"학년은 1 이상이어야 합니다.\" }," +
					"{ \"code\": 400, \"message\": \"학년은 6 이하여야 합니다.\" }" +
					"{ \"code\": 400, \"message\": \"중학생, 고등학생은 3학년까지만 존재합니다.\" }" +
					"{ \"code\": 400, \"message\": \"음성은 필수 값입니다.\" }" +
					"]"
				)
			)
		),
		@ApiResponse(responseCode = "401", description = "인증에 실패하였습니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ResponseDto.class),
				examples = @ExampleObject(value = "[" +
					"{ \"code\": 401, \"message\": \"인증에 실패하였습니다.\" }," +
					"{ \"code\": 401, \"message\": \"Token이 유효하지 않습니다.\" }," +
					"{ \"code\": 401, \"message\": \"Access Token이 필요합니다.\" }" +
					"]"
				)
			)
		),
		@ApiResponse(responseCode = "403", description = "접근이 허용되지 않습니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ResponseDto.class),
				examples = @ExampleObject(value = "{ \"code\": 403, \"message\": \"Token이 만료되었습니다.\" }")
			)
		)
	})
	ResponseEntity<ResponseDto> postSignUp(User user, SignUpReqDto signUpReq);

	@Operation(summary = "사용자 정보 확인", description = "사용자의 이름과 번역 언어, 레벨을 확인합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Ok",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ResponseDto.class),
				examples =
				@ExampleObject(value = "{\n"
					+ "  \"code\": 200,\n"
					+ "  \"message\": \"OK\",\n"
					+ "  \"data\": {\n"
					+ "    \"name\": \"김철수\",\n"
					+ "    \"lang\": \"ENG\",\n"
					+ "    \"level\": 1\n"
					+ "  }\n"
					+ "}")
			)
		),
		@ApiResponse(responseCode = "401", description = "인증에 실패하였습니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ResponseDto.class),
				examples = @ExampleObject(value = "[" +
					"{ \"code\": 401, \"message\": \"인증에 실패하였습니다.\" }," +
					"{ \"code\": 401, \"message\": \"Token이 유효하지 않습니다.\" }," +
					"{ \"code\": 401, \"message\": \"Access Token이 필요합니다.\" }" +
					"]"
				)
			)
		)
	})
	ResponseEntity<ResponseDto> getUserInfo(User user);

	@Operation(summary = "번역 언어 변경", description = "사용하는 번역 언어를 변경합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Ok",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ResponseDto.class),
				examples =
				@ExampleObject(value = "{ \"code\": 200, \"message\": \"Ok\" }")
			)
		),
		@ApiResponse(responseCode = "400", description = "잘못된 요청입니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ResponseDto.class),
				examples = @ExampleObject(value = "{ \"code\": 400, \"message\": \"LANG_TYPE 의 값이 아닙니다.\" }"
				)
			)
		),
		@ApiResponse(responseCode = "401", description = "인증에 실패하였습니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ResponseDto.class),
				examples = @ExampleObject(value = "[" +
					"{ \"code\": 401, \"message\": \"인증에 실패하였습니다.\" }," +
					"{ \"code\": 401, \"message\": \"Token이 유효하지 않습니다.\" }," +
					"{ \"code\": 401, \"message\": \"Access Token이 필요합니다.\" }" +
					"]"
				)
			)
		)
	})
	ResponseEntity<ResponseDto> patchLang(User user, LangType lang);

	@Operation(summary = "레벨 완료", description = "레벨을 완료하여 1단게 높아집니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Ok",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ResponseDto.class),
				examples =
				@ExampleObject(value = "{ \"code\": 200, \"message\": \"Ok\" }")
			)
		),
		@ApiResponse(responseCode = "401", description = "인증에 실패하였습니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ResponseDto.class),
				examples = @ExampleObject(value = "[" +
					"{ \"code\": 401, \"message\": \"인증에 실패하였습니다.\" }," +
					"{ \"code\": 401, \"message\": \"Token이 유효하지 않습니다.\" }," +
					"{ \"code\": 401, \"message\": \"Access Token이 필요합니다.\" }" +
					"]"
				)
			)
		)
	})
	ResponseEntity<ResponseDto> patchLevel(User user);

	@Operation(summary = "로그아웃", description = "로그아웃을 진행합니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Ok",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ResponseDto.class),
				examples =
				@ExampleObject(value = "{ \"code\": 200, \"message\": \"Ok\" }")
			)
		),
		@ApiResponse(responseCode = "401", description = "인증에 실패하였습니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ResponseDto.class),
				examples = @ExampleObject(value = "[" +
					"{ \"code\": 401, \"message\": \"인증에 실패하였습니다.\" }," +
					"{ \"code\": 401, \"message\": \"Token이 유효하지 않습니다.\" }," +
					"{ \"code\": 401, \"message\": \"Access Token이 필요합니다.\" }" +
					"]"
				)
			)
		)
	})
	ResponseEntity<ResponseDto> deleteGoogleLogout(User user);

}

