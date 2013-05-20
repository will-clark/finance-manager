    create table FM_ACCOUNT (
        id int8 not null,
        active boolean not null,
        balance numeric(19, 2),
        created timestamp,
        modified timestamp,
        name varchar(255) not null,
        notes varchar(255),
        number varchar(255),
        user_id int8 not null,
        primary key (id)
    );

    create table FM_CATEGORY (
        id int8 not null,
        active boolean not null,
        created timestamp,
        modified timestamp,
        name varchar(255) not null,
        user_id int8 not null,
        primary key (id)
    );

    create table FM_TAG (
        id int8 not null,
        active boolean not null,
        created timestamp,
        modified timestamp,
        name varchar(255) not null,
        user_id int8 not null,
        primary key (id)
    );

    create table FM_TRANSACTION (
        id int8 not null,
        active boolean not null,
        amount numeric(19, 2) not null,
        date timestamp not null,
        notes varchar(255),
        type varchar(255) not null,
        account_id int8 not null,
        category_id int8,
        user_id int8 not null,
        primary key (id)
    );

    create table FM_TRANSACTION_FM_TAG (
        FM_TRANSACTION_id int8 not null,
        tags_id int8 not null
    );

    create table FM_USER (
        id int8 not null,
        active boolean not null,
        created timestamp,
        email varchar(255) not null unique,
        modified timestamp,
        password varchar(255) not null,
        resetExpiration timestamp,
        resetToken varchar(255),
        salt varchar(255) not null,
        username varchar(255) not null unique,
        primary key (id)
    );

    alter table FM_ACCOUNT 
        add constraint FKD5D90E956FE239B1 
        foreign key (user_id) 
        references FM_USER;

    alter table FM_CATEGORY 
        add constraint FK67ADC5766FE239B1 
        foreign key (user_id) 
        references FM_USER;

    alter table FM_TAG 
        add constraint FK7BDCC0026FE239B1 
        foreign key (user_id) 
        references FM_USER;

    alter table FM_TRANSACTION 
        add constraint FK5AEEDB462712A363 
        foreign key (account_id) 
        references FM_ACCOUNT;

    alter table FM_TRANSACTION 
        add constraint FK5AEEDB4665AA0551 
        foreign key (category_id) 
        references FM_CATEGORY;

    alter table FM_TRANSACTION 
        add constraint FK5AEEDB466FE239B1 
        foreign key (user_id) 
        references FM_USER;

    alter table FM_TRANSACTION_FM_TAG 
        add constraint FK4393C71BC064A3E4 
        foreign key (tags_id) 
        references FM_TAG;

    alter table FM_TRANSACTION_FM_TAG 
        add constraint FK4393C71B3A66E7BB 
        foreign key (FM_TRANSACTION_id) 
        references FM_TRANSACTION;

    create sequence saccount;
    create sequence scategory;
    create sequence stag;
    create sequence stransaction;
    create sequence suser;