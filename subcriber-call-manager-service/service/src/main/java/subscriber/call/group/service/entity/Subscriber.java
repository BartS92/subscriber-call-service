package subscriber.call.group.service.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "subscribers")
@NoArgsConstructor
public class Subscriber {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subscriber_seq")
    @SequenceGenerator(name = "subscriber_seq", sequenceName = "subscriber_seq", allocationSize = 1)
    private Long id;

    @Column(name = "name")
    private String name;
}
