CREATE DATABASE `gestion_vacaciones_permisos` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;


use gestion_vacaciones_permisos
select c.IdEmpleado,
	c.nombre,
    c.primer_apellido,
    c.segundo_apellido,
    c.Departamento,
    c.puesto,
    c.activo,
    c.Observaciones,
    c.registro
    from gestion_seguiridad_monitoreo.conductores c;
    
    create database gestion_vacaciones_permisos
select *from restriccion_permisos

select *from pruebasol.empleado where empresa = 2

INSERT INTO `gestion_vacaciones_permisos`.`tipo_usuario`
(`idtipo_usuario`,
`descripcion`)
VALUES
(1,
"Secretaria/o");
SELECT `tipo_usuario`.`idtipo_usuario`,
    `tipo_usuario`.`descripcion`
FROM `gestion_vacaciones_permisos`.`tipo_usuario`;

insert into tipo_usuario(idtipo_usuario, descripcion)VALUES(1,'Secretaria/o'),(2,'Jefes');
