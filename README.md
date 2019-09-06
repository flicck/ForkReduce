# ForkReduce
ForkReduce是模仿MapReduce的shuffle过程用线程池和protobuf实现的单机多线程版本，可以自定义线程数量，目前还比较初级  
forkProperties配置文件主要内容如下，需要放在resources目录下 
#### 设定你的输入文件位置  
input=C:/Users/stawind/Desktop/text/1.txt  
#### 设置你的输出文件目录  
output=C:/Users/stawind/Desktop/output  
#### 设置你的tmp文件目录--》存放split后合并的partition文件,运行结束后文件会被删除  
tmp=C:/Users/stawind/Desktop/tmp  
#### 设置你的reduce线程个数  
num=10  
#### 设置你的forkreduce类的位置  
target=cn.fork.MyForkReduce  