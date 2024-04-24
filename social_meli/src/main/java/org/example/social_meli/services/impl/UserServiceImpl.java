package org.example.social_meli.services.impl;
import org.example.social_meli.exceptions.BadRequestException;
import org.example.social_meli.model.FollowerList;
import org.example.social_meli.model.User;
import org.example.social_meli.repository.IUserRepository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.social_meli.dto.UserDTO;
import org.example.social_meli.dto.UserResponseDTO;
import org.example.social_meli.exceptions.NotFoundException;
import org.example.social_meli.dto.UserCountResponseDTO;
import org.example.social_meli.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {


    @Autowired
    private IUserRepository userRepository;

    @Override
    public void followUser(Integer user_id, Integer user_id_to_follow) {

        if (!userRepository.existsById(user_id) || !userRepository.existsById(user_id_to_follow)) {
            throw new BadRequestException("Uno o ambos usuarios no existen", "USER_NOT_FOUND", "El usuario o el vendedor no existen en la base de datos");
        }else{
            User user = userRepository.findById(user_id);
            User userToFollow = userRepository.findById(user_id_to_follow);
            if(user_id.equals(user_id_to_follow)){
                throw new BadRequestException("No puedes seguirte a ti mismo");
            }
            if(!userToFollow.getIsSeller()){
                throw new BadRequestException("No puede seguirlo, el usuario no es vendedor.");
            }
            if(user.getIsSeller() && userToFollow.getIsSeller()){
                throw new BadRequestException("El vendedor no puede seguir a otro vendedor");
            }
            if (user.getIsSeller()) {
                throw new BadRequestException("El usuario es vendedor y no puede seguir.");
            }
            FollowerList seller = userRepository.findSellerByUser(userToFollow);
            if(seller == null){
                seller = new FollowerList(userToFollow);
            }
            FollowerList client = userRepository.findClientByUser(user);
            if(client == null){
                client = new FollowerList(user);
            }
            client.getFollower().add(userToFollow);
            seller.getFollower().add(user);
            userRepository.saveSeller(seller);
            userRepository.saveClient(client);
        }
    }


    @Override
    public UserCountResponseDTO countFollowers(Integer id) {
        return new UserCountResponseDTO(
                userRepository.getAllSellers().stream().filter(user -> user.getUser().getUser_id().
                        equals(id)).findFirst().get().getUser().getUser_id(),
                userRepository.getAllSellers().stream().filter(user -> user.getUser().getUser_id().
                        equals(id)).findFirst().get().getUser().getUser_name(),
                userRepository.countFollowers(id)
        );
    }

    @Autowired
    ObjectMapper objectMapper;
    @Override
    public UserResponseDTO getFollowers(Integer userId) {
        if(!userRepository.existsById(userId)){
            throw new NotFoundException("No existe el usuario "+userId);
        }
        FollowerList followerList = userRepository.getFollowerByUserId(userId);
        if (followerList == null){
            throw new NotFoundException("No tiene seguidores");
        }
        List<UserDTO> followers = followerList.getFollower().stream()
                .map(user -> objectMapper.convertValue(user,UserDTO.class))
                .collect(Collectors.toList());

        return new UserResponseDTO(userId,userRepository.getUsernameById(userId),followers);

    }
}
