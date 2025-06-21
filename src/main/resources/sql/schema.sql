


CREATE DATABASE IF NOT EXISTS webtruyen CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE webtruyen;
select * from  webtruyen.stories


-- Người dùng (tuỳ chọn nếu có login)
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100),
    role VARCHAR(20) DEFAULT 'user',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Truyện
CREATE TABLE stories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    slug VARCHAR(255) NOT NULL UNIQUE,
    cover_image VARCHAR(500),
    author  VARCHAR(255) NOT NULL,
    chapter_number int,
    description MEDIUMTEXT,
    status ENUM('Full', 'Ongoing', 'Paused', 'Dropped') DEFAULT 'Ongoing',
    active BOOLEAN  NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Chương
CREATE TABLE chapters (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    story_id BIGINT,
    chapter_number INT,
    title VARCHAR(255),
    content LONGTEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	active BOOLEAN  NOT NULL DEFAULT TRUE,
    FOREIGN KEY (story_id) REFERENCES stories(id) ON DELETE CASCADE
);

-- Thể loại
ALTER TABLE WEBTRUYEN.genres ADD COLUMN active BOOLEAN NOT NULL DEFAULT TRUE;
ALTER TABLE WEBTRUYEN.chapters ADD COLUMN active BOOLEAN NOT NULL DEFAULT TRUE;
ALTER TABLE WEBTRUYEN.stories ADD COLUMN chapter_number INT ;
ALTER TABLE WEBTRUYEN.stories ADD COLUMN author VARCHAR(255) ;

CREATE TABLE genres (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    slug VARCHAR(100) NOT NULL UNIQUE,
    active BOOLEAN  NOT NULL DEFAULT TRUE
);

-- Liên kết truyện - thể loại
CREATE TABLE story_genres (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    story_id BIGINT,
    genre_id BIGINT,
    FOREIGN KEY (story_id) REFERENCES stories(id) ON DELETE CASCADE,
    FOREIGN KEY (genre_id) REFERENCES genres(id) ON DELETE CASCADE
);

-- Tags như: Hot, Full, New
CREATE TABLE tags (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    color VARCHAR(20)
);

-- Liên kết truyện - tag
CREATE TABLE story_tags (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    story_id BIGINT,
    tag_id BIGINT,
    FOREIGN KEY (story_id) REFERENCES stories(id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tags(id) ON DELETE CASCADE
);

-- (Tuỳ chọn) bảng views
CREATE TABLE views (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    story_id BIGINT,
    views INT DEFAULT 0,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (story_id) REFERENCES stories(id) ON DELETE CASCADE
);
