USE choripan_solutions;

CREATE TABLE Usuarios (
    rut INT NOT NULL,
    nombre1 VARCHAR(40),
    nombre2 VARCHAR(200),
    apellido1 VARCHAR(40),
    apellido2 VARCHAR(40),
    rol INT,
    numero VARCHAR(15),
    correo VARCHAR(30),
    password_hash VARCHAR(255) NOT NULL,
    PRIMARY KEY (rut)
);


CREATE TABLE Log (
    id_log INT NOT NULL AUTO_INCREMENT,
    detalle TEXT,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    rut_usuario INT,
    PRIMARY KEY (id_log),
    FOREIGN KEY (rut_usuario)
        REFERENCES Usuarios (rut)
);


CREATE TABLE Participante (
    codigo VARCHAR(10),
    nombre1 VARCHAR(40),
    nombre2 VARCHAR(200),
    apellido1 VARCHAR(40),
    apellido2 VARCHAR(40),
    correo VARCHAR(30),
    numero VARCHAR(15),
    estado BOOLEAN NOT NULL,
    rut_usuario INT,
    PRIMARY KEY (codigo),
    FOREIGN KEY (rut_usuario)
        REFERENCES Usuarios (rut)
);