文件目录
- apachedbutil<br>
  apache 的数据库工具类
- dao<br>
  读写数据库
    - BatchDaoImpl<br>
      批处理
    - EmpDaoImpl<br>
      使用 Statement 类读写数据库
    - EmpDaoImpl<br>
      使用 PreparedStatement 类读写数据库
- entity<br>
  实体类
- pool<br>
  数据库连接池的使用，包括
    - c3p0
    - dbcp
    - durid
    - hikaricp
- reflect<br>
    - 反射的基本使用
    - 使用反射进行数据库读写
- util<br>
  自定的数据库工具类，用于 oracle 和 mysql 的建立连接和关闭连接。
