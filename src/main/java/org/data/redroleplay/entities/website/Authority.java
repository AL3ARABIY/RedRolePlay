package org.data.redroleplay.entities.website;

import jakarta.persistence.*;
import lombok.*;
import org.data.redroleplay.enums.BaseAuthority;

@Entity
@Data
@NoArgsConstructor
@Table(schema = "website")
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    public Authority(BaseAuthority authority) {
        this.id = authority.ordinal() + 1;
        this.name = authority.name();
    }
}
