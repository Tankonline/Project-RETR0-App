-- Local verification fixture for migrations 0014/0005.
-- Run with sqlite3 against a disposable database; this never touches D1.
SELECT name, sql
FROM sqlite_master
WHERE type IN ('table', 'index')
  AND (name = 'accountless_save_uploads'
    OR name LIKE 'idx_accountless_save_uploads_%')
ORDER BY type, name;

SELECT COUNT(*) AS empty_table_count FROM accountless_save_uploads;
