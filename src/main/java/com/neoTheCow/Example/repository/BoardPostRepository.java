package com.neoTheCow.Example.repository;

import com.neoTheCow.Example.entity.BoardPost;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardPostRepository extends JpaRepository<BoardPost, Long> {

	/**
	 * return posts by given pageable
	 * @param pageable
	 * */
	Slice<BoardPost> findSliceBy(Pageable pageable);

	/**
	 * search posts if post title contains given keyword
	 * @param keyword
	 * @param pageable
	 * */
	Slice<BoardPost> findByTitleContaining(String keyword, Pageable pageable);

	/**
	 * search posts if post content contains given keyword
	 * @param keyword
	 * @param pageable
	 * */
	Slice<BoardPost> findByContentContaining(String keyword, Pageable pageable);

	/**
	 * search posts if post writer contains given keyword
	 * @param keyword
	 * @param pageable
	 * */
	Slice<BoardPost> findByWriterContaining(String keyword, Pageable pageable);
}
