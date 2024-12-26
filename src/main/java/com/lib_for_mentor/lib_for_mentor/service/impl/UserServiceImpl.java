package com.lib_for_mentor.lib_for_mentor.service.impl;

import com.lib_for_mentor.lib_for_mentor.entity.Book;
import com.lib_for_mentor.lib_for_mentor.entity.User;
import com.lib_for_mentor.lib_for_mentor.mapper.BookMapper;
import com.lib_for_mentor.lib_for_mentor.mapper.UserMapper;
import com.lib_for_mentor.lib_for_mentor.model.param.UserParamsDTO;
import com.lib_for_mentor.lib_for_mentor.model.request.UserRequestDTO;
import com.lib_for_mentor.lib_for_mentor.model.response.UserResponseDTO;
import com.lib_for_mentor.lib_for_mentor.repository.BookRepository;
import com.lib_for_mentor.lib_for_mentor.repository.UserRepository;
import com.lib_for_mentor.lib_for_mentor.service.UserService;
import com.lib_for_mentor.lib_for_mentor.specification.UserSpecification;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserSpecification userSpecification;
    private final BookMapper bookMapper;
    private final BookRepository bookRepository;

    @NotNull
    @Transactional
    public UserResponseDTO create(@NotNull UserRequestDTO request) {
        List<Book> books =  bookRepository.findAllById(request.getBooksId());
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .build();

        // Установка связи "книга -> пользователь"
        user.setBooks(books);
        userRepository.save(user);

        return userMapper.toUserResponse(user);
    }

    @NotNull
    @Transactional
    public UserResponseDTO updateUserInfo(@NotNull Integer id, @NotNull UserRequestDTO request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    @NotNull
    @Transactional
    public void deleteById(@NotNull Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
    }

    @NotNull
    @Transactional(readOnly = true)
    public Page<UserResponseDTO> getUsers(UserParamsDTO params, Integer offset, Integer limit) {
        PageRequest pageRequest = PageRequest.of(offset, limit);
        return userRepository.findAll(userSpecification.build(params), pageRequest)
                .map(userMapper::toUserResponse);
    }

    @NotNull
    public UserResponseDTO findById(@NotNull Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toUserResponse(user);
    }

    @NotNull
    @Transactional
    public UserResponseDTO assignBook(@NotNull Integer userId, @NotNull Integer bookId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        user.getBooks().add(book);
        userRepository.save(user);

        book.getUsers().add(user);

        return userMapper.toUserResponse(user);
    }

    @NotNull
    @Transactional
    public UserResponseDTO unassignBook(@NotNull Integer userId, @NotNull Integer bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.getBooks().remove(book);

        book.setAuthor(null);
        bookRepository.save(book);

        book.getUsers().remove(user);
        return userMapper.toUserResponse(user);
    }
}
