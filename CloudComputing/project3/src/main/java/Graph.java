import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import static java.lang.Math.min;


class Vertex implements Writable {
    public short tag;           // 0 for a graph vertex, 1 for a group number
    public long group;          // the group where this vertex belongs to
    public long VID;            // the vertex ID
    public long[] adjacent;     // the vertex neighbors
    public int adjaLen;
    /* ... */
    Vertex(){
        this.tag = 0;
        this.group = 0;
        this.VID = 0;
        this.adjacent = new long[0];
        this.adjaLen = 0;
    }
    Vertex( short tag, long group,long VID, long[] adja){
        this.tag = tag;
        this.group = group;
        this.VID = VID;
        this.adjacent = adja;

    }
//    Vertex( short tag, long group,long VID, long[] adja,int adjaLen){
//        this.tag = tag;
//        this.group = group;
//        this.VID = VID;
//        this.adjacent = adja;
//        this.adjaLen = adjaLen;
//
//    }


    Vertex(short tag, long group){
        this.tag = tag;
        this.group = group;
        this.adjacent = new long[0];
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeShort(tag);
        out.writeLong(group);
        out.writeLong(VID);
        out.writeInt(adjacent.length);
        for (int i = 0; i < adjacent.length; i++)
            out.writeLong(adjacent[i]);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        tag = in.readShort();
        group = in.readLong();
        VID = in.readLong();
        adjaLen =in.readInt();
        long[] temp = new long[adjaLen];
        for (int i = 0; i < adjaLen;i++)
            temp[i] = in.readLong();
        adjacent = temp;

    }
    @Override
    public String toString() {
        System.out.println(adjaLen);
        return this.tag+" "+this.group+" "+this.VID+" "+ Arrays.toString(this.adjacent) ;
    }
}

public class Graph {

    /* ... */
    public static class FirstMap extends Mapper<Object, Text, LongWritable, Vertex>{
        @Override
        public void map(Object key, Text value,Context context) throws IOException, InterruptedException {
            Scanner input = new Scanner(value.toString());
            long VID;
            while (input.hasNext()){
                String[] KeyVal = input.nextLine().split(",");
                VID = Long.parseLong(KeyVal[0]);
                long[] adja = new long[KeyVal.length-1];
                for( int i =1; i<KeyVal.length;i++ ){
                    adja[i-1]=(Long.parseLong(KeyVal[i]));
                }
                int adjaLen = KeyVal.length;
                context.write(new LongWritable(VID),new Vertex((short) 0,VID,VID,adja));
            }
            input.close();
        }
    }
//    public static class FirstReduce extends Reducer<LongWritable,Vertex,LongWritable,Vertex> {
//        @Override
//        public void reduce(LongWritable key, Iterable<Vertex> values, Reducer<LongWritable, Vertex, LongWritable, Vertex>.Context context) throws IOException, InterruptedException {
//            Vertex v = new Vertex(values);
//
//            context.write(key,v);
//        }
//    }

        public static class SecondMap extends Mapper<LongWritable, Vertex, LongWritable, Vertex>{
        @Override
        public void map(LongWritable key, Vertex value, Context context) throws IOException, InterruptedException {
            context.write(new LongWritable(value.VID), value);


            for (long n: value.adjacent){
                context.write(new LongWritable(n), new Vertex((short) 1, value.group));
            }
        }
    }

    public static class SecondReduce extends Reducer<LongWritable,Vertex,LongWritable,Vertex>{
        @Override
        public void reduce(LongWritable key, Iterable<Vertex> values,Context context) throws IOException, InterruptedException {
            //check group number of all vertex and replace with minimum value

            long mini = Long.MAX_VALUE;
            long[] adj = null;
            for (Vertex v: values){
                if (v.tag ==0){
                    adj= v.adjacent.clone();
                }
                mini = Math.min(mini,v.group);
            }
            context.write(new LongWritable(mini),new Vertex((short) 0,mini, key.get(), adj));
        }
    }

    public static class FinalMap extends Mapper<LongWritable,Vertex,LongWritable,IntWritable>{
        @Override
        public void map(LongWritable key, Vertex value, Context context) throws IOException, InterruptedException {
            long group = value.group;
            context.write(new LongWritable(group),new IntWritable(1));
        }
    }

    public static class FinalReduce extends Reducer<LongWritable,IntWritable,LongWritable,IntWritable>{
        @Override
        public void reduce(LongWritable key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
             int m = 0;
            for ( IntWritable v:values){
                m += v.get();
            }
            context.write(key, new IntWritable(m));
        }
    }



        public static void main ( String[] args ) throws Exception {
        Job job = Job.getInstance();
        job.setJobName("Graph");
        /* ... First Map-Reduce job to read the graph */
            job.setJarByClass(Graph.class);

            job.setOutputKeyClass(LongWritable.class);
            job.setOutputValueClass(Vertex.class);


            job.setMapOutputKeyClass(LongWritable.class);
            job.setMapOutputValueClass(Vertex.class);

            job.setMapperClass(FirstMap.class);
//            job.setReducerClass(FirstReduce.class);

            job.setInputFormatClass(TextInputFormat.class);
            job.setOutputFormatClass(SequenceFileOutputFormat.class);// used for sequence
//            job.setOutputFormatClass(TextOutputFormat.class);// used for standard
            FileInputFormat.setInputPaths(job, new Path(args[0]));
            FileOutputFormat.setOutputPath(job, new Path(args[1]+"/f0"));
            job.waitForCompletion(true);

        for ( short i = 0; i < 5; i++ ) {
            job = Job.getInstance();
            /* ... Second Map-Reduce job to propagate the group number */
            job.setJarByClass(Graph.class);

            job.setOutputKeyClass(LongWritable.class);
            job.setOutputValueClass(Vertex.class);

            job.setMapOutputKeyClass(LongWritable.class);
            job.setMapOutputValueClass(Vertex.class);

            job.setMapperClass(SecondMap.class);
            job.setReducerClass(SecondReduce.class);

            job.setInputFormatClass(SequenceFileInputFormat.class);
            job.setOutputFormatClass(SequenceFileOutputFormat.class);// used for sequence
//            job.setOutputFormatClass(TextOutputFormat.class);// used for standard0
            FileInputFormat.setInputPaths(job, new Path(args[1]+"/f"+(i)));
            FileOutputFormat.setOutputPath(job, new Path(args[1]+"/f"+(i+1)));

            job.waitForCompletion(true);
        }
        job = Job.getInstance();
        /* ... Final Map-Reduce job to calculate the connected component sizes */
            job.setJarByClass(Graph.class);

            job.setOutputKeyClass(LongWritable.class);
            job.setOutputValueClass(IntWritable.class);

            job.setMapOutputKeyClass(LongWritable.class);
            job.setMapOutputValueClass(IntWritable.class);

            job.setMapperClass(FinalMap.class);
            job.setReducerClass(FinalReduce.class);

            job.setInputFormatClass(SequenceFileInputFormat.class);
//                        job.setOutputFormatClass(SequenceFileOutputFormat.class);// used for sequence
            job.setOutputFormatClass(TextOutputFormat.class);// used for standard
            FileInputFormat.setInputPaths(job, new Path(args[1]+"/f5/part-r-00000"));
            FileOutputFormat.setOutputPath(job, new Path(args[2]));
        job.waitForCompletion(true);
    }
}
