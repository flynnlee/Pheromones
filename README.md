#Pheromones

##介绍
Pheromones是一个基于velocity，通过xml配置文件生成多个文本文件的工具。生成的每个文本文件称为PheroNode，PheroNode具有以下特点：
- 每个PheroNode内包含多个其他PheroNode或内置类型的成员，这些成员称为PheroField
- 内置类型包括byte,short,int,long,double,float,string,list
- PheroNode和PheroField可用key-value形式定义多组信息Property
- PheroNode之间可设定单继承关系，继承的内容为PheroNode的Property
- PheroNode结构简单，不包含复杂的数据处理逻辑

通过自定义模板，并在配置文件中定义Property，可灵活设定所需输出文本格式。
