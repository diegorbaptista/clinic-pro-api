create table appointments (
    id varchar(36) not null,
    patient_id varchar(36) not null,
    doctor_id varchar(36) not null,
    date timestamp not null,

    primary key (id),
    constraint fk_appointments_patient_id foreign key (patient_id) references patients (id),
    constraint fk_appointments_doctor_id foreign key (doctor_id) references doctors (id)
 );