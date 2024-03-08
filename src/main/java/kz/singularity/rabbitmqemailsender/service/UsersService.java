package kz.singularity.rabbitmqemailsender.service;

import kz.singularity.rabbitmqemailsender.model.RequestDto;
import kz.singularity.rabbitmqemailsender.model.UserDetailsDto;

import java.util.List;


public interface UsersService {
    public void registerUser(RequestDto requestDto);

    public UserDetailsDto getUserByEmail(String email);
    public List<UserDetailsDto> getAllUsers();
    public boolean updateUser(RequestDto requestDto);

    public boolean deleteUser(String email);


}
