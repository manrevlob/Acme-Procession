package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

	@Query("select m  from Actor a join a.messageFolders ms join ms.messages m where a.id = ?1")
	Collection<Message> findByActorId(int actorId);
	
	@Query("select m from Message m where m.messageFolder.id = ?1")
	Collection<Message> findByMessageFolderId(int messageFolderId);
	
}
