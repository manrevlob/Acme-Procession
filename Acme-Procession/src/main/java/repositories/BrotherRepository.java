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

	@Query("select b from Brotherhood bb join bb.brothers b where bb.id = ?1 and b.id = ?2 group by b")
	Collection<Brother> findByBrotherhoodAndBrother(int brotherhoodId, int brotherId);
	
	@Query("select b from Brother b join b.costumeReserves br join br.costumeInvoice bri where bri.id = ?1 group by b")
	Brother findByCostumeInvoice(int costumeInvoiceId);
	
	@Query("select b from Brother b join b.registrations br join br.registrationInvoice bri where bri.id = ?1 group by b")
	Brother findByRegistrationInvoice(int registrationInvoiceId);
	
	// Dashboard
	@Query("select b from Brother b order by b.registrations.size desc")
	Collection<Brother> findAllOrderByNumReg();
	
	@Query("select b from Brother b join b.ownBrotherhoods bb group by b having count(bb) >= ALL(Select count(bb2) from Brother b2 join b2.ownBrotherhoods bb2 group by b2)")
	Collection<Brother> findByNumBrotherhood();
	
	@Query("select b, SUM(bri.totalCost.amount) as amount from Brother b join b.registrations br join br.registrationInvoice bri where bri.paidMoment != null group by b order by amount desc")
	Collection<Object[]> findAllTotalCostOfRegistration();
	
	@Query("select b, SUM(bci.totalCost.amount) as amount from Brother b join b.costumeReserves bc join bc.costumeInvoice bci where bci.paidMoment != null group by b order by amount desc")
	Collection<Object[]> findAllTotalCostOfCostume();
	
	@Query("select b from Brother b join b.costumeReserves bc where b.isAuthorized = true and bc.type like 'purchase' group by b")
	Collection<Brother> findWithAutoAndCostumePay();
	
}
