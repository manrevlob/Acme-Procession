package services.negative;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import repositories.RequestRepository;
import services.RequestService;
import utilities.AbstractTest;
import domain.Request;
import forms.AddCommentToRequestForm;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml",
	"classpath:spring/config/packages.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class RequestServiceTestNegative extends AbstractTest{

	// Services under test --------------------------------------
	
	@Autowired
	private RequestService requestService;
	
	// Repositories  --------------------------------------------
	
	@Autowired
	private RequestRepository requestRepository;
	
	// Comprobamos que no podemos crear una solicitud estando logueados como viewer
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSaveRequestNegative(){
		Request request;
		
		//Probamos a crearlo con un usuario sin permisos
		authenticate("viewer1");
		
		request = requestService.create();
		
		request.setTitle("title");
		request.setDescription("descrition");
		
		requestService.saveRequest(request);
		
		authenticate(null);
	}
	
	// Comprobamos que no podemos crear una solicitud estando logueados como admin
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSaveRequestNegative2(){
		Request request;
		
		//Probamos a crearlo con un usuario sin permisos
		authenticate("admin");
		
		request = requestService.create();
		
		request.setTitle("title");
		request.setDescription("descrition");
		
		requestService.saveRequest(request);
		
		authenticate(null);
	}
	
	// Comprobamos que no podemos crear una solicitud sin logueado
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSaveRequestNegative3(){
		Request request;
		
		//Probamos a crearlo sin estar autenticados
		authenticate(null);
		
		request = requestService.create();
		
		request.setTitle("title");
		request.setDescription("descrition");
		
		requestService.saveRequest(request);
	}
	
	// Comprobamos que no podemos crear una solicitud con un codigo ya en uso
	@Test(expected = DataIntegrityViolationException.class)
	public void testCreateAndSaveRequestNegative4(){
		Request request;
		
		authenticate("brother4");
		
		request = requestService.create();
		
		request.setTitle("title");
		request.setDescription("descrition");
		
		//Intentamos utilizar un code que ya esta en uso
		request.setCode("jht34298udjh");
		
		requestService.saveRequest(request);
		
		requestRepository.flush();
		
		authenticate(null);
	}
	
	// Comprobamos que no podemos crear una solicitud con el estado "accepted" de inicio
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSaveRequestNegative5(){
		Request request;
		
		authenticate("brother4");
		
		request = requestService.create();
		
		request.setTitle("title");
		request.setDescription("descrition");
		
		//Intentamos crear la request con el estado accepted en lugar de pending
		request.setStatus("accepted");
		
		requestService.saveRequest(request);
		
		authenticate(null);
	}
	
	// Comprobamos que no podemos crear una solicitud si el brother ya tiene una solicitud aceptada
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSaveRequestNegative6(){
		Request request;
		
		//Probamos a crearlo con un usuario que tiene una request ya aceptada
		authenticate("brother1");
		
		request = requestService.create();
		
		request.setTitle("title");
		request.setDescription("descrition");
		
		requestService.saveRequest(request);
		
		authenticate(null);
	}
	
	// Comprobamos que no podemos crear una solicitud si el brother ya tiene una solicitud en pendiente
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAndSaveRequestNegative7(){
		Request request;
		
		//Probamos a crearlo con un usuario que tiene una request pendiente
		authenticate("brother3");
		
		request = requestService.create();
		
		request.setTitle("title");
		request.setDescription("descrition");
		
		requestService.saveRequest(request);
		
		authenticate(null);
	}
	
	//Probamos a aceptar la request de un brother el rol de viewer
	@Test(expected = IllegalArgumentException.class)
	public void testAcceptRequestNegative(){
		Request request;
		List<Request> requests;
		AddCommentToRequestForm addCommentToRequestForm;
		
		//intentamos acceder con un usuario sin permisos
		authenticate("viewer1");
		//obtenemos la lista de todas las request pendientes
		requests = (List<Request>) requestService.findAllRequestPending();
		
		request = requests.get(0);
		
		addCommentToRequestForm = new AddCommentToRequestForm();
		addCommentToRequestForm.setRequest(request);
		
		requestService.acceptRequest(addCommentToRequestForm);
		
		authenticate(null);
	}
	
	//Probamos a aceptar la request de un brother el rol de brother
	@Test(expected = IllegalArgumentException.class)
	public void testAcceptRequestNegative2(){
		Request request;
		List<Request> requests;
		AddCommentToRequestForm addCommentToRequestForm;
		
		//intentamos acceder con un usuario sin permisos
		authenticate("brother1");
		//obtenemos la lista de todas las request pendientes
		requests = (List<Request>) requestService.findAllRequestPending();
		
		request = requests.get(0);
		
		addCommentToRequestForm = new AddCommentToRequestForm();
		addCommentToRequestForm.setRequest(request);
		
		requestService.acceptRequest(addCommentToRequestForm);
		
		authenticate(null);
	}
	
	//Probamos a aceptar la request sin estar logueados
	@Test(expected = IllegalArgumentException.class)
	public void testAcceptRequestNegative3(){
		Request request;
		List<Request> requests;
		AddCommentToRequestForm addCommentToRequestForm;
		
		//intentamos acceder sin estar autenticados
		authenticate(null);
		//obtenemos la lista de todas las request pendientes
		requests = (List<Request>) requestService.findAllRequestPending();
		
		request = requests.get(0);
		
		addCommentToRequestForm = new AddCommentToRequestForm();
		addCommentToRequestForm.setRequest(request);
		
		requestService.acceptRequest(addCommentToRequestForm);
		
	}
	
	//Probamos a aceptar una request que no esta pendiente
	@Test(expected = IllegalArgumentException.class)
	public void testAcceptRequestNegative4(){
		Request request;
		AddCommentToRequestForm addCommentToRequestForm;
		
		authenticate("admin");

		//ID de la request 1(aceptada)
		request = requestService.findOne(95);
		
		addCommentToRequestForm = new AddCommentToRequestForm();
		addCommentToRequestForm.setRequest(request);
		
		requestService.acceptRequest(addCommentToRequestForm);
		
		authenticate(null);
	}
	
	
	//Probamos a rechazar la request con el rol de viewer 
	@Test(expected = IllegalArgumentException.class)
	public void testRejectRequestnegative(){
		Request request;
		List<Request> requests;
		AddCommentToRequestForm addCommentToRequestForm;
		
		//intentamos acceder con un usuario sin permisos
		authenticate("viewer1");
		//obtenemos la lista de todas las request pendientes
		requests = (List<Request>) requestService.findAllRequestPending();
		
		request = requests.get(0);
		
		addCommentToRequestForm = new AddCommentToRequestForm();
		addCommentToRequestForm.setRequest(request);
		addCommentToRequestForm.setComment("comment");
		
		requestService.rejectedRequest(addCommentToRequestForm);

		authenticate(null);
	}
	
	//Probamos a rechazar la request con el rol de brother 
	@Test(expected = IllegalArgumentException.class)
	public void testRejectRequestnegative2(){
		Request request;
		List<Request> requests;
		AddCommentToRequestForm addCommentToRequestForm;
		
		//intentamos acceder con un usuario sin permisos
		authenticate("brother1");
		//obtenemos la lista de todas las request pendientes
		requests = (List<Request>) requestService.findAllRequestPending();
		
		request = requests.get(0);
		
		addCommentToRequestForm = new AddCommentToRequestForm();
		addCommentToRequestForm.setRequest(request);
		addCommentToRequestForm.setComment("comment");
		
		requestService.rejectedRequest(addCommentToRequestForm);

		authenticate(null);
	}
	
	//Probamos a rechazar la request sin estar logueados
	@Test(expected = IllegalArgumentException.class)
	public void testRejectRequestnegative3(){
		Request request;
		List<Request> requests;
		AddCommentToRequestForm addCommentToRequestForm;
		
		//intentamos acceder sin estar autenticados
		authenticate(null);
		//obtenemos la lista de todas las request pendientes
		requests = (List<Request>) requestService.findAllRequestPending();
		
		request = requests.get(0);
		
		addCommentToRequestForm = new AddCommentToRequestForm();
		addCommentToRequestForm.setRequest(request);
		addCommentToRequestForm.setComment("comment");
		
		requestService.rejectedRequest(addCommentToRequestForm);
	}
	
	//Probamos a rechazar una request no esta pendiente
	@Test(expected = IllegalArgumentException.class)
	public void testRejectedRequestNegative4(){
		Request request;
		AddCommentToRequestForm addCommentToRequestForm;
		
		authenticate("admin");

		//ID de la request 1(aceptada)
		request = requestService.findOne(95);
		
		addCommentToRequestForm = new AddCommentToRequestForm();
		addCommentToRequestForm.setRequest(request);
		
		requestService.rejectedRequest(addCommentToRequestForm);
		
		authenticate(null);
	}
	
}
