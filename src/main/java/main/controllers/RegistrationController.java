package main.controllers;

import main.entities.AlmostUser;
import main.entities.User;
import main.repositories.AlmostUserRepository;
import main.services.UserService;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "/register")
public class RegistrationController {
    @Autowired
    private AlmostUserRepository almostUserRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private JavaMailSender sender;
    private Base64.Encoder encoder = Base64.getEncoder();


    @RequestMapping(path = "/user", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void preregister(@RequestBody AlmostUser almostUser, HttpServletResponse response) throws IOException, MessagingException {
        if (almostUser == null || almostUser.getUsername() == null || almostUser.getPassword() == null || almostUser.getMail() == null ||
                almostUser.getUsername().length() <= 3 || almostUser.getPassword().length() <= 4
                || !EmailValidator.getInstance().isValid(almostUser.getMail())) {
            response.sendError(400, "Invalid user");
            return;
        }
        List<AlmostUser> almostUsersFromDB = almostUserRepository.findAllByUsernameOrMail(almostUser.getUsername(), almostUser.getMail());
        List<User> usersFromDB = userService.getUsersByUsernameOrMail(almostUser.getUsername(), almostUser.getMail());
        if (almostUsersFromDB.size() != 0 || usersFromDB.size() != 0) {
            response.sendError(400, "User already exist");
            return;
        }
        almostUser.setHashValue(encoder.encodeToString((almostUser.getUsername()
                + almostUser.getPassword() + almostUser.getMail()).getBytes()));
        almostUser.setPassword(encoder.encodeToString(almostUser.getPassword().getBytes()));
        almostUser.setDate(new Date());
        almostUserRepository.save(almostUser);
        sendHashValueToMail(almostUser.getHashValue(), almostUser.getMail());
    }

    @RequestMapping(path = "/accept/{hashValue}", method = RequestMethod.GET)
    public void acceptRegister(@PathVariable java.lang.String hashValue, HttpServletResponse response)
        throws IOException{
        AlmostUser almostUser = almostUserRepository.findByHashValue(hashValue);
        if (almostUser != null) {
            User newUser = new User();
            newUser.setUsername(almostUser.getUsername());
            newUser.setPassword(almostUser.getPassword());
            newUser.setMail(almostUser.getMail());
            newUser.setStatus("Active");
            userService.saveOrUpdate(newUser);
            almostUserRepository.delete(almostUser);
            response.setStatus(200);
        } else {
            response.sendError(400, "Invalid hashValue.");
        }
    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void deleteOldAlmostUsers() {
        almostUserRepository.deleteAllByDateBefore(new Date(System.currentTimeMillis() - 1800000));
    }


    private void sendHashValueToMail(java.lang.String hashValue, java.lang.String mail) throws MessagingException {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setTo(mail);
        helper.setText("Перейдите по <a href=\"http://website.mydomain.com:8080/register" +
                "/accept/" + hashValue + "\">ссылке</a>, чтобы подтвердить регистрацию.", true);
        helper.setSubject("Подтверждение регистрации.");

        sender.send(message);
    }
}
