package com.edu.kobridge.module.lesson.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edu.kobridge.module.lesson.domain.entity.LessonChat;

@Repository
public interface LessonChatRepository extends JpaRepository<LessonChat, Long> {
	Optional<LessonChat> findByLessonSentenceId(Long lessonSentenceId);
}
