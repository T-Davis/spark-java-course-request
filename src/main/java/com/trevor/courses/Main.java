package com.trevor.courses;

import com.trevor.courses.model.CourseIdea;
import com.trevor.courses.model.CourseIdeaDAO;
import com.trevor.courses.model.SimpleCourseIdeaDAO;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        staticFileLocation("/public");
        CourseIdeaDAO dao = new SimpleCourseIdeaDAO();

        before((rq, rs) -> {
            if (rq.cookie("username") != null) {
                rq.attribute("username", rq.cookie("username"));
            }
        });

        before("/ideas", (rq, rs) -> {
            if (rq.attribute("username") == null) {
                rs.redirect("/");
                halt();
            }
        });

        get("/", (rq, rs) -> {
            Map<String, String> model = new HashMap<>();
            model.put("username", rq.attribute("username"));
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        post("/sign-in", (rq, rs) -> {
            String username = rq.queryParams("username");
            rs.cookie("username", username);
            rs.redirect("/");
            return null;
        });

        get("/ideas", (rq, rs) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("ideas", dao.findAll());
            return new ModelAndView(model, "ideas.hbs");
        }, new HandlebarsTemplateEngine());

        post("/ideas", (rq, rs) -> {
            String title = rq.queryParams("title");
            CourseIdea courseIdea = new CourseIdea(title, rq.attribute("username"));
            dao.add(courseIdea);
            rs.redirect("/ideas");
            return null;
        });

        post("/ideas/:slug/vote", (rq, rs) -> {
            CourseIdea idea = dao.findBySlug(rq.params("slug"));
            idea.addVoter(rq.attribute("username"));
            rs.redirect("/ideas");
            return null;
        });
    }
}
