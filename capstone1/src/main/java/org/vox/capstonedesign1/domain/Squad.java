package org.vox.capstonedesign1.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Squad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String squadName;

    @OneToMany(mappedBy = "squad")
    private List<Agent> agents;
}
