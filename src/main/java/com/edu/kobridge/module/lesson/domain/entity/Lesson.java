package com.edu.kobridge.module.lesson.domain.entity;

import java.util.List;

import com.edu.kobridge.global.common.BaseTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
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
public class Lesson extends BaseTime {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(unique = true)
	private Integer number;

	@NotNull
	private String title;

	@NotNull
	private String subject;

	@NotNull
	private String subTitle1;

	@NotNull
	private String subTitle2;

	@NotNull
	private String subTitle3;

	@OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy("number ASC")
	private List<LessonSentence> sentences;

}
