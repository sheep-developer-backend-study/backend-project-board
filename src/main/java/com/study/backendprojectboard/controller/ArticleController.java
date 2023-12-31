package com.study.backendprojectboard.controller;

import com.study.backendprojectboard.user.model.Article;
import com.study.backendprojectboard.user.model.User;
import com.study.backendprojectboard.user.repository.ArticleRepository;
import com.study.backendprojectboard.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class ArticleController {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    @GetMapping
    public String articleList(Model model) {
        log.info("articleList() call");
        model.addAttribute("articles", articleRepository.findAll());
        return "article/articleList";
    }

    @GetMapping("/{id}")
    public String article(@PathVariable("id") Long id, Model model) {
        log.info("article({}) call", id);
        model.addAttribute("article", articleRepository.findById(id).get());
        return "article/articleView";
    }

    // TODO 새 글 작성시 로그인 여부 검증
    @GetMapping("/add")
    public String addArticleForm(@ModelAttribute("article") Article article) {
        log.info("addArticleForm() call - GET");
        return "article/addArticleForm";
    }

    @PostMapping("/add")
    public String addArticle(@ModelAttribute("article") Article article) {
        log.info("addArticle() call - POST");
        article.setRegDate(LocalDateTime.now());
        User user = userRepository.findById(1L).orElseGet(null);
        if(user != null) {
            article.setUser(user);
        }
        Article res = articleRepository.save(article);
        return "redirect:/board/" + res.getId();
    }

    @GetMapping("/{id}/edit")
    public String editArticleForm(@PathVariable("id") Long id, Model model) {
        log.info("editArticle({}) call", id);
        model.addAttribute("article", articleRepository.findById(id).get());
        return "article/editArticleForm";
    }

    @PostMapping("/{id}/edit")
    public String editArticle(@PathVariable("id") Long id, @ModelAttribute("article") Article article) {
        log.info("editArticle({}) call", id);
        articleRepository.save(article);
        return "redirect:/board/" + id;
    }
}
