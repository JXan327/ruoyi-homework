-- 无人船仿真菜单初始化 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values ('无人船仿真', '0', '90', 'usv', 'Layout', 1, 0, 'M', '0', '0', '', 'guide', 'admin', sysdate(), '', null, '无人船仿真目录');

select @usvParentId := last_insert_id();

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values ('机场管理', @usvParentId, '1', 'airport', 'system/airport/index', 1, 0, 'C', '0', '0', 'system:airport:list', 'international', 'admin', sysdate(), '', null, '机场管理菜单');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values ('无人船管理', @usvParentId, '2', 'boat', 'system/boat/index', 1, 0, 'C', '0', '0', 'system:boat:list', 'ship', 'admin', sysdate(), '', null, '无人船管理菜单');

select @boatMenuId := last_insert_id();

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values ('无人船查询', @boatMenuId, '1', '#', '', 1, 0, 'F', '0', '0', 'system:boat:query', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values ('无人船新增', @boatMenuId, '2', '#', '', 1, 0, 'F', '0', '0', 'system:boat:add', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values ('无人船修改', @boatMenuId, '3', '#', '', 1, 0, 'F', '0', '0', 'system:boat:edit', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values ('无人船删除', @boatMenuId, '4', '#', '', 1, 0, 'F', '0', '0', 'system:boat:remove', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values ('无人船导出', @boatMenuId, '5', '#', '', 1, 0, 'F', '0', '0', 'system:boat:export', '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values ('场景管理', @usvParentId, '3', 'scene', 'system/scene/index', 1, 0, 'C', '0', '0', 'system:scene:list', 'map', 'admin', sysdate(), '', null, '场景管理菜单');

select @sceneMenuId := last_insert_id();

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values ('场景查询', @sceneMenuId, '1', '#', '', 1, 0, 'F', '0', '0', 'system:scene:query', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values ('场景新增', @sceneMenuId, '2', '#', '', 1, 0, 'F', '0', '0', 'system:scene:add', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values ('场景修改', @sceneMenuId, '3', '#', '', 1, 0, 'F', '0', '0', 'system:scene:edit', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values ('场景删除', @sceneMenuId, '4', '#', '', 1, 0, 'F', '0', '0', 'system:scene:remove', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values ('场景导出', @sceneMenuId, '5', '#', '', 1, 0, 'F', '0', '0', 'system:scene:export', '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values ('任务管理', @usvParentId, '4', 'task', 'system/task/index', 1, 0, 'C', '0', '0', 'system:task:list', 'job', 'admin', sysdate(), '', null, '任务管理菜单');

select @taskMenuId := last_insert_id();

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values ('任务查询', @taskMenuId, '1', '#', '', 1, 0, 'F', '0', '0', 'system:task:query', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values ('任务新增', @taskMenuId, '2', '#', '', 1, 0, 'F', '0', '0', 'system:task:add', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values ('任务修改', @taskMenuId, '3', '#', '', 1, 0, 'F', '0', '0', 'system:task:edit', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values ('任务删除', @taskMenuId, '4', '#', '', 1, 0, 'F', '0', '0', 'system:task:remove', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values ('任务导出', @taskMenuId, '5', '#', '', 1, 0, 'F', '0', '0', 'system:task:export', '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values ('仿真管理', @usvParentId, '5', 'simulation', 'system/simulation/index', 1, 0, 'C', '0', '0', 'system:simulation:list', 'dashboard', 'admin', sysdate(), '', null, '仿真管理菜单');

select @simulationMenuId := last_insert_id();

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values ('仿真启动', @simulationMenuId, '1', '#', '', 1, 0, 'F', '0', '0', 'system:simulation:start', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values ('仿真暂停', @simulationMenuId, '2', '#', '', 1, 0, 'F', '0', '0', 'system:simulation:pause', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values ('仿真停止', @simulationMenuId, '3', '#', '', 1, 0, 'F', '0', '0', 'system:simulation:stop', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values ('仿真状态', @simulationMenuId, '4', '#', '', 1, 0, 'F', '0', '0', 'system:simulation:status', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values ('仿真分析', @simulationMenuId, '5', '#', '', 1, 0, 'F', '0', '0', 'system:simulation:analyze', '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values ('数据分析', @usvParentId, '6', 'analysis', 'system/analysis/index', 1, 0, 'C', '0', '0', 'system:analysis:list', 'chart', 'admin', sysdate(), '', null, '数据分析菜单');
