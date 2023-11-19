package subscriber.call.group.service.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import subscriber.call.group.service.entity.Event;


@Repository
public interface EventRepository extends CrudRepository<Event, Long> {
    Event findById(long id);

    @Query(value = "Select * from events where (init_phone= :phone or receiving_phone= :phone) ORDER BY date_created DESC LIMIT 1", nativeQuery = true)
    Event getLastPhoneEvent(@Param("phone") long phone);
}
