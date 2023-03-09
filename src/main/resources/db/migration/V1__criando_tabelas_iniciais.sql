create table COLLECT_ADDRESS(

    COLECT_ADDRESS_ID serial not null,

    primary key(COLECT_ADDRESS_ID)

);

create table COLLECT(

    COLLECT_ID serial not null,
    COLECT_ADDRESS_ID bigint not null,
    CUSTOMER_CODE varchar(40) not null,
    START_TIME  TIMESTAMP not null,
    END_TIME TIMESTAMP not null,

    primary key(COLLECT_ID),

    CONSTRAINT FK_COLECT_ADDRESS FOREIGN KEY (COLECT_ADDRESS_ID) REFERENCES COLLECT_ADDRESS (COLECT_ADDRESS_ID)

);