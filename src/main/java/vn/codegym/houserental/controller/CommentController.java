package vn.codegym.houserental.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.codegym.houserental.dto.BookingDTO;
import vn.codegym.houserental.dto.CommentDTO;
import vn.codegym.houserental.exception.CommonException;
import vn.codegym.houserental.model.Booking;
import vn.codegym.houserental.model.Comment;
import vn.codegym.houserental.requests.CommentRequest;
import vn.codegym.houserental.response.ApiResponse;
import vn.codegym.houserental.response.bookingresponse.CancelBookingResponse;
import vn.codegym.houserental.service.BookingService;
import vn.codegym.houserental.service.CommentService;
import vn.codegym.houserental.utils.ModelMapperUtil;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private ModelMapperUtil mapperUtil;
    @Autowired
    private CommentService commentService;

    @GetMapping("/house/{houseId}")
    public ResponseEntity<List<CommentDTO>> findAllByHouseId(@PathVariable Long houseId) {
        List<Comment> comments = commentService.findAllByHouseId(houseId);
        if (comments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(mapperUtil.mapList(comments, CommentDTO.class), HttpStatus.OK);

    }

    @PostMapping("")
    public ResponseEntity<?> createComment(@RequestBody CommentRequest commentRequest) {
        try {
            Comment savedComment = commentService.save(commentRequest);
            return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
        } catch (CommonException e) {
            return new ResponseEntity<>(new ApiResponse("01", e.getMessage(), null), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse("99", "Lỗi hệ thống", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
        try {
            commentService.deleteComment(commentId);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new CancelBookingResponse("ER-B2-02"), HttpStatus.BAD_REQUEST);
        }
    }



}
