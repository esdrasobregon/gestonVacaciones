use gestion_vacaciones_permisos
/*con este select puedo cargar los permisos de empleados
del mismo departamento y puesto de acuerdo a sol*/
select 
	p.idPermiso,
    p.idEmpleado,
    p.id,
	p.estado,
    p.idComprobante,
    p.idTipo_permiso,
    p.fecha_inicio,
    p.fecha_fin,
	p.obserbaciones
	from pruebasol.empleado e 
	join gestion_vacaciones_permisos.permiso p 
    on (e.departamento =2 and e.puesto =5 )
    and (e.idempleado = p.idempleado)
    order by p.fecha_inicio;

select  departamento, puesto, idempleado, activo from pruebasol.empleado where departamento > 2
select *from permiso
truncate table permiso
UPDATE `gestion_vacaciones_permisos`.`permiso`
SET
`estado` = 0
WHERE `idPermiso` > 0;

truncate table auditoria_permiso

select count(*) from pruebasol.puestos pp join
	pruebasol.empleado e
	join gestion_vacaciones_permisos.permiso p
    on e.Puesto = pp.Id_Puesto and e.puesto = 5 and p.idTipo_permiso =1 and p.estado = 0
    and e.IdEmpleado = p.idEmpleado
    
select *from restriccion_permisos where vigente =1 and idtipo_restriccion_permisos = 2 and idpuesto =5
DELETE FROM `gestion_vacaciones_permisos`.`restriccion_permisos`
WHERE idrestriccion_permisos > 1;
select *from auditoria_permiso
select *from tipo_restriccion_permisos
select a.idauditoria_permiso,a.idPermiso, a.fecha_creacion, a.idEmpleado, a.id, a.anterior_estado_permiso, a.nuevo_estado_permiso,e.nombre, e.Primer_Apellido, e.Segundo_Apellido from auditoria_permiso a join pruebasol.empleado e on a.idEmpleado = e.idEmpleado and a.idPermiso = 59 and e.idEmpleado = 3087

select *from pruebasol.empleado e where concat("edras", "Obregon","ubeda") = concat(e.nombre, e.Primer_Apellido, e.segundo_Apellido)



select *from usuariogestionvacaciones