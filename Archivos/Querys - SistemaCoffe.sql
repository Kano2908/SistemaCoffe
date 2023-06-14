-- Query para Costo total de la requisicion
SELECT SUM(cantidad * precioU) AS costoT FROM materiales;

-- Query para Costo total del horario del trabajador
SELECT SUM(horasT * pagoH) AS costoT FROM horario;

-- Quey para calcular la taza gif - subquery
SELECT ROUND((presupuesto / (SELECT SUM(horasT) AS horasTotal FROM horario)), 2) AS CostoT FROM tasaGIF;

-- Query para mostrar las cantidades del panel de MOD - Hoja de Costos
SELECT CURDATE() AS dia, SUM(horasT) AS horasT, pagoH, SUM(horasT * pagoH) AS costoT FROM horario GROUP BY pagoH; 

-- Query para mostrar las cantidades del panel de Materiales - Hoja de Costos
SELECT CURDATE() as fecha, (SELECT numeroR FROM requisicion) AS numeroR, SUM(cantidad * precioU) AS costoT FROM materiales;

-- Query para la informacion del panel GIF de la hoja de costos
SELECT CURDATE() as fecha,
    (SELECT SUM(horasT) FROM horario) AS horasT,
    (SELECT ROUND((presupuesto / horasMOD), 2) AS costoT FROM tasaGIF) AS tasaGIF,
    (SELECT SUM(HORASt) FROM horario) * (SELECT ROUND((presupuesto / horasMOD), 2) AS costoT FROM tasaGIF) AS costoTGIF
FROM tasaGIF;

-- Query para la informacion del panel Resumen de los costos totales de MOD, Materiales y TasaGIF, Costo total y Costo unitario de la hoja de costos
UPDATE hojaC SET manoOD = (SELECT SUM(horasT * pagoH) AS costoT FROM horario), 
materiales = (SELECT SUM(cantidad * precioU) AS costoT FROM materiales), 
gif = (SELECT ROUND((presupuesto / horasMOD), 2) AS costoT FROM tasaGIF) WHERE id = 1;

UPDATE hojaC SET costoT = (manoOD + materiales + gif) WHERE id = 1;

UPDATE hojac SET costoU = costoT / (SELECT cantidad FROM cliente) WHERE id = 1;