package com.neoTheCow.Example.service;

import com.neoTheCow.Example.dto.BoardPostDto;
import com.neoTheCow.Example.entity.BoardPost;
import com.neoTheCow.Example.repository.BoardPostRepository;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardPostService {

	private final BoardPostRepository boardPostRepository;

	public Long createPost(BoardPostDto dto) {
		BoardPost post = BoardPost.builder()
				.title(dto.getTitle())
				.writer(dto.getWriter())
				.password(dto.getPassword())
				.lastEdit(dto.getLastEdit())
				.content(dto.getContent())
				.blocked(dto.getBlocked())
				.build();
		boardPostRepository.saveAndFlush(post);
		return post.getId();
	}

	private Pageable getPageRequest(Integer page, Integer size) throws IllegalAccessException {
		if (page < 1 || size < 1) {
			throw new IllegalAccessException("invalid access.");
		}
		return PageRequest.of(page - 1, size, Sort.by(Direction.DESC, "id"));
	}

	public Slice<BoardPost> getPosts(Integer page, Integer size) throws IllegalAccessException {
		Slice<BoardPost> posts = boardPostRepository.findSliceBy(getPageRequest(page, size));
		if (posts.isEmpty() && page > 1) {
			throw new IllegalAccessException("invalid access.");
		}
		return posts;
	}

	public Slice<BoardPost> searchPosts(
			String search,
			String keyword,
			Integer page,
			Integer size
	) throws IllegalAccessException {
		if (search.equals("title")) {
			Slice<BoardPost> posts = boardPostRepository.findByTitleContaining(keyword, getPageRequest(page, size));
			if (posts.isEmpty() && page > 1) {
				throw new IllegalAccessException("invalid access.");
			}
			return posts;
		}
		if (search.equals("content")) {
			Slice<BoardPost> posts = boardPostRepository.findByContentContaining(keyword, getPageRequest(page, size));
			if (posts.isEmpty() && page > 1) {
				throw new IllegalAccessException("invalid access.");
			}
			return posts;
		}
		if (search.equals("writer")) {
			Slice<BoardPost> posts = boardPostRepository.findByWriterContaining(keyword, getPageRequest(page, size));
			if (posts.isEmpty() && page > 1) {
				throw new IllegalAccessException("invalid access.");
			}
			return posts;
		}
		throw new IllegalAccessException("invalid access");
	}

	public BoardPost getPost(Long id) throws NoSuchElementException {
		return boardPostRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("post not found."));
	}

	public Long updatePost(
			Long id,
			BoardPostDto dto
	) throws NoSuchElementException, IllegalAccessException {
		BoardPost boardPost = boardPostRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("post not found."));
		if (dto.getPassword().equals(boardPost.getPassword())) {
			boardPost.setTitle(dto.getTitle());
			boardPost.setLastEdit(dto.getLastEdit());
			boardPost.setContent(dto.getContent());
			boardPostRepository.saveAndFlush(boardPost);
			return id;
		}
		throw new IllegalAccessException("invalid access.");
	}

	public void deletePost(Long id) {
		boardPostRepository.deleteById(id);
	}
}
