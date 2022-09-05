package itransition.punko.CourseProject.models;

import javax.persistence.*;
import lombok.*;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Indexed
@Entity
@Table(name = "Items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Field
    @Column(name = "name")
    private String name;

    @ManyToMany
    @JoinTable(name="Items_Tags", joinColumns = {@JoinColumn (name="item_id")}, inverseJoinColumns = {@JoinColumn (name="tag_id")})
    private Set<Tag> tags = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "collection_id", referencedColumnName = "id")
    private Collection owner;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @ManyToMany
    @JoinTable(name = "item_likes", joinColumns = { @JoinColumn(name = "item_id") }, inverseJoinColumns = { @JoinColumn(name = "user_id")})
    private Set<User> likes = new HashSet<>();

    @Column(name = "firstNumericField")
    private int firstNumericField;

    @Column(name = "secondNumericField")
    private int secondNumericField;

    @Column(name = "thirdNumericField")
    private int thirdNumericField;

    @Field
    @Column(name = "firstStringField")
    private String firstStringField;

    @Field
    @Column(name = "secondStringField")
    private String secondStringField;

    @Field
    @Column(name = "thirdStringField")
    private String thirdStringField;

    @Field
    @Column(name = "firstTextField")
    private String firstTextField;

    @Field
    @Column(name = "secondTextField")
    private String secondTextField;

    @Field
    @Column(name = "thirdTextField")
    private String thirdTextField;

    @Column(name = "firstBooleanField")
    private Boolean firstBooleanField;

    @Column(name = "secondBooleanField")
    private Boolean secondBooleanField;

    @Column(name = "thirdBooleanField")
    private Boolean thirdBooleanField;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Column(name = "firstDateField")
    private Date firstDateField;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Column(name = "secondDateField")
    private Date secondDateField;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Column(name = "thirdDateField")
    private Date thirdDateField;

    public Item(Collection owner){
        this.owner = owner;
    }

}
