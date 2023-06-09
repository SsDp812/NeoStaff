package org.neoflex.business;

import org.neoflex.dto.response.user.UserCardDto;
import org.neoflex.dto.response.user.UserPreviewCardDto;
import org.neoflex.repository.UserInfoRepository;
import org.neoflex.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserCardDto findUserById(Long userId){
        return null;
    }


    public List<UserPreviewCardDto> findAll(){
        return null;
    }


}
