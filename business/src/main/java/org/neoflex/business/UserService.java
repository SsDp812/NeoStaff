package org.neoflex.business;

import jakarta.validation.constraints.Positive;
import org.neoflex.business.mapper.UserMapper;
import org.neoflex.common.exceptions.user.UserNotFoundException;
import org.neoflex.dto.response.user.UserCardDto;
import org.neoflex.dto.response.user.UserPreviewCardDto;
import org.neoflex.model.User;
import org.neoflex.repository.UserInfoRepository;
import org.neoflex.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserCardDto findUserById(@Positive Long userId) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            return UserMapper.getUserCard(optionalUser.get());
        } else {
            throw new UserNotFoundException();
        }
    }


    public List<UserPreviewCardDto> findAll() {
        List<User> users = userRepository.findAll();
        List<UserPreviewCardDto> userCards = new ArrayList<>();
        for (var user : users) {
            userCards.add(UserMapper.getUserPreviewCard(user));
        }
        return userCards;
    }


}
