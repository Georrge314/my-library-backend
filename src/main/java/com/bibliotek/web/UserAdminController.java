package com.bibliotek.web;

import com.bibliotek.domain.dto.ListResponse;
import com.bibliotek.domain.dto.book.BookView;
import com.bibliotek.domain.dto.user.CreateUserRequest;
import com.bibliotek.domain.dto.user.UpdateUserRequest;
import com.bibliotek.domain.dto.user.UserView;
import com.bibliotek.domain.model.Role;
import com.bibliotek.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/admin/user")
@CrossOrigin("http://localhost:3000")
@RolesAllowed(Role.USER_ADMIN)
public class UserAdminController {
    @Autowired
    private UserService userService;

    @GetMapping("{id}")
    public UserView getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @DeleteMapping("{id}")
    public UserView deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    @PostMapping
    public UserView createUser(@RequestBody @Valid CreateUserRequest request) {
        return userService.createUser(request);
    }

    @PutMapping("{id}")
    public UserView updateUser(@PathVariable Long id, @RequestBody @Valid UpdateUserRequest request) {
        return userService.updateUser(id, request);
    }

    @GetMapping("{id}/book")
    public ListResponse<BookView> getBooks(@PathVariable Long id) {
        return new ListResponse<>(userService.getUserBooks(id));
    }
}
