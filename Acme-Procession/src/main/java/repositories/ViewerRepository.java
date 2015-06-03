package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Viewer;

@Repository
public interface ViewerRepository extends JpaRepository<Viewer, Integer> {

	@Query("select v from Viewer v where v.userAccount.id = ?1")
	Viewer findByPrincipal(int userAccountId);
	
	@Query("select v from Viewer v join v.boxReserves vr join vr.boxInvoice vri where vri.id = ?1")
	Viewer findByBoxInvoice(int boxInvoiceId);
	
	// Dashboard
	@Query("select v from Viewer v order by v.boxReserves.size desc")
	Collection<Viewer> findAllReserMorBox();
	
	@Query("select v from Viewer v order by v.assessments.size desc")
	Collection<Viewer> findAllOrderByNumAssess();

}
