CREATE IF NOT EXISTS DATABASE choripan_solutions;
USE choripan_solutions;

CREATE TABLE IF NOT EXISTS Usuarios (
    rut INT NOT NULL,
    nombre1 VARCHAR(40),
    nombre2 VARCHAR(200),
    apellido1 VARCHAR(40),
    apellido2 VARCHAR(40),
    rol INT,
    activo BOOLEAN DEFAULT TRUE,
    numero VARCHAR(15),
    correo VARCHAR(30),
    password_hash VARCHAR(255) NOT NULL,
    PRIMARY KEY (rut)
);


CREATE TABLE IF NOT EXISTS Log (
    id_log INT NOT NULL AUTO_INCREMENT,
    detalle TEXT,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    rut_usuario INT,
    PRIMARY KEY (id_log),
    FOREIGN KEY (rut_usuario)
        REFERENCES Usuarios (rut)
);


CREATE TABLE IF NOT EXISTS Participante (
    codigo VARCHAR(10),
    nombre1 VARCHAR(40),
    nombre2 VARCHAR(200),
    apellido1 VARCHAR(40),
    apellido2 VARCHAR(40),
    correo VARCHAR(30),
    numero VARCHAR(15),
    estado BOOLEAN NOT NULL,
    rut_usuario INT,
    grupo int, /* 0 = control, 1 = caso*/
    fecha_inclusion DATE,
    /* datos sociodemograficos */
    edad int,
    nacionalidad VARCHAR(20),
    direccion VARCHAR(100),
    comuna VARCHAR(50),
    ciudad VARCHAR(50),
    sexo BOOLEAN, /* 0 = femenino, 1 = masculino */
    zona BOOLEAN, /* 0 = urbana, 1 = rural */
    vive_hace_5_annos BOOLEAN, /* 0 = no, 1 = si */
    nivel_educacional int, /* 0 = básico, 1 = medio, 2 = universitario */
    ocupacion_actual VARCHAR(50),
    prevision_salud int, /* 0 = ninguna, 1 = fonasa, 2 = isapre, 3 = capredema/Dipreca, 4 = otra */
    otra_prevision VARCHAR(50),

    /*antecedentes clinicos */
    diagnostico_histologico_adenocarcinoma_gastrico BOOLEAN, /* 0 = no, 1 = si - SOLO CASOS*/
    fecha_diagnostico DATE, /*solo casos*/
    ant_fam_cancer_gastrico BOOLEAN, /* 0 = no, 1 = si */
    ant_fam_cancer_otro BOOLEAN, /* 0 = no, 1 = si */
    cancer_otro VARCHAR(50),/* detalle tipo de cancer */
    otras_enfermedades VARCHAR(100 ),
    uso_cronico_medicametos_gastrolesivos BOOLEAN, /* 0 = no, 1 = si */
    medicamento_gastrolesivo VARCHAR(100),
    cirugia_gastrica_previa BOOLEAN, /* 0 = no, 1 = si */
   
    /*Variables antropomorficas */

    peso FLOAT,
    estatura FLOAT,
    imc FLOAT,

    /*Tabaquismo y alcohol */

    nunca_fumo int, /* 0 = no, 1 = si */
    ex_fumador int, /* 0 = no, 1 = si */
    ex_annos_sin_fumar int,
    fuma_actualmente int, /* 0 = no, 1 = si */
    edad_inicio_fuma int,
    promedio_fuma_diario int,
    años_fumador int,

    nunca_bebio int, /* 0 = no, 1 = si */
    ex_bebedor int, /* 0 = no, 1 = si */
    ex_annos_sin_beber int,
    bebe_actualmente int, /* 0 = no, 1 = si */
    edad_inicio_bebe int,
    frecuencia int, /* 0 = ocasional, 2 = regular,3 = frecuente */
    cantidad_x_ocasion int, /*desde el 0: 1-2, 3-4, >=5*/
    annos_consumo int,

    /*factores dieta */

    carnes_procesadas int, /* frecuencia consumo semanal */
    alimentos_salados int, /*0=no, 1=si*/
    frutas_verduras int, /* frecuencia diario */
    frituras int, /* >=3 veces x semana.  0 = no, 1 = si */
    bebida_caliente int, /* 0 = nunca, 1 = 1-2 sem, 2 = >=3 sem */
    
    /*exposiciones*/

    pesticidas int, /* 0 = no, 1 = si */
    otros_chemicos int, /* 0 = no, 1 = si */
    tipo_chemicos VARCHAR(100), /* detalle */
    humo_lenna int, /* 0 = no, 1 = estacional, 2 = diario */
    agua VARCHAR(20), 
    tratamiento_agua int, /* 0 = ninguno, 1 = hervir, 2= filtro, 3 = cloro */

    /*datos helicobacter pylori*/
    prueba_hel INT, /* 0 = aliento, 1 = antigeno, 2= endoscopia/biopsia */
    resultado_hel INT, /* 0 = negativo, 1 = positivo */
    tiempo_test int;

    /*muestras biologicas y geneticas*/

    fecha_toma_sangre DATE,

    TLR9_rs5743836 int, /* 0= tt, 1= tc, 2= cc */
    TLR9_rs187084 int, /* 0= tt, 1= tc, 2= cc */
    miR-146a_rs2910164 int, /* 0= gg, 1= gc, 2= cc */
    miR-196a2_rs11614913 int, /* 0= cc, 1= ct, 2= tt */
    MTHFR_rs1801133 int, /* 0= cc, 1= ct, 2= tt */
    DNMT3B rs1569686 int, /* 0= gg, 1= gt, 2= tt */

    /*hispopatologia (solo casos)*/

    tipo int, /* 0 = intestinal, 1 = difuso, 2 = mixto, 3 = otro */
    otro VARCHAR(50), /* detalle otro tipo */
    tumor_ubicacion int, /* 0 = cardias, 1 = cuerpo, 2 = antro, 3 = difuso*/
    estadio_clinico VARCHAR(30),

    PRIMARY KEY (codigo),
    FOREIGN KEY (rut_usuario)
        REFERENCES Usuarios (rut)
);