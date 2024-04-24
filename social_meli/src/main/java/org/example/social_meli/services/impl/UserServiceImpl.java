package org.example.social_meli.services.impl;
import org.example.social_meli.exceptions.BadRequestException;
import org.example.social_meli.model.FollowerList;
import org.example.social_meli.model.User;
import org.example.social_meli.repository.FollowerListRepository;
import org.example.social_meli.repository.UserRepository;
import org.example.social_meli.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FollowerListRepository followerListRepository; // Asegúrate que el nombre de la clase y del importe sean correctos

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
            FollowerList followerList = followerListRepository.findByUser(userToFollow);
            if(followerList == null){
                followerList = new FollowerList(userToFollow);
            }
            followerList.getFollower().add(user);
            followerListRepository.save(followerList);
        }
    }
}
