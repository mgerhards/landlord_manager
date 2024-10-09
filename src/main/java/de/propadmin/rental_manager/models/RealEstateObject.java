import org.hibernate.mapping.List;
import org.springframework.data.annotation.Id;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class RealEstateObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;
    private double size;  // In square meters
    private int numberOfRooms;
    private String description;

    @ManyToOne
    @JoinColumn(name = "landlord_id")
    private Landlord landlord;

    @OneToMany(mappedBy = "asset")
    private List<Contract> contracts;

    @OneToMany(mappedBy = "asset")
    private List<Ticket> tickets;

    // Getters and Setters
}
