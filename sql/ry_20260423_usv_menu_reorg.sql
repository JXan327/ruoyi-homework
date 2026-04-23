SET @usvRootId := (
  SELECT menu_id
  FROM sys_menu
  WHERE path = 'usv' AND menu_type = 'M'
  ORDER BY menu_id ASC
  LIMIT 1
);

INSERT INTO sys_menu (
  menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type,
  visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
)
SELECT '无人船仿真', 0, 90, 'usv', 'Layout', 1, 0, 'M', '0', '0', '', 'guide', 'admin', NOW(), '', NULL, '无人船仿真目录'
FROM dual
WHERE @usvRootId IS NULL;

SET @usvRootId := (
  SELECT menu_id
  FROM sys_menu
  WHERE path = 'usv' AND menu_type = 'M'
  ORDER BY menu_id ASC
  LIMIT 1
);

UPDATE sys_menu
SET parent_id = @usvRootId
WHERE menu_type = 'C'
  AND (
    path IN ('airport', 'boat', 'scene', 'task', 'simulation', 'analysis')
    OR perms IN (
      'system:airport:list',
      'system:boat:list',
      'system:scene:list',
      'system:task:list',
      'system:simulation:list',
      'system:analysis:list'
    )
  );

UPDATE sys_menu
SET order_num = CASE
  WHEN path = 'airport' THEN 1
  WHEN path = 'boat' THEN 2
  WHEN path = 'scene' THEN 3
  WHEN path = 'task' THEN 4
  WHEN path = 'simulation' THEN 5
  WHEN path = 'analysis' THEN 6
  ELSE order_num
END
WHERE parent_id = @usvRootId
  AND menu_type = 'C';

INSERT INTO sys_menu (
  menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type,
  visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
)
SELECT '数据分析', @usvRootId, 6, 'analysis', 'system/analysis/index', 1, 0, 'C', '0', '0', 'system:analysis:list', 'chart', 'admin', NOW(), '', NULL, '数据分析菜单'
FROM dual
WHERE NOT EXISTS (
  SELECT 1
  FROM sys_menu
  WHERE path = 'analysis' OR perms = 'system:analysis:list'
);
