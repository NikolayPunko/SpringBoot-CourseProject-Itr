package itransition.punko.CourseProject.models;

public enum Topic {
    Books("Books"),
    Signs("Signs"),
    Silverware("Silverware"),
    Technique("Technique"),
    Nature("Nature"),
    Food("Food"),
    Animals("Animals"),
    Plants("Plants"),
    Electronics("Electronics"),
    Movies("Movies"),
    Clothing("Clothing");


    private final String label;

    Topic(String topic) {
        this.label = topic;
    }

    public String getLabel() {
        return label;
    }

}
