## 建表语句
```sql
CREATE TABLE distribute_lock (
  lock_key VARCHAR(64),
  lock_id VARCHAR(64),
  expire_time timestamp,
  primary key (lock_key)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```