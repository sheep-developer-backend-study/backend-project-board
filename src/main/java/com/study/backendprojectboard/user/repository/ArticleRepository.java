package com.study.backendprojectboard.user.repository;

import com.study.backendprojectboard.user.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
