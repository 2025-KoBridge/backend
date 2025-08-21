package com.edu.kobridge.module.lesson.controller;

import org.springframework.http.ResponseEntity;

import com.edu.kobridge.global.common.ResponseDto;
import com.edu.kobridge.module.lesson.dto.req.ChatCorrectionReqDto;
import com.edu.kobridge.module.lesson.dto.req.PronunciationEvaluationReqDto;
import com.edu.kobridge.module.user.domain.entity.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Lesson", description = "학습 관련 API")
public interface LessonControllerDocs {

	@Operation(summary = "레슨 상세 조회", description = "레슨 별 문장과 주제를 확인할 수 있습니다.")
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
					+ "    \"subject\": \"오늘은 학교에 가는 상황에서 자주 쓰는 표현들을 배워봐요. 교실에서 친구와 이야기할 때 유용하게 쓸 수 있어요.\",\n"
					+ "    \"lessonSentences\": [\n"
					+ "      {\n"
					+ "        \"id\": 1,\n"
					+ "        \"sentence\": \"우리 1교시 뭐야?\",\n"
					+ "        \"translation\": \"What is our first class?\",\n"
					+ "        \"pronunciation\": \"Uri il-gyosi mwoya?\"\n"
					+ "      },\n"
					+ "      {\n"
					+ "        \"id\": 2,\n"
					+ "        \"sentence\": \"수업 언제 시작해?\",\n"
					+ "        \"translation\": \"When does the class start?\",\n"
					+ "        \"pronunciation\": \"Sueop eonje sijakhae?\"\n"
					+ "      },\n"
					+ "      {\n"
					+ "        \"id\": 3,\n"
					+ "        \"sentence\": \"오늘 숙제 다 했어?\",\n"
					+ "        \"translation\": \"Did you finish today’s homework?\",\n"
					+ "        \"pronunciation\": \"Oneul sukjje da haesseo?\"\n"
					+ "      }\n"
					+ "    ],\n"
					+ "    \"startChat\": {\n"
					+ "      \"id\": 3,\n"
					+ "      \"question\": \"안녕~ 오늘 첫 수업 뭐였지?\",\n"
					+ "      \"questionTrans\": \"Hi~ What was the first class today?\"\n"
					+ "    }\n"
					+ "  }\n"
					+ "}")
			)
		),
		@ApiResponse(responseCode = "404", description = "해당 자원을 찾을 수 없습니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ResponseDto.class),
				examples = @ExampleObject(value = "[" +
					"{ \"code\": 401, \"message\": \"해당하는 레슨을 찾을 수 없습니다.\" }," +
					"{ \"code\": 404, \"message\": \"해당하는 질문을 찾을 수 없습니다.\" }" +
					"]"
				)
			)
		)
	})
	public ResponseEntity<ResponseDto> getLessonDetail(User user, Long id);

	@Operation(summary = "레슨 전체 조회", description = "전체 레슨 리스트를 확인할 수있습니다.")
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
					+ "    \"level\": 1,\n"
					+ "    \"lessons\": [\n"
					+ "      {\n"
					+ "        \"id\": 1,\n"
					+ "        \"number\": 1,\n"
					+ "        \"title\": \"학교에 가자!\",\n"
					+ "        \"subTitle1\": \"사용할 문장 알아볼까?\",\n"
					+ "        \"subTitle2\": \"같이 대화해볼까?\",\n"
					+ "        \"subTitle3\": \"오늘의 레슨은 어땠어?\"\n"
					+ "      },\n"
					+ "      {\n"
					+ "        \"id\": 2,\n"
					+ "        \"number\": 2,\n"
					+ "        \"title\": \"친구랑 인사하기\",\n"
					+ "        \"subTitle1\": \"친구랑 어떻게 말할까?\",\n"
					+ "        \"subTitle2\": \"인사 연습해보자!\",\n"
					+ "        \"subTitle3\": \"오늘 배운 인사, 어땠어?\"\n"
					+ "      }, ...\n"
					+ "      \n"
					+ "      {\n"
					+ "        \"id\": 15,\n"
					+ "        \"number\": 15,\n"
					+ "        \"title\": \"미래 꿈 이야기\",\n"
					+ "        \"subTitle1\": \"꿈을 어떻게 말할까?\",\n"
					+ "        \"subTitle2\": \"내 꿈 얘기해보자!\",\n"
					+ "        \"subTitle3\": \"꿈 이야기 표현은 쉬웠어?\"\n"
					+ "      }\n"
					+ "    ]\n"
					+ "  }\n"
					+ "}")
			)
		)
	})
	public ResponseEntity<ResponseDto> getLessonList(User user);

	@Operation(summary = "발음 평가", description = "발음에 대한 별점을 확인할 수 있습니다.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Ok",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ResponseDto.class),
				examples =
				@ExampleObject(value = "{\n"
					+ "  \"code\": 200,\n"
					+ "  \"message\": \"OK\",\n"
					+ "  \"data\": 3\n"
					+ "}")
			)
		),
		@ApiResponse(responseCode = "404", description = "해당 자원을 찾을 수 없습니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ResponseDto.class),
				examples = @ExampleObject(value = "{ \"code\": 404, \"message\": \"해당하는 레슨 문장을 찾을 수 없습니다.\" }"
				)
			)
		)
	})
	public ResponseEntity<ResponseDto> postPronunciationEvaluationValue(Long id,
		PronunciationEvaluationReqDto audioUrl);

	@Operation(summary = "대화 답변 분석 및 다음 질문 확인", description = "사용자 답변의 번역, 교정본, 이유 및 다음 답변의 질문, 번역본 등을 제공한다.")
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
					+ "    \"answerTrans\": \"So, what is the first class today?\",\n"
					+ "    \"correction\": \"그러게 오늘 1교시가 뭐지?\",\n"
					+ "    \"reason\": \"‘워지’는 맞지 않아서 ‘뭐지’로 수정했어요. 그리고 1굣은 1교시로 바꿔야 해요.\",\n"
					+ "    \"response\": \"응, 오늘 1교시 수업 국어야!\",\n"
					+ "    \"nextChat\": {\n"
					+ "      \"id\": 2,\n"
					+ "      \"question\": \"우리 쉬는 시간 거의 끝났다..\",\n"
					+ "      \"questionTrans\": \"Our break is almost over..\"\n"
					+ "    }\n"
					+ "  }\n"
					+ "}")
			)
		),
		@ApiResponse(responseCode = "404", description = "해당 자원을 찾을 수 없습니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ResponseDto.class),
				examples = @ExampleObject(value = "{ \"code\": 404, \"message\": \"해당하는 질문을 찾을 수 없습니다.\" }"
				)
			)
		)
	})
	public ResponseEntity<ResponseDto> postChatCorrection(Long id, ChatCorrectionReqDto chatCorrectionReq);

}

