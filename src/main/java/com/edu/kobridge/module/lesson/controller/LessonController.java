package com.edu.kobridge.module.lesson.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edu.kobridge.global.common.DataResponseDto;
import com.edu.kobridge.global.common.ResponseDto;
import com.edu.kobridge.module.lesson.dto.req.ChatCorrectionReqDto;
import com.edu.kobridge.module.lesson.dto.req.PronunciationEvaluationReqDto;
import com.edu.kobridge.module.lesson.dto.res.ChatCorrectionResDto;
import com.edu.kobridge.module.lesson.dto.res.LessonListResDto;
import com.edu.kobridge.module.lesson.dto.res.LessonSentenceListResDto;
import com.edu.kobridge.module.lesson.service.LessonService;
import com.edu.kobridge.module.user.domain.entity.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/lesson")
@RequiredArgsConstructor
@Slf4j
public class LessonController implements LessonControllerDocs {
	private final LessonService lessonService;

	@GetMapping("/{id}")
	public ResponseEntity<ResponseDto> getLessonDetail(@RequestAttribute("user") User user,
		@PathVariable("id") Long id) {
		LessonSentenceListResDto resDto = lessonService.getLessonDetail(user, id);
		return ResponseEntity.status(200).body(DataResponseDto.of(resDto, 200));
	}

	@GetMapping
	public ResponseEntity<ResponseDto> getLessonList(@RequestAttribute("user") User user) {
		LessonListResDto resDto = lessonService.getLessonList(user);
		return ResponseEntity.status(200).body(DataResponseDto.of(resDto, 200));
	}

	@PostMapping("/sentence/{id}/pronunciation-evaluation")
	public ResponseEntity<ResponseDto> postPronunciationEvaluationValue(@PathVariable("id") Long id,
		@RequestBody PronunciationEvaluationReqDto pronunciationEvaluationReq) {
		Integer res = lessonService.postPronunciationEvaluationValue(id, pronunciationEvaluationReq);
		return ResponseEntity.status(200).body(DataResponseDto.of(res, 200));
	}

	@PostMapping("/chat/{id}/correction")
	public ResponseEntity<ResponseDto> postChatCorrection(@PathVariable("id") Long id,
		@RequestBody ChatCorrectionReqDto chatCorrectionReq) {
		ChatCorrectionResDto resDto = lessonService.postChatCorrection(id, chatCorrectionReq);
		return ResponseEntity.status(200).body(DataResponseDto.of(resDto, 200));
	}
}
