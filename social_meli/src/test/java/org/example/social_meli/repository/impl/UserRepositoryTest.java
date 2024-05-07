package org.example.social_meli.repository.impl;
import org.example.social_meli.model.FollowerList;
import org.example.social_meli.model.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.BeforeEach;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

public class UserRepositoryTest {

    private List<User> userList;

    private UserRepository userRepository;
    private FollowerList followerList;
    private User user;

    @BeforeEach
    void setUp() throws IOException {
        userRepository = new UserRepository();
        user = new User(1, "user", false);
        followerList = new FollowerList(user);

        User user1 = new User(2, "User1", true);
        User user2 = new User(1,"User2",false);
        List <User> users =new ArrayList<>();
        users.add(user1);
        users.add(user2);
    }
    @Test
    @DisplayName("Verificar método findByID, usuario existe.")
    void findById_UserExist() {
        Integer userId = 1;
        User found = userRepository.findById(userId);
        Assertions.assertEquals(userId, found.getUser_id(), "El ID del usuario encontrado debe coincidir con el buscado.");
        Assertions.assertNotNull(found, "El usuario encontrado no debe ser nulo.");
    }
    @Test
    @DisplayName("Verificar método findByID, usuario no existe.")
    void findById_UserNoExist() {
        Integer userId = 99999;
        User found = userRepository.findById(userId);
        assertNull(found, "Debe devolver null si el usuario no existe.");
    }
    @Test
    @DisplayName("Veficar método exixtByID, usuario existente.")
    void existById_UserExits(){
        Integer userId = 1;
        Boolean exists = userRepository.existsById(userId);
        Assertions.assertTrue(exists, "El método debería retornar verdadero para un usuario existente");
    }
    @Test
    @DisplayName("Veficar método exixtByID, usuario no existente.")
    void existById_UserNoExits(){
        Integer userId = 588;
        Boolean exists = userRepository.existsById(userId);
        assertFalse(exists, "El método debería retornar falso para un usuario existente");
    }
    @Test
    @DisplayName("Deberia retornar el indice del cliente")
    void getClientIndex() {
        userRepository.saveClient(followerList);
        Integer index = userRepository.getClientIndex(followerList);
        assertEquals(1, index);
    }

    @Test
    @DisplayName("Deberia retornar el indice del vendedor")
    void getSellerIndex() {
        User user = new User(2, "user", true);
        followerList.setUser(user);

        userRepository.saveSeller(followerList);
        Integer index = userRepository.getSellerIndex(followerList);
        assertEquals(0, index);
    }

    @Test
    @DisplayName("Deberia retornar el usuario por id")
    void findSellerById() {
        FollowerList returnedFollowerList = userRepository.findSellerById(2);
        assertTrue(returnedFollowerList.getUser().getUser_id().equals(2));
    }

    @Test
    @DisplayName("Deberia retornar el usuario por id")
    void findClientById() {
        FollowerList returnedFollowerList = userRepository.findClientById(1);
        assertTrue(returnedFollowerList.getUser().getUser_id().equals(1));
    }

    @Test
    @DisplayName("Deberia retornar el cliente por id despues de actualizarlo")
    void updateClients() {
        userRepository.saveClient(followerList);
        userRepository.updateClients(0, followerList);
        assertEquals(followerList, userRepository.findClientById(1));
    }

    @Test
    @DisplayName("Deberia retornar el vendedor por id despues de actualizarlo")
    void updateSellers() {
        userRepository.saveSeller(followerList);
        userRepository.updateSellers(0, followerList);
        assertEquals(followerList, userRepository.findSellerById(1));
    }
}