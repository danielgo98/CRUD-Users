create database proyecto_usuarios;
use proyecto_usuarios;

create table usuarios(
id int not null auto_increment,
username varchar(12) default null,
password varchar(60) default null,
email varchar(45) default null,
primary key (id)
);