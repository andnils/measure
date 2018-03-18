CREATE TABLE heroes (
   id BIGSERIAL PRIMARY KEY,
   firstname VARCHAR(25) NOT NULL,
   lastname VARCHAR(25) NOT NULL,
   heroname VARCHAR(25) NOT NULL
);

INSERT INTO heroes (firstname, lastname, heroname) VALUES
 ('Clark', 'Kent', 'Superman'),
 ('Bruce', 'Wayne', 'Batman'),
 ('Diana', 'Prince', 'Wonder Woman')
;
