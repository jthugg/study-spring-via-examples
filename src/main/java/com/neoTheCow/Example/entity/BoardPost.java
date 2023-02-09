package com.neoTheCow.Example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BoardPost {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Setter
	private String title;
	private String writer;
	private String password;
	@Setter
	private LocalDateTime lastEdit;
	@Setter
	private String content;
	@Setter
	private Boolean blocked;

	@Builder
	public BoardPost(
			String title,
			String writer,
			String password,
			LocalDateTime lastEdit,
			String content,
			Boolean blocked
	) {
		this.title = title;
		this.writer = writer;
		this.password = password;
		this.lastEdit = lastEdit;
		this.content = content;
		this.blocked = blocked;
	}
}
