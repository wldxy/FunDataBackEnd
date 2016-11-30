package FunData.Model;

import javax.persistence.*;

/**
 * Created by stanforxc on 2016/11/29.
 */

@Entity
@Table(name = "Competition")
public class Competition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
