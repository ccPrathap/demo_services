DROP TABLE IF EXISTS messages;
 
CREATE TABLE messages (
  id INT AUTO_INCREMENT PRIMARY KEY,
  message VARCHAR(250) NOT NULL,
  description VARCHAR(250) DEFAULT NULL
);
 
INSERT INTO messages (message, description) VALUES
  ('Hello World', 'Sample message'),
  ('Welcome', null);