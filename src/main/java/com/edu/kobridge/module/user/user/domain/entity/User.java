package com.edu.kobridge.module.user.user.domain.entity;

import com.edu.kobridge.global.common.BaseTime;
import com.edu.kobridge.global.enums.LangType;
import com.edu.kobridge.global.enums.SchoolType;
import com.edu.kobridge.global.enums.UserRoleType;
import com.edu.kobridge.global.enums.VoiceType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class User extends BaseTime {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(unique = true)
	private String email;

	private String name;

	@Enumerated(EnumType.STRING)
	private LangType lang;

	@Enumerated(EnumType.STRING)
	private SchoolType school;

	private byte grade;

	@Enumerated(EnumType.STRING)
	private VoiceType voice;

	private int level;

	@NotNull
	@Enumerated(EnumType.STRING)
	private UserRoleType role;

	public static User of(@NotNull String email) {
		return User
			.builder()
			.email(email)
			.role(UserRoleType.USER)
			.build();
	}

	public void updateInfo(@NotNull String name, @NotNull LangType lang, @NotNull SchoolType school,
		@NotNull byte grade, @NotNull VoiceType voice) {
		this.name = name;
		this.lang = lang;
		this.school = school;
		this.grade = grade;
		this.voice = voice;
		this.level = 1;
	}

	public void updateLang(@NotNull LangType lang) {
		this.lang = lang;
	}

	public void updateLevel(@NotNull int level) {
		this.level = level;
	}
}
