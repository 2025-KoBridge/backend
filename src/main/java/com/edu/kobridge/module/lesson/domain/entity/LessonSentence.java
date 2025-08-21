package com.edu.kobridge.module.lesson.domain.entity;

import com.edu.kobridge.global.common.BaseTime;
import com.edu.kobridge.global.enums.LangType;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class LessonSentence extends BaseTime {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private Integer number;

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

	@NotNull
	private String pronunciationEng;

	@NotNull
	private String pronunciationVet;

	@NotNull
	private String pronunciationJpn;

	@NotNull
	private String pronunciationChn;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lesson_id", nullable = false)
	private Lesson lesson;

	public String getPronunciationByLang(LangType langType) {
		return switch (langType) {
			case VET -> pronunciationVet;
			case JPN -> pronunciationJpn;
			case CHN -> pronunciationChn;
			default -> pronunciationEng;
		};
	}

	public String getTransByLang(LangType langType) {
		return switch (langType) {
			case VET -> contentVet;
			case JPN -> contentJpn;
			case CHN -> contentChn;
			default -> contentEng;
		};
	}
}
