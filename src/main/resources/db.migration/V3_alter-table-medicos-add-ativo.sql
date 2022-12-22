alter table medicos add ativo tinyint;
SET SQL_SAFE_UPDATES = 0;
UPDATE medicos SET ativo = 1;