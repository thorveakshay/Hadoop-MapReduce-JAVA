package com.cloud.akshay;


import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;
import java.io.IOException;
import java.util.*;


public class HadoopMapReduceCore extends Configured implements Tool{
  public static class MapClass extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text>{
    private Text state = new Text();
    private Text votes = new Text();

    @Override
    public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter) throws IOException{
      String[] rows = value.toString().split(",");
      if(rows.length > 31){
    	String states = rows[10];  
        String dem = rows[3];
        String rep = rows[4];
        String tolal2016 = rows[5];
        String tolal2012 = rows[13];

      

        state.set(states);
        votes.set(dem + "\t" + rep + "\t" + tolal2016 + "\t" + tolal2012); //numTotal, numRated, rating
        output.collect(state, votes);
      }
    }
  }

  public static class Reduce extends MapReduceBase implements Reducer<Text, Text, Text, Text>
  {
    @Override
    public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, Text> output, Reporter reporter) throws IOException{
    	 int sum_dem = 0;
         int sum_rep = 0; 
         int sum_total2016 = 0;
         int sum_total2012 = 0;

      while(values.hasNext()){
        String tokens[] = (values.next().toString()).split("\t");
        int dem = Integer.parseInt(tokens[0]);
        int rep = Integer.parseInt(tokens[1]); //gets number of markets
        int total2016 = Integer.parseInt(tokens[2]);
        int total2012 = Integer.parseInt(tokens[3]);//gets rating

        sum_dem= sum_dem+dem;
        sum_rep=sum_rep + rep;
        sum_total2016= sum_total2016+ total2016;
        sum_total2012= sum_total2012+ total2012;
        
        
      }

      
        output.collect(key, new Text(sum_dem + "\t" + sum_rep + "\t" + sum_total2016 + "\t" + sum_total2012));
    }
  }

  static int printUsage(){
    System.out.println("HadoopDataRepresentation [-m <maps>] [-r <reduces>] <input> <output>");
    return 0;
  }

  @Override
  public int run(String[] args) throws IOException
  {
    return 0;
  }

  public static void main(String[] args) throws IOException{
    JobConf conf = new JobConf(HadoopMapReduceCore.class);
    conf.setJobName("HadoopDataRepresentation");

    conf.setOutputKeyClass(Text.class);
    conf.setOutputValueClass(Text.class);

    conf.setMapperClass(MapClass.class);
    conf.setReducerClass(Reduce.class);

    conf.setInputFormat(TextInputFormat.class);
    conf.setOutputFormat(TextOutputFormat.class);

    FileInputFormat.setInputPaths(conf, new Path(args[0]));
    FileOutputFormat.setOutputPath(conf, new Path(args[1]));

    JobClient.runJob(conf);
  }
}
