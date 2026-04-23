-- 测试数据 SQL

-- 无人机场信息测试数据
insert into uav_airport (airport_id, airport_code, airport_name, airport_type, province, city, district, address, longitude, latitude, altitude, cover_radius, max_uav_count, manager_name, manager_phone, status, online_status, create_by, create_time, update_by, update_time, remark, del_flag)
values
(1, 'AP001', '青岛无人机场', '1', '山东省', '青岛市', '市南区', '青岛市市南区海边路1号', 120.38, 36.07, 10.5, 5000, 50, '张三', '13800138001', '0', '0', 'admin', sysdate(), '', null, '青岛海域无人船基地', '0'),
(2, 'AP002', '上海无人机场', '1', '上海市', '上海市', '浦东新区', '上海市浦东新区洋山港', 121.47, 31.23, 5.2, 8000, 80, '李四', '13800138002', '0', '0', 'admin', sysdate(), '', null, '上海洋山港无人船基地', '0'),
(3, 'AP003', '深圳无人机场', '1', '广东省', '深圳市', '南山区', '深圳市南山区蛇口港', 114.05, 22.55, 8.7, 6000, 60, '王五', '13800138003', '0', '0', 'admin', sysdate(), '', null, '深圳蛇口无人船基地', '0');

-- 无人船信息测试数据
insert into sys_ship (ship_id, ship_code, ship_name, ship_type, airport_id, longitude, latitude, speed, course, status, online_status, create_by, create_time, update_by, update_time, remark, del_flag)
values
(1, 'SHIP001', '巡逻一号', '1', 1, 120.39, 36.08, 15.5, 90, '0', '0', 'admin', sysdate(), '', null, '青岛基地巡逻船', '0'),
(2, 'SHIP002', '救援一号', '2', 1, 120.40, 36.09, 20.0, 180, '0', '0', 'admin', sysdate(), '', null, '青岛基地救援船', '0'),
(3, 'SHIP003', '科研一号', '3', 2, 121.48, 31.24, 12.0, 45, '0', '1', 'admin', sysdate(), '', null, '上海基地科研船', '0'),
(4, 'SHIP004', '巡逻二号', '1', 2, 121.49, 31.25, 16.0, 270, '0', '0', 'admin', sysdate(), '', null, '上海基地巡逻船', '0'),
(5, 'SHIP005', '救援二号', '2', 3, 114.06, 22.56, 21.0, 135, '0', '0', 'admin', sysdate(), '', null, '深圳基地救援船', '0');

-- 仿真场景信息测试数据
insert into sys_sim_scene (scene_id, scene_name, scene_type, description, map_url, weather_conditions, sea_conditions, create_by, create_time, update_by, update_time, remark, del_flag)
values
(1, '青岛近海场景', '1', '青岛近海巡逻场景', 'map/qd_coastal.json', '晴天', '平静', 'admin', sysdate(), '', null, '青岛近海常规巡逻场景', '0'),
(2, '上海洋山港场景', '2', '上海洋山港物流场景', 'map/sh_yangshan.json', '多云', '轻度海浪', 'admin', sysdate(), '', null, '洋山港物流运输场景', '0'),
(3, '深圳蛇口场景', '3', '深圳蛇口救援场景', 'map/sz_shekou.json', '雨天', '中度海浪', 'admin', sysdate(), '', null, '蛇口海域救援演练场景', '0');

-- 任务信息测试数据
insert into sys_task (task_id, task_name, task_type, scene_id, ship_id, start_time, end_time, status, progress, create_by, create_time, update_by, update_time, remark, del_flag)
values
(1, '青岛近海巡逻任务', '1', 1, 1, sysdate(), sysdate() + interval 2 hour, '1', 50, 'admin', sysdate(), '', null, '常规巡逻任务', '0'),
(2, '上海洋山港运输任务', '2', 2, 3, sysdate(), sysdate() + interval 4 hour, '0', 0, 'admin', sysdate(), '', null, '物流运输任务', '0'),
(3, '深圳蛇口救援演练', '3', 3, 5, sysdate(), sysdate() + interval 1 hour, '2', 100, 'admin', sysdate(), '', null, '救援演练任务', '0');

-- 仿真记录信息测试数据
insert into sys_simulation (simulation_id, task_id, start_time, end_time, status, result, create_by, create_time, update_by, update_time, remark, del_flag)
values
(1, 1, sysdate() - interval 1 hour, null, '1', null, 'admin', sysdate() - interval 1 hour, '', null, '青岛近海巡逻仿真', '0'),
(2, 3, sysdate() - interval 2 hour, sysdate() - interval 1 hour, '2', '救援演练成功完成', 'admin', sysdate() - interval 2 hour, '', null, '深圳蛇口救援仿真', '0');
