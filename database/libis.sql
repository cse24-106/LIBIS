DROP DATABASE IF EXISTS libis;
CREATE DATABASE libis CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE libis;

CREATE TABLE members (
                           `member_Id` int NOT NULL AUTO_INCREMENT,
                           `member_number` varchar(20) NOT NULL,
                           `full_name` varchar(150) NOT NULL,
                           `firstName` varchar(150) NOT NULL,
                           `lastName` varchar(100) NOT NULL,
                           `email` varchar(255) NOT NULL,
                           `password` varchar(255) NOT NULL,
                           `username` varchar(50) NOT NULL,
                           `role` varchar(20) NOT NULL,
                           PRIMARY KEY (`member_Id`),
                           UNIQUE KEY `email` (`email`),
                           UNIQUE KEY `member_number_UNIQUE` (`member_number`),
                           UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE books (
                         `book_Id` int NOT NULL AUTO_INCREMENT,
                         `title` varchar(255) NOT NULL,
                         `author` varchar(150) NOT NULL,
                         `genre` varchar(100) NOT NULL,
                         `published_year` int NOT NULL,
                         `synopsis` text NOT NULL,
                         `isbn` varchar(20) NOT NULL,
                         PRIMARY KEY (`book_Id`),
                         UNIQUE KEY `isbn_UNIQUE` (`isbn`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE loans (
                                 `loan_id` INT NOT NULL AUTO_INCREMENT,
                                 `member_id` INT NOT NULL,
                                 `book_id` INT NOT NULL,
                                 `borrowed_on` DATE NOT NULL,
                                 `returned_on` DATE NULL,
                                 PRIMARY KEY (`loan_id`),
                                 INDEX `fk_loans_members_idx` (`member_id` ASC) VISIBLE,
                                 INDEX `fk_loans_books_idx` (`book_id` ASC) VISIBLE,
                                 CONSTRAINT `fk_loans_members`
                                     FOREIGN KEY (`member_id`)
                                         REFERENCES `libis`.`members` (`member_Id`)
                                         ON DELETE CASCADE
                                         ON UPDATE NO ACTION,
                                 CONSTRAINT `fk_loans_books`
                                     FOREIGN KEY (`book_id`)
                                         REFERENCES `libis`.`books` (`book_Id`)
                                         ON DELETE CASCADE
                                         ON UPDATE NO ACTION);

-- seeding librarians and  members
INSERT INTO members (member_Id, member_number, full_name, firstName, lastName, email, password, username, role) VALUES
    (1,'MEM-001','Felix Kagwi','Felix','Kagwi','felix.kagwi@example.com','TestPass123!','felixkagwi','librarian'),
    (2,'MEM-002','Amara Molefe','Amara','Molefe','amara.molefe@example.com','TestPass123!','amaramolefe','member'),
    (3,'MEM-003','Thabo Nkosi','Thabo','Nkosi','thabo.nkosi@example.com','TestPass123!','thabonkosi','member'),
    (4,'MEM-004','Naledi Dube','Naledi','Dube','naledi.dube@example.com','TestPass123!','naledidube','member'),
    (5,'MEM-005','Kabelo Phiri','Kabelo','Phiri','kabelo.phiri@example.com','TestPass123!','kabelophiri','member'),
    (6,'MEM-006','Lerato Mokoena','Lerato','Mokoena','lerato.mokoena@example.com','TestPass123!','leratomokena','member'),
    (7,'MEM-007','Neo Modise','Neo','Modise','neo.modise@example.com','TestPass123!','neomodise','member'),
    (8,'MEM-008','Boitumelo	Molefe','Boitumelo','Molefe','boitumelo.molefe@example.com','TestPass123!','boitumelomolefe','member'),
    (9,'MEM-009','Kagiso Tshabalala','Kagiso','Tshabalala','kagiso.tshabalala@example.com','TestPass123!','kagisotsabalala','member'),
    (10,'MEM-010','Mpho Seboni','Mpho','Seboni','mpho.seboni@example.com','TestPass123!','mphoseboni','member'),
    (11,'MEM-011','Aisha Patel','Aisha','Patel','aisha.patel@example.com','TestPass123!','aishapatel','librarian'),
    (12,'MEM-012','David Kamau','David','Kamau','david.kamau@example.com','TestPass123!','davidkamau','member');

-- seeding loans
INSERT INTO loans (loan_id, member_id, book_id, borrowed_on, returned_on) VALUES
    (1,3,6,'2026-07-14','2026-07-28'),
    (2,3,7,'2026-03-04','2026-03-14'),
    (3,5,3,'2026-06-24','2026-07-01'),
    (4,1,9,'2026-02-20','2026-03-21'),
    (5,7,4,'2026-10-07',NULL),
    (6,15,2,'2026-05-04','2026-06-13'),
    (7,13,12,'2026-07-08','2026-07-14'),
    (8,3,16,'2026-09-01',NULL),
    (9,5,10,'2026-11-08','2026-11-14'),
    (10,9,19,'2026-06-12','2026-06-14'),
    (11,5,13,'2026-11-14',NULL),
    (12,5,11,'2026-11-14',NULL);

-- seeding books
INSERT INTO books (book_Id, title, author, genre, published_year, synopsis, isbn) VALUES
    (1,'Clean Code','Robert C. Martin','Programming',2008,'A guide to writing readable, maintainable and well-structured software.','978-0132350884'),
    (2,'Effective Java','Joshua Bloch','Programming',2018,'Practical techniques and best practices for writing robust Java programs.','978-0134685991'),
    (3,'Head First Java','Kathy Sierra and Bert Bates','Programming',2022,'An accessible introduction to Java programming concepts and object-oriented development.','978-1491910771'),
    (4,'The Pragmatic Programmer','David Thomas and Andrew Hunt','Software Engineering',2019,'Advice and techniques for becoming a more effective and adaptable software developer.','978-0135957059'),
    (5,'Introduction to Algorithms','Thomas H. Cormen et al.','Computer Science',2022,'A comprehensive introduction to algorithms, data structures and computational problem-solving.','978-0262046305'),
    (6,'Design Patterns','Erich Gamma et al.','Software Engineering',1994,'A collection of reusable object-oriented design solutions for common software problems.','978-0201633610'),
    (7,'Database System Concepts','Abraham Silberschatz et al.','Databases',2019,'An introduction to relational databases, SQL, transactions and database system design.','978-1260084504'),
    (8,'Computer Networking: A Top-Down Approach','James Kurose and Keith Ross','Networking',2021,'Explores networking principles, protocols and Internet architecture from the application layer downward.','978-0135429334'),
    (9,'Operating System Concepts','Abraham Silberschatz et al.','Operating Systems',2018,'Explains operating system architecture, processes, memory management and file systems.','978-1119329480'),
    (10,'The Linux Command Line','William Shotts','Linux',2019,'A practical guide to navigating Linux systems and using shell commands.','978-1-71850-452-3'),
    (11,'Python Crash Course','Eric Matthes','Programming',2023,'A hands-on introduction to Python programming through exercises and practical projects.','978-1718505148'),
    (12,'Artificial Intelligence: A Modern Approach','Stuart Russell and Peter Norvig','Artificial Intelligence',2020,'An introduction to artificial intelligence, intelligent agents, search and machine learning.','978-0137505135');
