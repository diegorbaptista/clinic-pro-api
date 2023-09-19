create table doctors (
    id varchar(36) not null,
    name varchar(255) not null,
    email varchar(255) not null unique,
    registration_code varchar(8) not null,
    specialty varchar(30) not null,
    address varchar(255) not null,
    number varchar(30),
    neighborhood varchar(255) not null,
    city varchar(255) not null,
    state varchar(30) not null,
    zip_code varchar(8) not null,
    complement varchar(255),
    primary key (id)
)