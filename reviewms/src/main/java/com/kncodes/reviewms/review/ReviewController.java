package com.kncodes.reviewms.review;

import com.kncodes.reviewms.review.messaging.ReviewMessageProducer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private ReviewService reviewService;
    private ReviewMessageProducer reviewMessageProducer;


    public ReviewController(ReviewService reviewService, ReviewMessageProducer reviewMessageProducer) {
        this.reviewService = reviewService;
        this.reviewMessageProducer = reviewMessageProducer;
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews(@RequestParam Long companyId)
    {
        return new ResponseEntity<>(reviewService.getAllReviews(companyId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> addReviews(@RequestParam Long companyId,@RequestBody Review review)
    {
        boolean isReviewAdded = reviewService.addReview(companyId,review);
        if(isReviewAdded) {
            reviewMessageProducer.sendMessage(review);
            return new ResponseEntity<>("Review Added Successfully", HttpStatus.OK);
        }else
            return new ResponseEntity<>("Review Not Added, Company not found",HttpStatus.OK);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReview(@PathVariable Long reviewId)
    {
        Review review = reviewService.getReviewById(reviewId);
        if(review!=null)
            return new ResponseEntity<>(review,HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<String> updateReviewById(@PathVariable Long reviewId, @RequestBody Review review)
    {
        boolean isUpdated = reviewService.updateReviewById(reviewId, review);
        if(isUpdated)
            return new ResponseEntity<>("The review is updated", HttpStatus.OK);
        else
            return new ResponseEntity<>("The review is not updated", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReviewById( @PathVariable Long reviewId)
    {
        boolean isdeleted = reviewService.deleteReviewById(reviewId);
        if(isdeleted)
            return new ResponseEntity<>("The review is deleted", HttpStatus.OK);
        else
            return new ResponseEntity<>("The review is not deleted", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/averageRating")
    public Double getAverageRating(@RequestParam Long companyId)
    {
        List<Review> reviewList = reviewService.getAllReviews(companyId);

        Double avgrating = reviewList.stream().mapToDouble(Review::getRating).average().orElse(0.0);
        System.out.println("Got the avg rating "+avgrating);
        return avgrating;
    }


}
