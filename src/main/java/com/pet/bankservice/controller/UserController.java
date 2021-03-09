package com.pet.bankservice.controller;

import com.pet.bankservice.entity.User;
import com.pet.bankservice.entity.dto.UserRequestDto;
import com.pet.bankservice.entity.dto.UserResponseDto;
import com.pet.bankservice.service.RoleService;
import com.pet.bankservice.service.UserService;
import com.pet.bankservice.service.mapper.UserMapper;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final RoleService roleService;
    private final UserMapper mapper;

    @PostMapping
    public ResponseEntity<UserResponseDto>
                createUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        User user = mapper.makeEntity(userRequestDto);
        user.getRoles().add(roleService.getByName("USER"));
        userService.save(user);
        UserResponseDto responseDto = mapper.makeDto(user);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponseDto>
               updateUser(@PathVariable Long userId,
               @Valid @RequestBody UserRequestDto userRequestDto) {
        User user = mapper.makeEntity(userRequestDto);
        user.setId(userId);
        user.setRoles(userService.get(userId).getRoles());
        userService.save(user);
        UserResponseDto responseDto = mapper.makeDto(user);
        return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> get(@PathVariable Long userId) {
        User user = userService.get(userId);
        UserResponseDto responseDto = mapper.makeDto(user);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/by-phone")
    public ResponseEntity<UserResponseDto> findByPhoneNumber(@RequestParam String phoneNumber) {
        User user = userService.findByPhoneNumber(phoneNumber);
        UserResponseDto responseDto = mapper.makeDto(user);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping ("/{userId}")
    public ResponseEntity<String> remove(@PathVariable Long userId) {
        userService.removeById(userId);
        return new ResponseEntity<>("User deleted, id=" + userId, HttpStatus.ACCEPTED);
    }
}
