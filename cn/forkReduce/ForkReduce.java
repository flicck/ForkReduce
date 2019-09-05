package cn.forkReduce;

public class ForkReduce {
	
	private String outkey=null;
	private Long outvalue=null;
	public static boolean flag=false;
	public void fork(Long position,String line,FRContext frc){
		String[] splits = line.split(" ");
		for(int i=0;i<splits.length;i++){
			outkey=splits[i];
			outvalue=1L;
			if(i==splits.length-1){
				flag=true;
			}
			frc.write(outkey, outvalue);
		}
		
		
	}
	public void reduce(String key,Iterable<Long> values,FRContext frc){
		
	}
}
