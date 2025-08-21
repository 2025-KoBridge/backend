package com.edu.kobridge.module.lesson.domain.entity;

import com.edu.kobridge.global.common.BaseTime;
import com.edu.kobridge.global.enums.LangType;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class LessonChat extends BaseTime {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String contentKo;

	@NotNull
	private String contentEng;

	@NotNull
	private String contentVet;

	@NotNull
	private String contentJpn;

	@NotNull
	private String contentChn;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "next_chat_id", unique = true)
	private LessonChat nextChat;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lesson_sentence_id", nullable = false, unique = true)
	private LessonSentence lessonSentence;

	public String getTransByLang(LangType langType) {
		return switch (langType) {
			case VET -> contentVet;
			case JPN -> contentJpn;
			case CHN -> contentChn;
			default -> contentEng;
		};
	}
}
