package itransition.punko.CourseProject.models;

import javax.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "Collections")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Collection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "markdown")
    private String markdown;

    @Column(name = "topic")
    @Enumerated(EnumType.STRING)
    private Topic topic;

    @Column(name = "url_photo")
    private String urlPhoto;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User owner;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Item> items;

    @Column(name = "bitmask")
    private int bitmask;

    @Column(name = "firstNumericField")
    private String nameFirstNumericField;

    @Column(name = "secondNumericField")
    private String nameSecondNumericField;

    @Column(name = "thirdNumericField")
    private String nameThirdNumericField;

    @Column(name = "firstStringField")
    private String nameFirstStringField;

    @Column(name = "secondStringField")
    private String nameSecondStringField;

    @Column(name = "thirdStringField")
    private String nameThirdStringField;

    @Column(name = "firstTextField")
    private String nameFirstTextField;

    @Column(name = "secondTextField")
    private String nameSecondTextField;

    @Column(name = "thirdTextField")
    private String nameThirdTextField;

    @Column(name = "firstBooleanField")
    private String nameFirstBooleanField;

    @Column(name = "secondBooleanField")
    private String nameSecondBooleanField;

    @Column(name = "thirdBooleanField")
    private String nameThirdBooleanField;

    @Column(name = "firstDateField")
    private String nameFirstDateField;

    @Column(name = "secondDateField")
    private String nameSecondDateField;

    @Column(name = "thirdDateField")
    private String nameThirdDateField;

}
