package itransition.punko.CourseProject.models;

import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Tags")
@Getter
@Setter
@NoArgsConstructor
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "tag")
    private String tag;

    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    private Set<Item> items = new HashSet<>();

    public Tag(String tag){
        this.tag = tag;
    }

}
