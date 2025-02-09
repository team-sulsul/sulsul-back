ALTER TABLE member DROP INDEX idx__username;
ALTER TABLE member ADD INDEX idx_member_username (username);