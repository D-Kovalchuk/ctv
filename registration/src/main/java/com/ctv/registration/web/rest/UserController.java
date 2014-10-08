package com.ctv.registration.web.rest;

import com.ctv.registration.core.dto.User;
import com.ctv.registration.web.adapter.UserMvcAdapter;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ctv.registration.web.rest.Endpoint.USER_PATH;
import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * @author Dmitry Kovalchuk
 */
@RestController
@RequestMapping(USER_PATH)
public class UserController {

    public static final String START_PAGE = "0";
    public static final String PAGE_SIZE = "10";
    private UserMvcAdapter userMvcAdapter;

    public UserController(UserMvcAdapter userMvcAdapter) {
        this.userMvcAdapter = userMvcAdapter;
    }

    @RequestMapping(method = POST)
    public void createUser(@RequestBody User user) {
        userMvcAdapter.createUser(user);
    }

    @RequestMapping(method = PUT)
    public void updateUser(@RequestBody User user) {
        userMvcAdapter.updateUser(user);
    }

    @RequestMapping(value = "/{id}", method = DELETE)
    public void deleteUser(@PathVariable Integer id) {
        userMvcAdapter.deleteUser(id);
    }

    @RequestMapping(value = "/{id}", method = GET)
    public void findUser(@PathVariable Integer id) {
        userMvcAdapter.findUserById(id);
    }

    @RequestMapping(params = {"page", "size"}, method = GET)
    public List<User> findUsers(@RequestParam(defaultValue = START_PAGE) int page,
                                @RequestParam(defaultValue = PAGE_SIZE) int size) {
        return userMvcAdapter.findAllUsers(page, size);
    }
}
