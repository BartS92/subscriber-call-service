package subscriber.call.group.service.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import subscriber.call.group.service.entity.Subscriber;


@Repository
public interface SubscriberRepository extends CrudRepository<Subscriber, Long> {
    Subscriber findById(long id);
}
