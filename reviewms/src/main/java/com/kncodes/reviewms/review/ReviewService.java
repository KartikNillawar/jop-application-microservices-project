package com.kncodes.reviewms.review;

import java.util.List;
public interface ReviewService {
    List<Review> getAllReviews(Long companyId);

    boolean addReview(Long companyId, Review review);

    Review getReviewById(Long reviewId);

    boolean updateReviewById(Long ReviewId, Review review);

    boolean deleteReviewById(Long ReviewId);

}
