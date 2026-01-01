package bo;
import Enum.TypeCafe;
import Enum.LabelCafe;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/**
 * Description simple d'un café
 */
@Entity
@Data
public class Cafe {

    /**
     * Identifiant du café
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nom du café
     */
    @Column(nullable = false)
    private String nomCafe;

    /**
     * Description du café
     */
    @Column(nullable = false)
    private String description;

    /**
     * Type du café
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TypeCafe typeCafe;

    /**
     * Label du café
     */
    @Column()
    @Enumerated(EnumType.STRING)
    private LabelCafe labelCafe;

    /**
     * Consommations réalisées avec ce café
     */
    @OneToMany(mappedBy = "cafe", fetch = FetchType.LAZY)
    private List<Consommation> listeConsommations;
}
