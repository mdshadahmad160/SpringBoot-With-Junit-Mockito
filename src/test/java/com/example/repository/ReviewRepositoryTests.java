package com.example.repository;
import com.example.entity.Review;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import java.util.Optional;

/**
 * @author Shad Ahmad
 * This  Test is Responsible to ReviewTests Of  Repository layer
 */

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2, replace = AutoConfigureTestDatabase.Replace.NONE)
public class ReviewRepositoryTests {

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void ReviewRepository_SaveAll_ReturnSavedReview() {
        Review review = Review.builder()
                .title("title")
                .content("content")
                .stars(5)
                .build();
        Review savedReview = reviewRepository.save(review);
        Assertions.assertThat(savedReview).isNotNull();
        Assertions.assertThat(savedReview.getId()).isGreaterThan(0);
    }

    @Test
    public void ReviewRepository_GetAll_ReturnsMoreThanOneReview() {
        Review review = Review.builder()
                .title("title")
                .content("content")
                .stars(5)
                .build();
        Review review2 = Review.builder()
                .title("title")
                .content("content")
                .stars(5)
                .build();
        reviewRepository.save(review);
        reviewRepository.save(review2);

        List<Review> reviewList = reviewRepository.findAll();
        Assertions.assertThat(reviewList).isNotNull();
        Assertions.assertThat(reviewList.size()).isEqualTo(2);

    }

    @Test
    public void ReviewRepository_FindById_ReturnsSavedReview() {
        Review review = Review.builder()
                .title("title")
                .content("content")
                .stars(5)
                .build();
        reviewRepository.save(review);

        Review reviewReturn = reviewRepository.findById(review.getId()).get();
        Assertions.assertThat(reviewReturn).isNotNull();

    }

    @Test
    public void ReviewRepository_UpdateReview_ReturnReview() {
        Review review = Review.builder()
                .title("title")
                .content("content")
                .stars(5)
                .build();
        reviewRepository.save(review);

        Review savedReview = reviewRepository.findById(review.getId()).get();
        savedReview.setTitle("title");
        savedReview.setContent("content");
        Review updatedReview = reviewRepository.save(savedReview);
        Assertions.assertThat(updatedReview.getTitle()).isNotNull();
        Assertions.assertThat(updatedReview.getContent()).isNotNull();


    }

    @Test
    public void ReviewRepository_ReviewDelete_ReturnReviewIsEmpty() {
        Review review = Review.builder()
                .title("title")
                .content("content")
                .stars(5)
                .build();
        reviewRepository.save(review);
        reviewRepository.deleteById(review.getId());
        Optional<Review> reviewReturn = reviewRepository.findById(review.getId());
        Assertions.assertThat(reviewReturn).isEmpty();


    }

}

