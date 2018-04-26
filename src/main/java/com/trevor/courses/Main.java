package com.trevor.courses;

import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import static spark.Spark.get;

public class Main {
    public static void main(String[] args) {

        // index.hbs file is in resources/templates directory
        get("/", (rq, rs) -> new ModelAndView(null, "index.hbs"), new HandlebarsTemplateEngine());

    }
}
