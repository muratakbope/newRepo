package kz.singularity.rabbitmqemailsender.mapper;

import kz.singularity.rabbitmqemailsender.entity.Users;
import kz.singularity.rabbitmqemailsender.model.RequestDto;
import kz.singularity.rabbitmqemailsender.model.UserDetailsDto;

public class UsersMapper {

    public static Users mapToUser(Users user, RequestDto requestDto) {
        user.setFirstName(requestDto.getFirstName());
        user.setLastName(requestDto.getLastName());
        user.setEmail(requestDto.getEmail());
        user.setPassword(requestDto.getPassword());
        return user;
    }
    public static UserDetailsDto mapToUserDetails(UserDetailsDto userDetailDTO,
                                                  Users user) {

       userDetailDTO.setFirstName(user.getFirstName());
       userDetailDTO.setLastName(user.getLastName());
       userDetailDTO.setEmail(user.getEmail());
       return userDetailDTO;
    }
}
