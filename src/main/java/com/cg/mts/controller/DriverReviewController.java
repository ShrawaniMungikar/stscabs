package com.cg.mts.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.mts.dto.ReviewResponseDTO;
import com.cg.mts.entities.DriverReview;
import com.cg.mts.service.ReviewServiceImpl;
import com.cg.mts.util.ReviewDTOConvertor;

@RestController
@RequestMapping("/DriverReview")
public class DriverReviewController {
	public static final Logger logger = LoggerFactory.getLogger(DriverReviewController.class);
	@Autowired
	ReviewServiceImpl reviewService;

	@Autowired
	ReviewDTOConvertor dtoConvertor;

	public DriverReviewController() {

		logger.info(" Review Rest Controller Called");
		System.err.println("Review Rest Controller called");
	}

	@GetMapping("/reviews/list")

	public List<DriverReview> listAllReviews() {
		logger.info("Listing all reviews");
		return reviewService.listallReviews();
	}

	@PostMapping("/register")

	public ResponseEntity<ReviewResponseDTO> addReview(@RequestBody DriverReview review) {

		DriverReview savedReview = reviewService.addReview(review);
		logger.info("Adding reviews");
		{
			ReviewResponseDTO dto = dtoConvertor.getReviewResponseDTO(review);
			return new ResponseEntity<ReviewResponseDTO>(dto, HttpStatus.OK);
		}
	}

	@GetMapping("driverName/{name}")

	public ResponseEntity<List<ReviewResponseDTO>> getReviewByDriverName(@PathVariable String driverName) {
		List<DriverReview> allReviews = reviewService.getReviewByDriverName(driverName);
		List<ReviewResponseDTO> allReviewDTO = new ArrayList<>();
		for (DriverReview review : allReviews) {
			allReviewDTO.add(dtoConvertor.getReviewResponseDTO(review));
		}
		return new ResponseEntity<List<ReviewResponseDTO>>(allReviewDTO, HttpStatus.OK);
	}

}
