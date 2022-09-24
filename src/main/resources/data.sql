INSERT INTO `nessfit`.roles (id, name) VALUES (1, 'ADMINISTRADOR');
INSERT INTO `nessfit`.roles (id, name) VALUES (2, 'ADMINISTRATIVO');
INSERT INTO `nessfit`.roles (id, name) VALUES (3, 'CLIENTE');

INSERT INTO users (rut, email, first_name, last_name, password, phone, status, id_role)
VALUES ('17977139K', 'antonio.barraza.guzman@gmail.com', 'Antonio', 'Barraza', '{noop}Nessfit_$22', -1, 1, 1);