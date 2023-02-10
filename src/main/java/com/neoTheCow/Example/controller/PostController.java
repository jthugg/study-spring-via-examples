package com.neoTheCow.Example.controller;

import com.neoTheCow.Example.dto.BoardPostDto;
import com.neoTheCow.Example.dto.PasswordDto;
import com.neoTheCow.Example.service.BoardPostService;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

	private final BoardPostService boardPostService;

	@PostMapping("/post")
	public ResponseEntity<?> createPost(@RequestBody BoardPostDto dto) {
		return ResponseEntity.status(201).body(boardPostService.createPost(dto));
	}

	@GetMapping("/get")
	public ResponseEntity<?> getPosts(
			@RequestParam("page") Integer page,
			@RequestParam("size") Integer size
	) {
		try {
			return ResponseEntity.status(200).body(boardPostService.getPosts(page, size));
		} catch (IllegalAccessException exception) {
			return ResponseEntity.status(400).body(exception.getMessage());
		}
	}

	@GetMapping("/search/get")
	public ResponseEntity<?> searchPosts(
			@RequestParam("search") String search,
			@RequestParam("keyword") String keyword,
			@RequestParam("page") Integer page,
			@RequestParam("size") Integer size
	) {
		try {
			return ResponseEntity.status(200).body(boardPostService.searchPosts(search, keyword, page, size));

		} catch (IllegalAccessException exception) {
			return ResponseEntity.status(400).body(exception.getMessage());
		}
	}

	@GetMapping("/{id}/get")
	public ResponseEntity<?> getPost(@PathVariable("id") Long id) {
		try {
			return ResponseEntity.status(200).body(boardPostService.getPost(id));
		} catch (NoSuchElementException exception) {
			return ResponseEntity.status(400).body(exception.getMessage());
		}
	}

	@PostMapping("/check")
	public ResponseEntity<?> checkPassword(@RequestBody PasswordDto dto) {
		try {
			return ResponseEntity.status(200)
					.body(boardPostService.getPost(dto.getId()).getPassword().equals(dto.getPassword()));
		} catch (NoSuchElementException exception) {
			return ResponseEntity.status(400).body(exception.getMessage());
		}
	}

	@PutMapping("/{id}/put")
	public ResponseEntity<?> updatePost(
			@PathVariable("id") Long id,
			@RequestBody BoardPostDto dto
	) {
		try {
			return ResponseEntity.status(200).body(boardPostService.updatePost(id, dto));
		} catch (NoSuchElementException | IllegalAccessException exception) {
			return ResponseEntity.status(400).body(exception.getMessage());
		}
	}

	@DeleteMapping("/{id}/delete")
	public ResponseEntity<?> deletePost(@PathVariable("id") Long id) {
		boardPostService.deletePost(id);
		return ResponseEntity.status(200).build();
	}
}
