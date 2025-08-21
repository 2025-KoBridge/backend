package com.edu.kobridge.module.lesson.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edu.kobridge.global.enums.LangType;
import com.edu.kobridge.global.error.exception.AppException;
import com.edu.kobridge.global.util.FileUtil;
import com.edu.kobridge.infra.api.chatgpt.ChatGptService;
import com.edu.kobridge.infra.api.chatgpt.res.ChatGptCorrectionResDto;
import com.edu.kobridge.infra.api.epretx.PronunciationEvaluationService;
import com.edu.kobridge.module.lesson.domain.entity.Lesson;
import com.edu.kobridge.module.lesson.domain.entity.LessonChat;
import com.edu.kobridge.module.lesson.domain.entity.LessonSentence;
import com.edu.kobridge.module.lesson.domain.repository.LessonChatRepository;
import com.edu.kobridge.module.lesson.domain.repository.LessonRepository;
import com.edu.kobridge.module.lesson.domain.repository.LessonSentenceRepository;
import com.edu.kobridge.module.lesson.dto.req.ChatCorrectionReqDto;
import com.edu.kobridge.module.lesson.dto.req.PronunciationEvaluationReqDto;
import com.edu.kobridge.module.lesson.dto.res.ChatCorrectionResDto;
import com.edu.kobridge.module.lesson.dto.res.ChatResDto;
import com.edu.kobridge.module.lesson.dto.res.LessonBriefResDto;
import com.edu.kobridge.module.lesson.dto.res.LessonListResDto;
import com.edu.kobridge.module.lesson.dto.res.LessonSentenceListResDto;
import com.edu.kobridge.module.lesson.dto.res.LessonSentenceResDto;
import com.edu.kobridge.module.lesson.error.LessonErrorCode;
import com.edu.kobridge.module.user.domain.entity.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class LessonService {
	private final LessonRepository lessonRepository;
	private final LessonSentenceRepository lessonSentenceRepository;
	private final LessonChatRepository lessonChatRepository;
	private final PronunciationEvaluationService pronunciationEvaluationService;
	private final ChatGptService chatGptService;
	private final FileUtil fileUtil;

	// 레슨 상세 조회
	public LessonSentenceListResDto getLessonDetail(User user, Long id) {
		// lesson 번호 유효성 검증
		Lesson lesson = lessonRepository.findById(id)
			.orElseThrow(() -> new AppException(LessonErrorCode.LESSON_NOT_FOUND));

		// lesson sentence list 변환하여 dto 에 담기
		List<LessonSentenceResDto> lessonSentences = lesson.getSentences().stream()
			.map(sentence -> LessonSentenceResDto.of(
				sentence.getId(),
				sentence.getContentKo(),
				sentence.getTransByLang(user.getLang()),
				sentence.getPronunciationByLang(user.getLang())
			))
			.toList();

		// lesson sentence 첫번째로 발화시킬 lesson chat 가져오기
		LessonChat lessonChat = lessonChatRepository.findByLessonSentenceId(lessonSentences.get(0).getId())
			.orElseThrow(() -> new AppException(LessonErrorCode.LESSON_CHAT_NOT_FOUND));

		return LessonSentenceListResDto.of(lesson.getSubject(), lessonSentences,
			ChatResDto.of(lessonChat.getId(), lessonChat.getContentKo(), lessonChat.getTransByLang(user.getLang())));
	}

	// 레슨 전체 간단 조회
	public LessonListResDto getLessonList(User user) {
		// TODO entity 변경에 따라 trans 부분 추가로 매핑해서 전달하기

		return LessonListResDto.of(
			user.getLevel(),
			lessonRepository.findAllByOrderByNumberAsc().stream()
				.map(lesson -> LessonBriefResDto.of(
					lesson.getId(),
					lesson.getNumber(),
					lesson.getTitle(),
					lesson.getSubTitle1(),
					lesson.getSubTitle2(),
					lesson.getSubTitle3()
				))
				.toList()
		);
	}

	// 발음 평가
	public Integer postPronunciationEvaluationValue(Long id, PronunciationEvaluationReqDto pronunciationEvaluationReq) {
		// sentence id 검증
		LessonSentence lessonSentence = lessonSentenceRepository.findById(id)
			.orElseThrow(() -> new AppException(LessonErrorCode.LESSON_SENTENCE_NOT_FOUND));

		// audio url 을 base 64 값으로 전환
		String audioBase64Url = fileUtil.convertS3UrlToBase64(pronunciationEvaluationReq.audioUrl());
		log.error(audioBase64Url);

		// 외부 api 호출
		float score = pronunciationEvaluationService
			.checkPronunciation(lessonSentence.getContentKo(), audioBase64Url)
			.getReturn_object()
			.getScore();

		// 소수점 첫째 자리에서 반올림 → 정수
		return Math.round(score);
	}

	// 대화 답변 교정 및 전달
	public ChatCorrectionResDto postChatCorrection(Long id, ChatCorrectionReqDto chatCorrectionReq) {
		// chat id 검증
		LessonChat lessonChat = lessonChatRepository.findById(id)
			.orElseThrow(() -> new AppException(LessonErrorCode.LESSON_CHAT_NOT_FOUND));

		// 다음 채팅 정보 가져오기
		LessonChat nextChat = lessonChat.getNextChat();
		Boolean isNextChatExist = nextChat != null;

		// TODO : user 받아와서 Lang 매핑

		// chat gpt 호출 및 응답 확인
		ChatGptCorrectionResDto chatGptCorrectionRes = chatGptService.postAnswerCorrectionAndResponse(
			chatCorrectionReq.answer(), isNextChatExist, LangType.ENG);

		// 결과 반환
		return ChatCorrectionResDto.of(
			chatGptCorrectionRes.getTranslation(),
			chatGptCorrectionRes.getCorrection(),
			chatGptCorrectionRes.getReason(),
			chatGptCorrectionRes.getResponse(),
			nextChat == null ? null :
				ChatResDto.of(nextChat.getId(), nextChat.getContentKo(),
					nextChat.getTransByLang(LangType.ENG))
		);
	}

}
