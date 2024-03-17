package com.sophia.entity.enums;

import lombok.Getter;

@Getter
public enum ChannelTags {
    NEWS("News", "All happening in the country and world"),
    CINEMA("Cinema", "All about movies, series, actors, actresses, and directors"),
    MUSIC("Music", "All about music, singers, bands, and concerts"),
    SPORTS("Sports", "All about sports, teams, players, and matches"),
    TECHNOLOGY("Technology", "All about technology, gadgets, software, and companies"),
    GAMES("Games", "All about games, game developers, and game consoles"),
    BOOKS("Books", "All about books, authors, and publishers"),
    FASHION("Fashion", "All about fashion, designers, and brands"),
    FOOD("Food", "All about food, recipes, and restaurants"),
    TRAVEL("Travel", "All about travel, countries, and cities"),
    HEALTH("Health", "All about health, diseases, and treatments"),
    SCIENCE("Science", "All about science, scientists, and discoveries"),
    ART("Art", "All about art, artists, and galleries"),
    ANIMALS("Animals", "All about animals, species, and habitats"),
    ENVIRONMENT("Environment", "All about environment, climate, and pollution"),
    POLITICS("Politics", "All about politics, politicians, and elections"),
    ECONOMY("Economy", "All about economy, companies, and markets"),
    HISTORY("History", "All about history, events, and people"),
    RELIGION("Religion", "All about religion, beliefs, and rituals"),
    PSYCHOLOGY("Psychology", "All about psychology, mental health, and therapies"),
    PHILOSOPHY("Philosophy", "All about philosophy, philosophers, and theories"),
    EDUCATION("Education", "All about education, schools, and universities"),
    LANGUAGES("Languages", "All about languages, words, and grammar");

    private final String name;
    private final String explanation;

    ChannelTags(String name, String explanation) {
        this.name = name;
        this.explanation = explanation;
    }

}
