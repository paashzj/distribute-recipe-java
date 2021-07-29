# distribute-recipe

## Usage

### Mybatis

```xml

<dependency>
    <groupId>com.github.shoothzj</groupId>
    <artifactId>distribute-impl-mybatis</artifactId>
    <version>0.0.3</version>
</dependency>
```

## 流程

### 加锁流程

```flow
st=>start: Start
lock_op=>operation: Get Lock
lock_failed_op=>operation: Get Lock Failed
is_find_lock=>condition: Find Lock?
is_lock_expire=>condition: Lock Expire?
st->is_find_lock
is_find_lock(no)->lock_op
is_find_lock(yes)->is_lock_expire
is_lock_expire(yes)->lock_op->e
is_lock_expire(no)->lock_failed_op->e
e=>end
```