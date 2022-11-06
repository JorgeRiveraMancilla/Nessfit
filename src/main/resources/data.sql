INSERT INTO `nessfit`.roles (id, name)
VALUES (1, 'Administrador');
INSERT INTO `nessfit`.roles (id, name)
VALUES (2, 'Administrativo');
INSERT INTO `nessfit`.roles (id, name)
VALUES (3, 'Cliente');

INSERT INTO `nessfit`.users (rut, email, first_name, last_name, password, phone, status, id_role)
VALUES ('17977139K', 'antonio.barraza.guzman@gmail.com', 'Antonio', 'Barraza', '$2a$10$sBHSQ4CahXOCjbuyNyDu7e8VAcB5Exaq6v7brxSCGlJiV52MK6X2u', 111111111111, 1, 1);

INSERT INTO `nessfit`.categories (id, name)
VALUES (1, 'Cancha');
INSERT INTO `nessfit`.categories (id, name)
VALUES (2, 'Gimnasio');
INSERT INTO `nessfit`.categories (id, name)
VALUES (3, 'Piscina');
INSERT INTO `nessfit`.categories (id, name)
VALUES (4, 'Quincho');
INSERT INTO `nessfit`.categories (id, name)
VALUES (5, 'Estadio');