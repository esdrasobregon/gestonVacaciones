use gestion_vacaciones_permisos

drop table tipo_Permisos
CREATE TABLE tipo_Permisos (
    idTipo_permiso INT AUTO_INCREMENT PRIMARY KEY,
    descripcion varchar(255) not null,
    CONSTRAINT descUniq UNIQUE (descripcion)
);
drop table tipo_permisos
INSERT INTO `gestion_vacaciones_permisos`.`tipo_permisos`
(
`descripcion`)
VALUES
("VACACIONES"),
("PERMISO SIN GOCE DE SALARIO"),
("VACACIONES (medio día)"),
("CITA SIN GOCE DE SALARIO"),
("CITA MEDICA"),
("CITA MIGRACION"),
("CITA JUZGADO"),
("CITA LICENCIA"),
("CAMBIO DIA LIBRE");

select idTipo_permiso, descripcion
 from tipo_permisos


truncate table comprobante_solicitud
CREATE TABLE comprobante_solicitud ( 
	idComprobante INTEGER UNSIGNED NOT NULL AUTO_INCREMENT primary key, 
	titulo VARCHAR(45) NOT NULL, 
	imagen LONGBLOB NOT NULL,
	CONSTRAINT tituloUnq UNIQUE (titulo)
);

truncate table permiso
create table permiso (
	idPermiso int auto_increment primary key,
	fecha_creacion date not null,
	fecha_inicio date not null,
    fecha_fin date not null,
    idEmpleado int not null,
    id int not null,
    idpuesto int not null,
    estado int not null,
	idComprobante int default null, 
    idTipo_permiso int,
    trabajar int default -1,
    dia_libre varchar(15) not null,
    obserbaciones varchar(50),
    FOREIGN KEY (idTipo_permiso) REFERENCES tipo_permisos(idTipo_permiso)
);
ALTER TABLE permiso
modify COLUMN idpuesto int not null;

create table auditoria_permiso (
	idauditoria_permiso int auto_increment primary key,
    idPermiso int not null,
	fecha_creacion date not null,
    idEmpleado int not null,
    id int not null,
    anterior_estado_permiso int not null,
    nuevo_estado_permiso int not null,
    FOREIGN KEY (idPermiso) REFERENCES permiso(idPermiso)
);
truncate table auditoria_permiso
select *from auditoria_permiso
select * from permiso where idempleado = 2568

UPDATE `gestion_vacaciones_permisos`.`permiso`
SET
`fecha_inicio` = ?,
`fecha_fin` = ?,
`estado` = ?,
WHERE `idPermiso` = ?;

create table tipo_restriccion_permisos(
	idtipo_restriccion_permisos int auto_increment primary key,
	decripcion varchar(15)
)
insert into restricciones_permisos
(decripcion)
VALUES
("POR FECHAS"),
("POR CANTIDAD");
SELECt *from restriccion_permisos
create table restriccion_permisos (
	idrestriccion_permisos int auto_increment primary key,
    idtipo_restriccion_permisos int not null,
	fecha_creacion date not null,
    iddepartamento int,
    idpuesto int,
    fecha_inicio date,
    fecha_fin date,
    idEmpleado int not null,
    id int not null,
    vigente tinyint not null,
    cantidad_maxima int,
    FOREIGN KEY (idtipo_restriccion_permisos) REFERENCES tipo_restriccion_permisos(idtipo_restriccion_permisos)
);
truncate table restriccion_permisos
select *from restriccion_permisos
INSERT INTO `gestion_vacaciones_permisos`.`restriccion_permisos`
(
`idtipo_restriccion_permisos`,
`fecha_creacion`,
`iddepartamento`,
`idpuesto`,
`fecha_inicio`,
`fecha_fin`,
`idEmpleado`,
`id`,
`vigente`,
`cantidad_maxima`)
VALUES
(
1,
curdate(),
15,
16,
curdate(),
curdate(),
3087,
15,
1,
2);



DELIMITER ;

select *from tipo_restriccion_permisos

SELECT schema_name FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = 'gestion_vacaciones_permisos'
CREATE  DATABASE if not exists `gestion_vacaciones_permisos` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;