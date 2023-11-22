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
    private Long id;

    @Column(name = "name")
    private String name;
}
