package subscriber.call.group.service.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.Data;

@Data
@Entity
@Table(name = "events")
public class Event {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "events_seq")
    @SequenceGenerator(name = "events_seq", sequenceName = "events_seq", allocationSize = 1)
    private Long id;

    @Column(name = "status")
    private String status;

    @Column(name = "init_phone")
    private Long initPhone;

    @Column(name = "receiving_phone")
    private Long receivingPhone;

    @Column(name = "date_created", columnDefinition = "timestamp")
    private Date dateCreated;
}
