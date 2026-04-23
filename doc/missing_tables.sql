-- 缺失的表结构 SQL

-- 无人船信息表
create table sys_ship
(
    ship_id        bigint auto_increment comment '无人船ID'
        primary key,
    ship_code      varchar(50)             not null comment '船编号',
    ship_name      varchar(100)            not null comment '船名称',
    ship_type      char        default '1' null comment '船类型（1巡逻船 2救援船 3科研船 4其他）',
    airport_id     bigint                  null comment '所属机场ID',
    longitude      decimal(10, 6)          null comment '经度',
    latitude       decimal(10, 6)          null comment '纬度',
    speed          decimal(10, 2)          null comment '速度',
    course         decimal(10, 2)          null comment '航向',
    status         char        default '0' null comment '状态（0正常 1停用 2维护）',
    online_status  char        default '0' null comment '在线状态（0在线 1离线）',
    remark         varchar(500)            null comment '备注',
    create_by      varchar(64) default ''  null comment '创建者',
    create_time    datetime                null comment '创建时间',
    update_by      varchar(64) default ''  null comment '更新者',
    update_time    datetime                null comment '更新时间',
    del_flag       char        default '0' null comment '删除标志（0存在 2删除）',
    constraint uk_ship_code
        unique (ship_code),
    constraint fk_ship_airport
        foreign key (airport_id) references uav_airport (airport_id)
) comment '无人船信息表';

-- 仿真场景信息表
create table sys_sim_scene
(
    scene_id          bigint auto_increment comment '场景ID'
        primary key,
    scene_name        varchar(100)            not null comment '场景名称',
    scene_type        char        default '1' null comment '场景类型（1近海 2港口 3远海）',
    description       varchar(500)            null comment '场景描述',
    map_url           varchar(255)            null comment '地图URL',
    weather_conditions varchar(100)            null comment '天气条件',
    sea_conditions    varchar(100)            null comment '海况',
    remark            varchar(500)            null comment '备注',
    create_by         varchar(64) default ''  null comment '创建者',
    create_time       datetime                null comment '创建时间',
    update_by         varchar(64) default ''  null comment '更新者',
    update_time       datetime                null comment '更新时间',
    del_flag          char        default '0' null comment '删除标志（0存在 2删除）',
    constraint uk_scene_name
        unique (scene_name)
) comment '仿真场景信息表';

-- 任务信息表
create table sys_task
(
    task_id     bigint auto_increment comment '任务ID'
        primary key,
    task_name   varchar(100)            not null comment '任务名称',
    task_type   char        default '1' null comment '任务类型（1巡逻 2运输 3救援 4科研）',
    scene_id    bigint                  null comment '场景ID',
    ship_id     bigint                  null comment '无人船ID',
    start_time  datetime                null comment '开始时间',
    end_time    datetime                null comment '结束时间',
    status      char        default '0' null comment '状态（0待执行 1执行中 2已完成 3已取消）',
    progress    int         default 0   null comment '进度（0-100）',
    remark      varchar(500)            null comment '备注',
    create_by   varchar(64) default ''  null comment '创建者',
    create_time datetime                null comment '创建时间',
    update_by   varchar(64) default ''  null comment '更新者',
    update_time datetime                null comment '更新时间',
    del_flag    char        default '0' null comment '删除标志（0存在 2删除）',
    constraint fk_task_scene
        foreign key (scene_id) references sys_sim_scene (scene_id),
    constraint fk_task_ship
        foreign key (ship_id) references sys_ship (ship_id)
) comment '任务信息表';

-- 仿真记录信息表
create table sys_simulation
(
    simulation_id bigint auto_increment comment '仿真ID'
        primary key,
    task_id       bigint                  null comment '任务ID',
    start_time    datetime                null comment '开始时间',
    end_time      datetime                null comment '结束时间',
    status        char        default '0' null comment '状态（0待开始 1进行中 2已完成 3已暂停）',
    result        varchar(500)            null comment '仿真结果',
    remark        varchar(500)            null comment '备注',
    create_by     varchar(64) default ''  null comment '创建者',
    create_time   datetime                null comment '创建时间',
    update_by     varchar(64) default ''  null comment '更新者',
    update_time   datetime                null comment '更新时间',
    del_flag      char        default '0' null comment '删除标志（0存在 2删除）',
    constraint fk_simulation_task
        foreign key (task_id) references sys_task (task_id)
) comment '仿真记录信息表';
