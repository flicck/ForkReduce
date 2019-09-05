package cn.fork;

import cn.forkReduce.FRContext;
import cn.forkReduce.ForkReduce;

public class MyForkReduce extends ForkReduce{
	private String outkey=null;
	private Long outvalue=null;
	//fork阶段
	@Override
	public void fork(Long position, String line, FRContext frc) {
		String[] splits = line.split(" ");
		for(int i=0;i<splits.length;i++){
			outkey=splits[i];
			outvalue=1L;
			if(i==splits.length-1){
				endflag=true;
			}
			frc.write(outkey, outvalue);
		}	
		
	}
	//reduce阶段
	private  String outkey2=null;
	private  Long outvalue2=null;
	private Long tmpvalue=0L;
	@Override
	public void reduce(String key, Iterable<Object> values, FRContext frc) {
		tmpvalue=0L;
		outkey2=key;
		for(Object o:values){
			tmpvalue=tmpvalue+(Long)o;
		}
		outvalue2=tmpvalue;	
		frc.write(outkey2, outvalue2);
		
	}

}
