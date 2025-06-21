package springboot.webtruyen.model;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "chapters")
public class Chapter {

	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	 	private boolean active;
	    private int chapterNumber;
	    private String title;
	    
		@Lob
	    private String content;

	    @Column(name = "created_at", updatable = false)
	    private Timestamp createdAt;

	    @ManyToOne
	    @JoinColumn(name = "story_id", nullable = false)
	    private Story story;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public int getChapterNumber() {
			return chapterNumber;
		}

		public void setChapterNumber(int chapterNumber) {
			this.chapterNumber = chapterNumber;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public Timestamp getCreatedAt() {
			return createdAt;
		}

		public void setCreatedAt(Timestamp createdAt) {
			this.createdAt = createdAt;
		}

		public Story getStory() {
			return story;
		}

		public void setStory(Story story) {
			this.story = story;
		}
		public boolean isActive() {
			return active;
		}

		public void setActive(boolean active) {
			this.active = active;
		}


}
