-- 无人船仿真系统演示数据
-- 执行顺序：先建表，再执行本脚本
-- 作用范围：uav_airport / uav_boat / sim_scene / task

SET FOREIGN_KEY_CHECKS = 0;

DELETE FROM task WHERE task_code LIKE 'TASK26%';
DELETE FROM uav_boat WHERE boat_code LIKE 'UB26%';
DELETE FROM sim_scene WHERE scene_name IN (
  '青岛近海巡逻区',
  '青岛港口保障区',
  '上海洋山港物流区',
  '上海近海风电运维区',
  '宁波舟山港调度区',
  '厦门近岸搜救区',
  '深圳蛇口港联调区',
  '珠海万山群岛海域'
);
DELETE FROM uav_airport WHERE airport_code IN (
  'AP2601', 'AP2602', 'AP2603', 'AP2604', 'AP2605', 'AP2606'
);

INSERT INTO uav_airport (
  airport_id, airport_code, airport_name, airport_type, province, city, district, address,
  longitude, latitude, altitude, cover_radius, max_uav_count, manager_name, manager_phone,
  status, online_status, remark, create_by, create_time, update_by, update_time, del_flag
) VALUES
(101, 'AP2601', '青岛西海岸无人船基地', '1', '山东省', '青岛市', '黄岛区', '青岛市黄岛区前湾港路88号', 120.212300, 35.960400, 12.50, 8000.00, 30, '王海峰', '13810010001', '0', '0', '近海巡逻与测试基地', 'admin', NOW(), 'admin', NOW(), '0'),
(102, 'AP2602', '上海洋山港无人船基地', '1', '上海市', '上海市', '浦东新区', '上海市浦东新区洋山深水港A区', 121.955700, 30.615800, 8.20, 12000.00, 40, '李志远', '13810010002', '0', '0', '港口调度与物流保障基地', 'admin', NOW(), 'admin', NOW(), '0'),
(103, 'AP2603', '宁波舟山港无人船基地', '1', '浙江省', '宁波市', '北仑区', '宁波市北仑区穿山港区6号', 121.889500, 29.937800, 9.60, 10000.00, 35, '周启明', '13810010003', '0', '0', '港口巡检与引导基地', 'admin', NOW(), 'admin', NOW(), '0'),
(104, 'AP2604', '厦门近海无人船基地', '1', '福建省', '厦门市', '海沧区', '厦门市海沧区港中路18号', 118.032400, 24.484500, 7.80, 9000.00, 28, '陈思敏', '13810010004', '0', '0', '近岸搜救与演练基地', 'admin', NOW(), 'admin', NOW(), '0'),
(105, 'AP2605', '深圳蛇口无人船基地', '1', '广东省', '深圳市', '南山区', '深圳市南山区蛇口港湾大道66号', 113.911500, 22.483200, 6.50, 11000.00, 45, '赵文杰', '13810010005', '0', '0', '港口联调与应急基地', 'admin', NOW(), 'admin', NOW(), '0'),
(106, 'AP2606', '珠海万山无人船基地', '1', '广东省', '珠海市', '香洲区', '珠海市香洲区情侣中路368号', 113.576700, 22.271000, 5.80, 15000.00, 32, '黄嘉豪', '13810010006', '0', '1', '岛礁补给与远海测试基地', 'admin', NOW(), 'admin', NOW(), '0');

INSERT INTO sim_scene (
  scene_id, scene_name, scene_type, description, map_url, weather_conditions, sea_conditions,
  status, remark, create_by, create_time, update_by, update_time, del_flag
) VALUES
(201, '青岛近海巡逻区', '1', '适用于近海巡逻、目标跟踪与航线避障测试。', 'map/qd_patrol.json', '晴', '轻浪', '0', '巡逻主场景', 'admin', NOW(), 'admin', NOW(), '0'),
(202, '青岛港口保障区', '2', '适用于港池安保、进出港引导与电子围栏测试。', 'map/qd_port.json', '多云', '平静', '0', '港口保障场景', 'admin', NOW(), 'admin', NOW(), '0'),
(203, '上海洋山港物流区', '2', '适用于港口物流运输与码头联动作业测试。', 'map/sh_yangshan.json', '阴', '轻浪', '0', '物流运输场景', 'admin', NOW(), 'admin', NOW(), '0'),
(204, '上海近海风电运维区', '1', '适用于海上风电巡检与应急补给仿真。', 'map/sh_windfarm.json', '晴', '中浪', '0', '风电运维场景', 'admin', NOW(), 'admin', NOW(), '0'),
(205, '宁波舟山港调度区', '2', '适用于复杂港道调度与避碰算法验证。', 'map/nbzs_dispatch.json', '小雨', '轻浪', '0', '调度验证场景', 'admin', NOW(), 'admin', NOW(), '0'),
(206, '厦门近岸搜救区', '1', '适用于近岸救援、漂移目标搜索与协同作业。', 'map/xm_rescue.json', '晴', '中浪', '0', '搜救演练场景', 'admin', NOW(), 'admin', NOW(), '0'),
(207, '深圳蛇口港联调区', '2', '适用于港口联调、设备联动与监管巡检。', 'map/sz_shekou.json', '多云', '平静', '0', '联调测试场景', 'admin', NOW(), 'admin', NOW(), '0'),
(208, '珠海万山群岛海域', '3', '适用于远海续航、岛礁补给与复杂海况验证。', 'map/zh_wanshan.json', '阴', '大浪', '0', '远海测试场景', 'admin', NOW(), 'admin', NOW(), '0');

INSERT INTO uav_boat (
  boat_id, boat_code, boat_name, boat_type, airport_id, longitude, latitude, speed, course,
  status, online_status, remark, create_by, create_time, update_by, update_time, del_flag
) VALUES
(301, 'UB2601', '青巡01', '1', 101, 120.220100, 35.968000, 18.50, 90.00, '0', '0', '青岛近海巡逻船', 'admin', NOW(), 'admin', NOW(), '0'),
(302, 'UB2602', '青巡02', '1', 101, 120.225800, 35.972500, 17.20, 135.00, '0', '0', '青岛近海备用巡逻船', 'admin', NOW(), 'admin', NOW(), '0'),
(303, 'UB2603', '青救01', '2', 101, 120.230300, 35.964600, 22.00, 45.00, '0', '0', '青岛快速救援船', 'admin', NOW(), 'admin', NOW(), '0'),
(304, 'UB2604', '洋运01', '2', 102, 121.960200, 30.620100, 16.00, 270.00, '0', '0', '洋山港运输保障船', 'admin', NOW(), 'admin', NOW(), '0'),
(305, 'UB2605', '洋巡01', '1', 102, 121.963500, 30.617600, 19.30, 180.00, '0', '1', '洋山港巡检船', 'admin', NOW(), 'admin', NOW(), '0'),
(306, 'UB2606', '甬调01', '1', 103, 121.892800, 29.942200, 15.80, 315.00, '0', '0', '舟山港调度巡检船', 'admin', NOW(), 'admin', NOW(), '0'),
(307, 'UB2607', '甬保01', '2', 103, 121.896200, 29.938900, 14.50, 120.00, '2', '1', '宁波港保障船，当前维护', 'admin', NOW(), 'admin', NOW(), '0'),
(308, 'UB2608', '厦救01', '2', 104, 118.038600, 24.488800, 21.60, 210.00, '0', '0', '厦门近岸搜救船', 'admin', NOW(), 'admin', NOW(), '0'),
(309, 'UB2609', '厦科01', '3', 104, 118.041100, 24.492300, 12.80, 60.00, '0', '0', '厦门海洋观测船', 'admin', NOW(), 'admin', NOW(), '0'),
(310, 'UB2610', '深巡01', '1', 105, 113.916400, 22.487000, 18.90, 80.00, '0', '0', '蛇口港巡逻船', 'admin', NOW(), 'admin', NOW(), '0'),
(311, 'UB2611', '深救01', '2', 105, 113.920300, 22.481500, 23.40, 160.00, '0', '0', '蛇口港应急救援船', 'admin', NOW(), 'admin', NOW(), '0'),
(312, 'UB2612', '珠远01', '3', 106, 113.581900, 22.275200, 13.60, 25.00, '0', '1', '万山群岛远海测试船', 'admin', NOW(), 'admin', NOW(), '0');

INSERT INTO task (
  task_id, task_code, task_name, task_type, scene_id, boat_id, status, progress, start_time, end_time,
  task_result, task_description, remark, create_by, create_time, update_by, update_time, del_flag
) VALUES
(401, 'TASK2601', '青岛近海日间巡逻', '1', 201, 301, '1', 45, DATE_SUB(NOW(), INTERVAL 40 MINUTE), DATE_ADD(NOW(), INTERVAL 80 MINUTE), NULL, '执行近海常规巡逻与目标识别。', '正在执行', 'admin', NOW(), 'admin', NOW(), '0'),
(402, 'TASK2602', '青岛港池电子围栏巡检', '1', 202, 302, '0', 0, DATE_ADD(NOW(), INTERVAL 1 HOUR), DATE_ADD(NOW(), INTERVAL 3 HOUR), NULL, '校验港池电子围栏与异常闯入告警。', '待执行', 'admin', NOW(), 'admin', NOW(), '0'),
(403, 'TASK2603', '青岛应急落水救援演练', '2', 201, 303, '2', 100, DATE_SUB(NOW(), INTERVAL 4 HOUR), DATE_SUB(NOW(), INTERVAL 2 HOUR), '已完成落水目标搜索与回收演练。', '验证快速搜救和返航流程。', '演练完成', 'admin', NOW(), 'admin', NOW(), '0'),
(404, 'TASK2604', '洋山港集装箱短驳保障', '2', 203, 304, '1', 65, DATE_SUB(NOW(), INTERVAL 90 MINUTE), DATE_ADD(NOW(), INTERVAL 2 HOUR), NULL, '执行港区内短驳运输与路径规避。', '执行中', 'admin', NOW(), 'admin', NOW(), '0'),
(405, 'TASK2605', '洋山港巡检联调', '1', 203, 305, '4', 30, DATE_SUB(NOW(), INTERVAL 3 HOUR), DATE_ADD(NOW(), INTERVAL 1 HOUR), '联调中断，等待设备恢复。', '联调监管设备和巡检接口。', '已暂停', 'admin', NOW(), 'admin', NOW(), '0'),
(406, 'TASK2606', '舟山港主航道调度验证', '1', 205, 306, '0', 0, DATE_ADD(NOW(), INTERVAL 2 HOUR), DATE_ADD(NOW(), INTERVAL 5 HOUR), NULL, '验证主航道调度与避碰策略。', '待执行', 'admin', NOW(), 'admin', NOW(), '0'),
(407, 'TASK2607', '宁波港保障船维护回归', '2', 205, 307, '3', 20, DATE_SUB(NOW(), INTERVAL 6 HOUR), DATE_SUB(NOW(), INTERVAL 5 HOUR), '船只维护状态导致任务终止。', '验证维护状态下的调度限制。', '失败样例', 'admin', NOW(), 'admin', NOW(), '0'),
(408, 'TASK2608', '厦门近岸搜救联合演练', '2', 206, 308, '1', 55, DATE_SUB(NOW(), INTERVAL 50 MINUTE), DATE_ADD(NOW(), INTERVAL 70 MINUTE), NULL, '执行近岸搜救与回传链路联调。', '执行中', 'admin', NOW(), 'admin', NOW(), '0'),
(409, 'TASK2609', '厦门海域水文采样', '3', 206, 309, '2', 100, DATE_SUB(NOW(), INTERVAL 1 DAY), DATE_SUB(NOW(), INTERVAL 22 HOUR), '已完成4个采样点数据采集。', '执行科研采样与数据上报。', '科研样例', 'admin', NOW(), 'admin', NOW(), '0'),
(410, 'TASK2610', '蛇口港监管巡检', '1', 207, 310, '0', 0, DATE_ADD(NOW(), INTERVAL 30 MINUTE), DATE_ADD(NOW(), INTERVAL 2 HOUR), NULL, '执行港区监管设施巡检。', '待执行', 'admin', NOW(), 'admin', NOW(), '0'),
(411, 'TASK2611', '蛇口港应急救援待命', '2', 207, 311, '1', 15, DATE_SUB(NOW(), INTERVAL 20 MINUTE), DATE_ADD(NOW(), INTERVAL 4 HOUR), NULL, '执行应急待命与响应链路检查。', '执行中', 'admin', NOW(), 'admin', NOW(), '0'),
(412, 'TASK2612', '万山群岛远海续航测试', '3', 208, 312, '4', 70, DATE_SUB(NOW(), INTERVAL 5 HOUR), DATE_ADD(NOW(), INTERVAL 6 HOUR), '海况恶化，任务暂停。', '验证远海续航、补给与返航策略。', '暂停样例', 'admin', NOW(), 'admin', NOW(), '0');

SET FOREIGN_KEY_CHECKS = 1;
