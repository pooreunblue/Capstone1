package org.vox.capstonedesign1.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
public class Squad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "squad_name")
    private String squadName;

    @OneToMany(mappedBy = "squad")
    private List<Agent> agents;
}
