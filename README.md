#Pheromones

##介绍
Pheromones是一个基于velocity，通过xml配置文件生成多个文本文件的工具。生成的每个文本文件称为PheroNode，PheroNode具有以下特点：

- 每个PheroNode内包含多个其他PheroNode或内置类型的成员，这些成员称为PheroField
- 内置类型包括byte,short,int,long,double,float,string,list
- PheroNode和PheroField均可用key-value形式定义多组参数Property
- PheroNode之间可设定单继承关系，继承的内容为PheroNode的Property
- PheroNode结构简单，不包含复杂的数据处理逻辑

通过自定义模板，并在配置文件中定义Property，可灵活设定所需输出文本格式。

##依赖

clouter-utils.jar：常用工具类集合

##XML配置文件

###参数格式

```
<properties>
	<property key="${name}" value="${value}"/>
	.....
</properties>
```

###配置格式细节

####根节点 configure

####全局参数 properties

|参数名|含义|默认值|
|---|---|---|
|project\_path|要生成文件所在位置的项目绝对路径||
|encode|输出文件编码格式||
|extension|输出文件扩展名||
|vm\_file|默认模板路径(包路径与文件名)|工具自带模板template.vm|
|vmfile\_key|PheroNode中定义模板路径的参数名|vm\_file|
|filename\_key|PheroNode中定义输出文件名的参数名|file\_name|
|relative\_path\_key|PheroNode中定义project\_path下输出文件所在相对路径名的参数名|package\_path|
|output\_key|PheroNode中定义自身是否输出到文件的标识的参数名||

####PheroNode列表

#####根节点 nodes

- \<node resource="xxx.xml"/\> 引用其他xml文件，文件路径相对于本配置文件
- \<node alias="xxxx">....</node>\ 定义一个PheroNode结构
- alias为PheroNode的唯一标识
- 参数可作为node的attribute，也可在node中定义上文中的properties格式

#####PheroNode参数 properties

|参数名|含义|默认值|
|---|---|---|
|src_folder|当全局参数relative_path_key指向的PheroNode参数未定义，则获取该参数|空字符串|
|package|当使用src_folder时，将该参数的值作为包名，转为相对路径名，追加在src_folder之后||