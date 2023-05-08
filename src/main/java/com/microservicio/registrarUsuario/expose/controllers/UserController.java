package com.microservicio.registrarUsuario.expose.controllers;

import com.microservicio.registrarUsuario.exceptions.IdUserNotFoundException;
import com.microservicio.registrarUsuario.expose.dto.GetUserDTO;
import com.microservicio.registrarUsuario.expose.dto.UpdateUserPasswDTO;
import com.microservicio.registrarUsuario.expose.dto.UpdateUserEmailDTO;
import com.microservicio.registrarUsuario.persistence.entities.User;
import com.microservicio.registrarUsuario.persistence.repository.UserRepository;
import com.microservicio.registrarUsuario.services.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
public class UserController {


    private final UserService userService;
    private final UserRepository userRepository;


    @GetMapping
    public ResponseEntity<List<GetUserDTO>> getAll(){
        return new ResponseEntity<List<GetUserDTO>>(userService.findAll(), HttpStatus.OK);
    }


    @GetMapping("/us/{id}")
    public ResponseEntity<User>find(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.findById(id)
                .orElseThrow(() -> new IdUserNotFoundException(id)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetUserDTO>findById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(userService.findById(id));
    }



    @PutMapping("/email/{id}")
    public ResponseEntity<GetUserDTO> updateEmail(@RequestBody UpdateUserEmailDTO updateUserEmailDTO, @PathVariable Long id){
        return new ResponseEntity<GetUserDTO>(userService.editEmail(updateUserEmailDTO,id), HttpStatus.OK);
    }

    @PutMapping("/password/{id}")
    public ResponseEntity<GetUserDTO> updatePassword(@RequestBody UpdateUserPasswDTO updateUserPasswDTO , @PathVariable Long id){
        return new ResponseEntity<GetUserDTO>(userService.editPassw(updateUserPasswDTO,id), HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        userService.deleteById(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }



}
