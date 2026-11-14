DROP DATABASE IF EXISTS libis;
CREATE DATABASE libis CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE libis;

CREATE TABLE members (
                         member_id INT AUTO_INCREMENT PRIMARY KEY,
                         member_number VARCHAR(50) NOT NULL UNIQUE,
                         first_name VARCHAR(100) NOT NULL,
                         last_name VARCHAR(100) NOT NULL,
                         email VARCHAR(150) NOT NULL UNIQUE,
                         username VARCHAR(100) NOT NULL UNIQUE,
                         password VARCHAR(255) NOT NULL,
                         role VARCHAR(50) NOT NULL DEFAULT 'member'
) ENGINE=InnoDB;

CREATE TABLE books (
                       book_id INT AUTO_INCREMENT PRIMARY KEY,
                       isbn VARCHAR(50) NOT NULL UNIQUE,
                       title VARCHAR(255) NOT NULL,
                       author VARCHAR(255) NOT NULL,
                       genre VARCHAR(100),
                       published_year INT NOT NULL,
                       synopsis TEXT
) ENGINE=InnoDB;

CREATE TABLE loans (
                       loan_id INT AUTO_INCREMENT PRIMARY KEY,
                       member_id INT NOT NULL,
                       book_id INT NOT NULL,
                       borrowed_on DATE NOT NULL,
                       returned_on DATE DEFAULT NULL,
                       CONSTRAINT fk_loans_member FOREIGN KEY (member_id) REFERENCES members(member_id) ON DELETE CASCADE,
                       CONSTRAINT fk_loans_book FOREIGN KEY (book_id) REFERENCES books(book_id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- seeding a librarian and 5 members
INSERT INTO members (member_number, first_name, last_name, email, username, password, role) VALUES
('MEM-001', 'Admin', 'Librarian', 'librarian@libis.bw', 'admin', 'password123', 'librarian'),
('MEM-002', 'Kabo', 'Seretse', 'kabo.seretse@gmail.com', 'kabo', 'password123', 'member'),
('MEM-003', 'Neo', 'Molefe', 'neo.molefe@gmail.com', 'neo', 'password123', 'member'),
('MEM-004', 'Thabo', 'Mothibamele', 'thabo.m@gmail.com', 'thabo', 'password123', 'member'),
('MEM-005', 'Lesego', 'Tau', 'lesego.tau@gmail.com', 'lesego', 'password123', 'member'),
('MEM-006', 'Mpho', 'Kgomotso', 'mpho.k@gmail.com', 'mpho', 'password123', 'member');

-- seeding 10 books
INSERT INTO books (isbn, title, author, genre, published_year, synopsis) VALUES
('978-0140283334', 'Things Fall Apart', 'Chinua Achebe', 'Classic Fiction', 1958, 'A masterpiece depicting the colonization of Igbo society in Nigeria.'),
('978-0385474542', 'The Power of One', 'Bryce Courtenay', 'Historical Fiction', 1989, 'A powerful coming-of-age story set in 1930s South Africa.'),
('978-0143039008', 'The No. 1 Ladies Detective Agency', 'Alexander McCall Smith', 'Mystery', 1998, 'Charming detective stories set in sunny Botswana.'),
('978-0435905590', 'So Long a Letter', 'Mariama Bâ', 'Epistolary Fiction', 1979, 'A poignant look at widowhood and societal expectations in West Africa.'),
('978-0624049388', 'Tsotsi', 'Athol Fugard', 'Drama', 1980, 'A gritty look at redemption in the townships of Johannesburg.'),
('978-0007150618', 'Cry, the Beloved Country', 'Alan Paton', 'Tragedy', 1948, 'A haunting novel about race relations in pre-apartheid South Africa.'),
('978-0571058089', 'Waiting for Godot', 'Samuel Beckett', 'Absurdist Fiction', 1952, 'Two characters wait for someone who never arrives.'),
('978-0141182803', '1984', 'George Orwell', 'Dystopian', 1949, 'A dystopian masterwork exploring totalitarian surveillance.'),
('978-0451524935', 'Animal Farm', 'George Orwell', 'Political Satire', 1945, 'An allegorical novella about the corruption of revolutionary ideals.'),
('978-0140449136', 'The Odyssey', 'Homer', 'Epic Poetry', -800, 'The ancient epic journey of Odysseus returning home.')

-- seeding loans
INSERT INTO loans (member_id, book_id, borrowed_on, returned_on) VALUES
(2, 1, '2026-10-01', NULL),
(2, 2, '2026-10-03', NULL),
(2, 3, '2026-10-05', NULL),
(2, 4, '2026-09-01', '2026-09-15'),
(3, 5, '2026-10-04', NULL),
(3, 6, '2026-08-10', '2026-08-25'),
(4, 7, '2026-09-10', '2026-09-20'),
(4, 8, '2026-09-15', '2026-09-30')
