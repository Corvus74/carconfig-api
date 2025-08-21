create table if not exists car_color
(
    id integer not null
        primary key,
    created_at   timestamp(6) with time zone,
    created_by   varchar(20),
    delete_flag  varchar(1),
    description  varchar(400),
    modified_at  timestamp(6) with time zone,
    modified_by  varchar(20),
    name         varchar(20),
    order_number varchar(10),
    price        integer,
    product_id   varchar(20)
);

create table if not exists car_engine
(
    car_engine_id integer not null
        primary key,
    created_at    timestamp(6) with time zone,
    created_by    varchar(20),
    delete_flag   varchar(1),
    description   varchar(400),
    modified_at   timestamp(6) with time zone,
    modified_by   varchar(20),
    name          varchar(20),
    order_number  varchar(10),
    price         integer,
    product_id    varchar(20)
);

create table if not exists car_rims
(
    car_rims_id  integer not null
        primary key,
    created_at   timestamp(6) with time zone,
    created_by   varchar(20),
    delete_flag  varchar(1),
    description  varchar(400),
    modified_at  timestamp(6) with time zone,
    modified_by  varchar(20),
    name         varchar(20),
    order_number varchar(10),
    price        integer,
    product_id   varchar(20)
);

create table if not exists orders_user
(
    user_id     integer not null
        primary key,
    created_at  timestamp(6) with time zone,
    created_by  varchar(20),
    delete_flag varchar(1),
    is_valid    varchar(1),
    modified_at timestamp(6) with time zone,
    modified_by varchar(20),
    user_email  varchar(20),
    user_name   varchar(20)
);

create table if not exists orderstatus
(
    order_status_id integer not null
        primary key,
    created_at      timestamp(6) with time zone,
    created_by      varchar(20),
    delete_flag     varchar(1),
    delivery_date   date,
    modified_at     timestamp(6) with time zone,
    modified_by     varchar(20),
    order_status    varchar(10),
    shipping_date   date
);

create table if not exists car_color_order
(
    car_color_order_id           integer not null
        primary key,
    created_at                   timestamp(6) with time zone,
    created_by                   varchar(20),
    delete_flag                  varchar(1),
    modified_at                  timestamp(6) with time zone,
    modified_by                  varchar(20),
    car_color_car_color_id       integer
        constraint uk89cu3ad449srkh2la7nf0leyr
            unique
        constraint fkdi15fkby18f7qgnm0jcta8rls
            references car_color,
    order_status_order_status_id integer
        constraint ukarpgf9to7qt2ffjh53ea3sb4e
            unique
        constraint fk8qqmwkwe5ve7o3jpo4rc9ckdw
            references orderstatus
);

create table if not exists car_engine_order
(
    car_engine_order_id          integer not null
        primary key,
    created_at                   timestamp(6) with time zone,
    created_by                   varchar(20),
    delete_flag                  varchar(1),
    modified_at                  timestamp(6) with time zone,
    modified_by                  varchar(20),
    car_engine_car_engine_id     integer
        constraint uks6009pji4knec98hbulspbsx3
            unique
        constraint fk8bvlct5favyqo5f0l75ekiso8
            references car_engine,
    order_status_order_status_id integer
        constraint uk6kdc0gwfjhueh3piduvqrffbu
            unique
        constraint fkjp6evvdd0awdvtvkhqofnx4x1
            references orderstatus
);

create table if not exists car_rims_order
(
    car_rims_order_id            integer not null
        primary key,
    created_at                   timestamp(6) with time zone,
    created_by                   varchar(20),
    delete_flag                  varchar(1),
    modified_at                  timestamp(6) with time zone,
    modified_by                  varchar(20),
    car_rim_car_rims_id          integer
        constraint uk8yd8ehlumy4x0m9y1binm6p5x
            unique
        constraint fk7ym0iajivb6url6n24cswc3w
            references car_rims,
    order_status_order_status_id integer
        constraint ukaxw2yq4ad1h923y5u0qqe7ke6
            unique
        constraint fk2i7f42rv2tvg393gdmmf0if3v
            references orderstatus
);

create table if not exists orders
(
    order_id                           integer not null
        primary key,
    created_at                         timestamp(6) with time zone,
    created_by                         varchar(20),
    delete_flag                        varchar(1),
    description                        varchar(400),
    modified_at                        timestamp(6) with time zone,
    modified_by                        varchar(20),
    price                              integer,
    car_color_order_car_color_order_id integer
        constraint ukrnyrg9qte3rb6491ie4ca6rr1
            unique
        constraint fkh2ewrc9xme6205hoy8nn74bhr
            references car_color_order,
    car_engine_id_car_engine_order_id  integer
        constraint ukn8tcbk9c94aaj4tcf0hr1xb6r
            unique
        constraint fkptm8lhndtwmr6kjcwq6wxblqe
            references car_engine_order,
    car_rims_order_car_rims_order_id   integer
        constraint uksl234crf0l6l6xtoautl2gsx
            unique
        constraint fkj7j99v4un737vwp1ss170puwf
            references car_rims_order,
    order_status_order_status_id       integer
        constraint uk8uboqxnc5vtnlv8w85dvbgvk1
            unique
        constraint fkjxu6vd8a4ip3b7m74kc4gbfba
            references orderstatus,
    user_id_user_id                    integer
        constraint uk1mhljyotgth4lumml8cf18xa3
            unique
        constraint fkcif3hg5w4gmgmbaarjg3071x0
            references orders_user
);

create table if not exists special_equipment
(
    special_equipment_id integer not null
        primary key,
    created_at           timestamp(6) with time zone,
    created_by           varchar(20),
    delete_flag          varchar(1),
    description          varchar(400),
    modified_at          timestamp(6) with time zone,
    modified_by          varchar(20),
    name                 varchar(10),
    order_number         varchar(20),
    price                integer,
    product_id           varchar(20)
);

create table if not exists special_equipment_order
(
    special_equipment_id                   integer not null
        primary key,
    created_at                             timestamp(6) with time zone,
    created_by                             varchar(20),
    delete_flag                            varchar(1),
    modified_at                            timestamp(6) with time zone,
    modified_by                            varchar(20),
    order_group                            varchar(20),
    order_status_order_status_id           integer
        constraint uka39x1j8hofsv8qbxwfolnt48f
            unique
        constraint fkrr8et7p0fh38c4fbwdmcu0wwe
            references orderstatus,
    special_equipment_special_equipment_id integer
        constraint uk1p6ab9srui3874ufgov6bbxx9
            unique
        constraint fkprwgrvajo0hsmyno951ruwxx3
            references special_equipment
);

create table if not exists orders_special_equipment_orders
(
    order_order_id                                integer not null
        constraint fko6ci9sohqa3yw1cyya2l35eb9
            references orders,
    special_equipment_orders_special_equipment_id integer not null
        constraint ukleld693xlde6bbfmk7c6fgcnn
            unique
        constraint fkmf0bymufi8x0icy8s4iecih21
            references special_equipment_order
);

