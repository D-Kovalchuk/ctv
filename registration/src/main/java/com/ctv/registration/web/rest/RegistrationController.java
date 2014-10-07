package com.ctv.registration.web.rest;

import com.ctv.registration.core.port.out.RegistrationMvcAdapter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.ctv.registration.web.rest.Endpoint.USER_PATH;

/**
 * @author Dmitry Kovalchuk
 */
@RestController
@RequestMapping(USER_PATH)
public class RegistrationController {

    private RegistrationMvcAdapter registrationMvcAdapter;

    public RegistrationController(RegistrationMvcAdapter registrationMvcAdapter) {
        this.registrationMvcAdapter = registrationMvcAdapter;
    }
//
//    @RequestMapping(method = POST)
//    public void createUser(User user) {
//        registrationMvcAdapter.create(user);
//    }
//
//    @RequestMapping(method = PUT)
//    public void updateUser(User user) {
//        registrationMvcAdapter.update(user);
//    }
//
//    @RequestMapping(vo = "/{id}", method = DELETE)
//    public void deleteUser(@PathVariable Integer id) {
//        registrationMvcAdapter.delete(id);
//    }
//
//    @RequestMapping(vo = "/{id}", method = GET)
//    public void findUser(@PathVariable Integer id) {
//        registrationMvcAdapter.findUser(id);
//    }

}
