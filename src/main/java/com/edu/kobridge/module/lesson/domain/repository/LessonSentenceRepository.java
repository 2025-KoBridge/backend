package com.edu.kobridge.module.lesson.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edu.kobridge.module.lesson.domain.entity.LessonSentence;

@Repository
public interface LessonSentenceRepository extends JpaRepository<LessonSentence, Long> {
}
