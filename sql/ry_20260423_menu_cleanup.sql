DELETE m1
FROM sys_menu m1
JOIN sys_menu m2
  ON m1.menu_id > m2.menu_id
 AND IFNULL(m1.parent_id, 0) = IFNULL(m2.parent_id, 0)
 AND IFNULL(m1.menu_name, '') = IFNULL(m2.menu_name, '')
 AND IFNULL(m1.path, '') = IFNULL(m2.path, '')
 AND IFNULL(m1.component, '') = IFNULL(m2.component, '')
 AND IFNULL(m1.perms, '') = IFNULL(m2.perms, '')
WHERE (
    m1.perms LIKE 'system:airport:%'
 OR m1.perms LIKE 'system:boat:%'
 OR m1.perms LIKE 'system:scene:%'
 OR m1.perms LIKE 'system:task:%'
 OR m1.perms LIKE 'system:simulation:%'
 OR m1.component IN (
      'system/airport/index',
      'system/boat/index',
      'system/scene/index',
      'system/task/index',
      'system/simulation/index'
    )
);

DELETE m1
FROM sys_menu m1
JOIN sys_menu m2
  ON m1.menu_id > m2.menu_id
 AND IFNULL(m1.parent_id, 0) = IFNULL(m2.parent_id, 0)
 AND IFNULL(m1.path, '') = IFNULL(m2.path, '')
 AND IFNULL(m1.component, '') = IFNULL(m2.component, '')
WHERE m1.component IN (
    'system/airport/index',
    'system/boat/index',
    'system/scene/index',
    'system/task/index',
    'system/simulation/index'
);

SELECT parent_id, menu_name, path, component, perms, COUNT(*) AS duplicate_count
FROM sys_menu
WHERE (
    perms LIKE 'system:airport:%'
 OR perms LIKE 'system:boat:%'
 OR perms LIKE 'system:scene:%'
 OR perms LIKE 'system:task:%'
 OR perms LIKE 'system:simulation:%'
 OR component IN (
      'system/airport/index',
      'system/boat/index',
      'system/scene/index',
      'system/task/index',
      'system/simulation/index'
    )
)
GROUP BY parent_id, menu_name, path, component, perms
HAVING COUNT(*) > 1;
