package kz.singularity.rabbitmqemailsender.service;

import kz.singularity.rabbitmqemailsender.Repository.UsersRepository;
import kz.singularity.rabbitmqemailsender.constants.UsersConstants;
import kz.singularity.rabbitmqemailsender.entity.Users;
import kz.singularity.rabbitmqemailsender.exception.ResourceNotFoundException;
import kz.singularity.rabbitmqemailsender.exception.UserAlreadyExistsException;
import kz.singularity.rabbitmqemailsender.mapper.UsersMapper;
import kz.singularity.rabbitmqemailsender.model.EmailDetails;
import kz.singularity.rabbitmqemailsender.model.RequestDto;
import kz.singularity.rabbitmqemailsender.model.UserDetailsDto;
import org.apache.catalina.User;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsersServiceImpl implements UsersService{
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;
    // some
    @Value("${rabbitmq.exchange.email.name}")
    private String emailExchange;
    @Value("${rabbitmq.binding.email.name}")
    private String emailRoutingKey;
    @Override
    public void registerUser(RequestDto requestDto) {
        if (usersRepository.existsByEmail(requestDto.getEmail())) {
            throw new UserAlreadyExistsException(UsersConstants.USER_ALREADY_EXISTS);
        }
        Users user = UsersMapper.mapToUser(new Users(), requestDto);
        usersRepository.save(user);
        rabbitTemplate.convertAndSend(
                emailExchange,
                emailRoutingKey,
                EmailDetails.builder()
                        .messageBody("Regestration is Successfull with mail id: " + requestDto.getEmail())
                        .recipient(requestDto.getEmail())
                        .subject("SUCCESSFULL REGISTRATION")
                        .build());

    }

    @Override
    public UserDetailsDto getUserByEmail(String email) {
        if (!usersRepository.existsByEmail(email)) {
            throw new ResourceNotFoundException(UsersConstants.USER_NOT_FOUND);
        }

        Users user = usersRepository.findByEmail(email);
        return UsersMapper.mapToUserDetails(new UserDetailsDto(), user);
    }

    @Override
    public List<UserDetailsDto> getAllUsers() {
        List<Users> usersList = usersRepository.findAll();
        if(usersList.isEmpty()) {
            throw new ResourceNotFoundException((UsersConstants.USER_NOT_FOUND));
        }
        List<UserDetailsDto> userDetailsDtoList = new ArrayList<>();

        usersList.forEach(users -> {
            userDetailsDtoList.add(UsersMapper.mapToUserDetails(new UserDetailsDto(), users));
        });
        return userDetailsDtoList;
    }

    @Override
    public boolean updateUser(RequestDto requestDto) {
        return false;
    }

    @Override
    public boolean deleteUser(String email) {
        return false;
    }
}
