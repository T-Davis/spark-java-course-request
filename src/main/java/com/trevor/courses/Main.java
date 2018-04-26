package com.trevor.courses;

import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;

public class Main {
    public static void main(String[] args) {

        // index.hbs file is in resources/templates directory
        get("/", (rq, rs) -> {
            Map<String, String> model = new HashMap<>();
            model.put("username", rq.cookie("username"));
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        post("/sign-in", (rq, rs) -> {
            Map<String, String> model = new HashMap<>();
            String username = rq.queryParams("username");
            rs.cookie("username", username);
            model.put("username", username);
            return new ModelAndView(model, "sign-in.hbs");
        }, new HandlebarsTemplateEngine());
    }
}
