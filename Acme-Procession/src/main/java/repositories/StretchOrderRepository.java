package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.StretchOrder;

@Repository
public interface StretchOrderRepository extends JpaRepository<StretchOrder, Integer> {

}
