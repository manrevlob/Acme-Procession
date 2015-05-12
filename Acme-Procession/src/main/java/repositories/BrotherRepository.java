package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Brother;

@Repository
public interface BrotherRepository extends JpaRepository<Brother, Integer> {

	@Query("select b from Brother b where b.userAccount.id = ?1")
	Brother findByPrincipal(int userAccountId);

}
