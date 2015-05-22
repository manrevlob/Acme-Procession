package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Brother;

@Repository
public interface BrotherRepository extends JpaRepository<Brother, Integer> {

	@Query("select b from Brother b where b.userAccount.id = ?1")
	Brother findByPrincipal(int userAccountId);

	@Query("select b from Brother b where ?1 in (select j.id from Brother bb join bb.brotherhoods j where bb = b) and ?1 not in (select j.id from Brother bb join bb.ownBrotherhoods j where bb = b)")
	Collection<Brother> findAllBrothersNotAdded(int brotherhoodId);

	@Query("select b.name from Brotherhood bb join bb.brothers b where bb.id = ?1 and b.id = ?2 group by b")
	Collection<Brother> findByBrotherhoodAndBrother(int brotherhoodId, int brotherId);
	
}
