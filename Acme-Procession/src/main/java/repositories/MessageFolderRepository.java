package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;
import domain.MessageFolder;

@Repository
public interface MessageFolderRepository extends
		JpaRepository<MessageFolder, Integer> {

	@Query("select a.messageFolders from Actor a where a.id = ?1")
	Collection<MessageFolder> findByActorId(int actorId);
	
	@Query("select mf from MessageFolder mf where mf = ?1 and mf IN (select mf2  from Actor a join a.messageFolders  mf2 where a = ?2)")
	MessageFolder checkPrincipal(MessageFolder messageFolder, Actor actor);
	
}
