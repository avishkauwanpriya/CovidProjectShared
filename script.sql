create table globalcoviddata
(
    confirmedCases int         not null,
    recoveredCases int         not null,
    deaths         int         not null,
    Date           varchar(20) not null
        primary key
);

create table hospital
(
    hospitalId         varchar(50) not null
        primary key,
    hospitalName       varchar(50) not null,
    city               varchar(50) not null,
    district           varchar(50) not null,
    capacity           int         not null,
    director           varchar(50) not null,
    directorContactNo  varchar(50) not null,
    hospitalContactNo1 varchar(50) not null,
    hospitalContactNo2 varchar(50) not null,
    hospitalFaxNo      varchar(50) not null,
    hospitalEMail      varchar(50) not null
);

create table lastupdated
(
    confirmedCases int         not null,
    recoveredCases int         not null,
    deaths         int         not null,
    date           varchar(50) not null
);

create table quarantinecenter
(
    Id               varchar(50) not null
        primary key,
    centerName       varchar(50) not null,
    city             varchar(50) not null,
    district         varchar(50) not null,
    head             varchar(50) not null,
    headContactNo    varchar(50) not null,
    centerContactNo1 varchar(50) not null,
    centerContactNo2 varchar(50) not null,
    capacity         int         not null
);

create table user
(
    name          varchar(50) not null,
    contactNumber varchar(50) null,
    email         varchar(50) not null,
    username      varchar(50) not null
        primary key,
    password      varchar(50) not null,
    role          varchar(50) not null
);

create table user_hospital
(
    userName   varchar(50) not null,
    hospitalId varchar(50) not null,
    primary key (userName, hospitalId)
);

create table user_qrcenter
(
    userName   varchar(50) not null,
    qrCenterId varchar(50) not null,
    primary key (userName, qrCenterId)
);


