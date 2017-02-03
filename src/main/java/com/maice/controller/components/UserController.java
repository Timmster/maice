package com.maice.controller.components;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import com.maice.model.dto.MaiceServerResponse;
import com.maice.model.entities.User;
import com.maice.model.repositories.UserRepository;

@Component
@RequestScope
public class UserController extends AbstractMaiceController{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MailController mailController;
	
	public ResponseEntity<MaiceServerResponse> register(String username, String password){
		List<User> userList = userRepository.findByUsername(username);
		if (userList != null && userList.size() > 0){
			addInfo("Der Benutzername ist bereits vergeben.");
			return createResponse(null);			
		}else{
			String hash = mailController.sendRegistrationEmail(username);
			User user = new User(username, password, hash);
			userRepository.save(user);
			addInfo("Registrierung erfolgreich. Bitte prüfen Sie Ihre Emails und aktivieren Sie das Konto.");
			return createResponse(null);
		}
	}
}
