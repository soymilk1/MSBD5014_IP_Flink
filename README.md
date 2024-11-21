# MSBD5014 - Independent Project

## 思路：
* 分别读取Customer，Order，Lineitem三个流
* 三个流自上而下(Customer -> Orders -> Lineitem)判断tuple是否alive，
将alive的数据保存在状态内
* 聚合，从状态内拿到alive的函数进行聚合操作
