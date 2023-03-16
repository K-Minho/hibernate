package shop.mtcoding.hibernate.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.hibernate.model.User;
import shop.mtcoding.hibernate.model.UserJpaRepository;

@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class UserApiController {

    // private final UserRepository userRepository;
    private final UserJpaRepository userRepository;

    @PostMapping("/users")
    public ResponseEntity<?> addUser(User user) {
        User userPS = userRepository.save(user);
        return new ResponseEntity<>(userPS, HttpStatus.CREATED);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, User user) {
        User userPS = userRepository.findById(id).get();
        if (userPS == null) {
            return new ResponseEntity<>("Not Found.", HttpStatus.BAD_REQUEST);
        }
        userPS.update(user.getPassword(), user.getEmail());
        User updatedUserPS = userRepository.save(userPS);
        return new ResponseEntity<>(updatedUserPS, HttpStatus.CREATED);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id) {
        User userPS = userRepository.findById(id).get();
        if (userPS == null) {
            return new ResponseEntity<>("Not Found.", HttpStatus.BAD_REQUEST);
        }
        userRepository.delete(userPS);
        return new ResponseEntity<>("Done!", HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<?> findUsers(@RequestParam(defaultValue = "0") int page) {
        Page<User> userListPS = userRepository.findAll(PageRequest.of(page, 2));
        return new ResponseEntity<>(userListPS, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> findUser(@PathVariable Long id) {
        User userPS = userRepository.findById(id).get();
        if (userPS == null) {
            return new ResponseEntity<>("Not Found.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userPS, HttpStatus.OK);
    }

}
