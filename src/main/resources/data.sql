INSERT INTO contacts (id_contact, first_name, last_name, email, phone_number) VALUES
  (1, 'John', 'Snow', 'john.snow@gmail.com', '992-557-547'),
  (2, 'Daenerys', 'Targaryen', 'daenerys.targaryen@gmail.com', '123-685-221'),
  (3, 'Sansa', 'Stark', 'sansa.stark@gmail.com', '378-566-147'),
  (4, 'Arya', 'Stark', 'arya.stark@gmail.com', '323-665-782');

INSERT INTO tags (id_tag, tag_name) VALUES
  (1, 'HOME'),
  (2, 'STUDIES'),
  (3, 'FAMILY');

INSERT INTO contact_tag (tag_id, contact_id) VALUES
  (1, 1);
--   (2, 2),
--   (3, 3),
--   (4, 4);