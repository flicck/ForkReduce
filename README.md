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
在设置线程个数的时候，需要自行计算一下最合适的开启线程数。合适的线程数=cpu可用逻辑核心数/(1-阻塞系数）。阻塞系数的取值在0和1之间。计算操作多
于io操作时时，阻塞系数小。io操作多于计算操作时，阻塞系数大。因此这个线程数不是越大越好的
#### 设置你的forkreduce类的位置  
target=cn.fork.MyForkReduce  
  
然后需要继承ForkReduce接口，实现fork和reduce方法，完成后运行driver类即可
