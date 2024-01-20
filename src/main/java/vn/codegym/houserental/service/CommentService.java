package vn.codegym.houserental.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.codegym.houserental.exception.CommonException;
import vn.codegym.houserental.model.Booking;
import vn.codegym.houserental.model.Comment;
import vn.codegym.houserental.model.House;
import vn.codegym.houserental.model.account.User;
import vn.codegym.houserental.repository.BookingRepository;
import vn.codegym.houserental.repository.CommentRepository;
import vn.codegym.houserental.repository.HouseRepository;
import vn.codegym.houserental.repository.UserRepository;
import vn.codegym.houserental.requests.CommentRequest;
import vn.codegym.houserental.service.impl.HouseServiceImpl;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {
    @Autowired BookingService bookingService;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final HouseRepository houseRepository;
    public CommentService(CommentRepository commentRepository,
                          UserRepository userRepository,
                          HouseRepository houseRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.houseRepository = houseRepository;
    }

    public Comment save (CommentRequest request) throws CommonException {
        List<Booking> bookings = bookingService.findByUserIdAndHouseIdAndStatusAndDeleteFlag(request.getUserId(),request.getHouseId());
        if (bookings.isEmpty()) {
            throw new CommonException("Không tìm thấy tài khoản hoặc thông tin đặt phòng không hợp lệ");
        }
        else {
            Comment comment = new Comment();
            User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new CommonException("Không tìm thấy tài khoản"));
            House house = houseRepository.findById(request.getHouseId()).orElseThrow(() -> new CommonException("Không tìm tấy nhà"));
            comment.setUser(user);
            comment.setHouse(house);
            comment.setCreatedAt(LocalDateTime.now());
            comment.setStar(request.getStar());
            comment.setContent(request.getContent());
            return commentRepository.save(comment);
        }

    }

    public List<Comment> findAllByHouseId(Long houseId) {
        return commentRepository.findAllByHouseIdAndDeleteFlag(houseId,false);
    }


}
