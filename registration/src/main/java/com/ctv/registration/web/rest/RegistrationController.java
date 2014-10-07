package com.ctv.registration.web.rest;

import com.ctv.registration.core.dto.User;
import com.ctv.registration.web.adapter.RegistrationMvcAdapter;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(method = RequestMethod.POST)
    public void createUser(@RequestBody User user) {
        registrationMvcAdapter.createUser(user);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void updateUser(User user) {
        registrationMvcAdapter.updateUser(user);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable Integer id) {
        registrationMvcAdapter.deleteUser(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public void findUser(@PathVariable Integer id) {
        registrationMvcAdapter.findUserById(id);
    }

}
