package com.trevor.courses.model;

import com.github.slugify.Slugify;

import java.util.Objects;

public class CourseIdea {
    private String slug;
    private String title;
    private String creator;

    public CourseIdea(String title, String creator) {

        this.title = title;
        this.creator = creator;
        Slugify slugify = new Slugify();
        slug = slugify.slugify(title);
    }

    public String getTitle() {
        return title;
    }

    public String getCreator() {
        return creator;
    }

    public String getSlug() {
        return slug;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseIdea that = (CourseIdea) o;
        return Objects.equals(title, that.title) &&
                Objects.equals(creator, that.creator);
    }

    @Override
    public int hashCode() {

        return Objects.hash(title, creator);
    }
}
