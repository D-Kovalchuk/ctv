package com.ctv.registration.web.rest;

import com.ctv.registration.core.dto.User;
import com.ctv.registration.web.adapter.UserMvcAdapter;
import org.springframework.web.bind.annotation.*;

import static com.ctv.registration.web.rest.Endpoint.USER_PATH;

/**
 * @author Dmitry Kovalchuk
 */
@RestController
@RequestMapping(USER_PATH)
public class UserController {

    private UserMvcAdapter userMvcAdapter;

    public UserController(UserMvcAdapter userMvcAdapter) {
        this.userMvcAdapter = userMvcAdapter;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void createUser(@RequestBody User user) {
        userMvcAdapter.createUser(user);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void updateUser(@RequestBody User user) {
        userMvcAdapter.updateUser(user);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable Integer id) {
        userMvcAdapter.deleteUser(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public void findUser(@PathVariable Integer id) {
        userMvcAdapter.findUserById(id);
    }

}
