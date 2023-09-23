create table patients (
    id varchar(36) not null,
    name varchar(255) not null,
    email varchar(255) not null,
    phone varchar(14) not null,
    cpf varchar(14) not null,
    active boolean not null default true,
    address varchar(255) not null,
    number varchar(30),
    neighborhood varchar(255) not null,
    city varchar(255) not null,
    state varchar(30) not null,
    zip_code varchar(8) not null,
    complement varchar(255),
    primary key (id)
);