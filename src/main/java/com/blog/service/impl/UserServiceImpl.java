package com.blog.service.impl;

import com.blog.dto.PagedDTO;
import com.blog.dto.RegisterUserDTO;
import com.blog.dto.UserDTO;
import com.blog.exception.BlogException;
import com.blog.model.User;
import com.blog.repository.UserRepository;
import com.blog.service.UserService;
import java.util.List;
import java.util.Objects;
import org.jboss.logging.Logger;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

  private final Logger logger = Logger.getLogger(UserServiceImpl.class);

  UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public RegisterUserDTO saveUser(RegisterUserDTO userDTo) {
    logger.info("Enter in save user service ..");
    try {
      User user =
          User.builder()
              .name(userDTo.getName())
              .userName(userDTo.getUserName())
              .email(userDTo.getEmail())
              .gender(userDTo.getGender())
              .mobileNumber(userDTo.getMobileNumber())
              .build();
      user = userRepository.save(user);
      if (Objects.nonNull(user)) {
        userDTo.setId(user.getId());
      }
      return userDTo;
    } catch (DuplicateKeyException duplicateKeyException) {
      logger.error("Duplicate key Exception : ", duplicateKeyException);
      throw new BlogException(
          duplicateKeyException.getLocalizedMessage(), HttpStatus.CONFLICT.value());
    } catch (Exception e) {
      throw new BlogException(e.getMessage(), HttpStatus.BAD_REQUEST.value());
    }
  }

  @Override
  public UserDTO getUserByUserName(String userName) {
    User user = userRepository.findByUserName(userName);
    System.out.println(user);

    if (Objects.isNull(user)) {
      throw new BlogException(
          "Username Not found in Db:: " + userName, HttpStatus.NOT_FOUND.value());
    }
    UserDTO userDTO = new UserDTO();
    userDTO.setId(user.getId());
    userDTO.setUserName(user.getUserName());
    userDTO.setName(user.getName());
    userDTO.setEmail(user.getEmail());
    userDTO.setMobileNumber(user.getMobileNumber());
    userDTO.setGender(user.getGender());
    return userDTO;
  }

  @Override
  public PagedDTO<UserDTO> getUserList(Integer pageNo, Integer pageSize) {
    logger.info("Inside getUserList....");
    logger.debug("Page No :: " + pageNo + " Page Size :: " + pageSize);
    try {
      Pageable pageable = PageRequest.of(pageNo, pageSize);
      Page<User> pageList = userRepository.findAll(pageable);
      logger.info("User List :: " + pageList);
      List<User> userList1 = pageList.getContent();
      List<UserDTO> userDTOList =
          userList1.stream()
              .map(
                  user ->
                      UserDTO.builder()
                          .id(user.getId())
                          .name(user.getName())
                          .userName(user.getUserName())
                          .email(user.getEmail())
                          .gender(user.getGender())
                          .mobileNumber(user.getMobileNumber())
                          .build())
              .toList();
      PagedDTO<UserDTO> pagedDTO =
          PagedDTO.<UserDTO>builder()
              .data(userDTOList)
              .currentPage(pageList.getNumber())
              .totalElements(pageList.getTotalElements())
              .totalPages(pageList.getTotalPages())
              .dataSize(userDTOList.size())
              .build();

      logger.debug("Paged DTO :: " + pagedDTO);

      return pagedDTO;
    } catch (Exception exception) {
      throw new BlogException(exception.getMessage(), HttpStatus.BAD_REQUEST.value());
    }
  }

  @Override
  public UserDTO getUserById(String id) {
    return userRepository
        .findById(id)
        .map(
            user -> {
              UserDTO userDTO = new UserDTO();
              userDTO.setId(user.getId());
              userDTO.setUserName(user.getUserName());
              userDTO.setName(user.getName());
              userDTO.setEmail(user.getEmail());
              userDTO.setMobileNumber(user.getMobileNumber());
              userDTO.setGender(user.getGender());
              return userDTO;
            })
        .orElseThrow(
            () -> new BlogException("User Not found in Db:: " + id, HttpStatus.NOT_FOUND.value()));
  }

  @Override
  public void deleteUser(String userName) {
    logger.info("Inside deleteUser");
    try {
      Long deletedCount = userRepository.deleteByUserName(userName);
      logger.debug("User Deleted :: " + deletedCount);
      if (deletedCount == 0) {
        throw new BlogException("User Not found in Db:: " + userName, HttpStatus.NOT_FOUND.value());
      }
    } catch (Exception e) {
      throw new BlogException(e.getMessage(), HttpStatus.BAD_REQUEST.value());
    }
  }
}
