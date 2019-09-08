INSERT INTO contacts (id_contact, first_name, last_name, email, phone_number) VALUES
  (1, 'John', 'Snow', 'john.snow@gmail.com', '992-557-547'),
  (2, 'Daenerys', 'Targaryen', 'daenerys.targaryen@gmail.com', '123-685-221'),
  (3, 'Sansa', 'Stark', 'sansa.stark@gmail.com', '378-566-147'),
  (4, 'Arya', 'Stark', 'arya.stark@gmail.com', '323-665-782');

INSERT INTO tags (id_tag, tag_name) VALUES
  (1, 'WORK'),
  (2, 'STUDIES'),
  (3, 'FAMILY'),
  (4, 'HIGH_SCHOOL');

INSERT INTO contact_tag (tag_id, contact_id) VALUES
  (1, 1),
  (2, 1),
  (2, 2),
  (3, 2),
  (3, 3);

INSERT INTO usersroles (id_role, name) VALUES
  (1, 'ROLE_USER');

INSERT INTO users (id_user, first_name, last_name, login, password, email, pesel) VALUES
  (1, 'Jan', 'Nowak', 'jan', '$2a$10$aKkykQ/TaABhZUMcVPdD3uJrnT7Ep0dnL9SgGfXXOPtcJOta2Altm', 'jnowak@gmail.com', '91011203366');