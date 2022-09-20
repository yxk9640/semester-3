import java.io.*;
import java.util.Formatter;
import java.util.Scanner;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;


public class Netflix {
    /* ... */
        public static class NetflixMapper extends Mapper<Object, Text, IntWritable, IntWritable> {
            @Override
            public void map(Object key, Text value, Context context)
                throws IOException, InterruptedException {
                Scanner input = new Scanner(value.toString());
                int user, rating;

                while ( input.hasNext() ){
                    String[] x2;
                    String x = input.nextLine();
                    if (!x.endsWith(":")) {
                        x2 = x.split(",");
                        user = Integer.parseInt(x2[0]);
                        rating = Integer.parseInt(x2[1]);
                        context.write(new IntWritable(user), new IntWritable(rating));
                    }
                }
                input.close();
            }
        }
        public static class NetflixReducer extends Reducer<IntWritable,IntWritable,IntWritable,IntWritable> {
            @Override
            public void reduce(IntWritable key, Iterable<IntWritable> values, Context context)
                    throws IOException, InterruptedException {
                int count = 0;
                double sum = 0;
                for (IntWritable n : values){
                    count++;
                    sum+=n.get();
                }

                context.write(key, new IntWritable((int) ((sum / count) * 10)) );

            }
        }

    public static class NetflixMapper2 extends Mapper<Object, Text, DoubleWritable, IntWritable> {
        @Override
        public void map(Object key, Text value, Context context)
                throws IOException, InterruptedException {
            Scanner input2 = new Scanner(value.toString());
            int rating;
            String str;
            String[] str2;
            while ( input2.hasNext() ){
                str = input2.nextLine();
                str2 = str.split("\t");
                rating = Integer.parseInt(str2[1]);
                context.write(new DoubleWritable(rating/10.0), new IntWritable(1));
            }
            input2.close();
        }
    }
    public static class NetflixReducer2 extends Reducer<DoubleWritable,IntWritable,DoubleWritable,IntWritable> {
        @Override
        public void reduce(DoubleWritable key, Iterable<IntWritable> values, Context context)
                throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable v : values){
                sum+=v.get();
            }
            context.write(key,new IntWritable(sum) );
        }
    }



    public static void main ( String[] args ) throws Exception {
        /* ... */
        Job job1 = Job.getInstance();
        job1.setJobName("Netflix_yxk9640_1");
        job1.setJarByClass(Netflix.class);

        job1.setOutputKeyClass(IntWritable.class); //
        job1.setOutputValueClass(DoubleWritable.class);  // Reducer - 1 output

        job1.setMapOutputKeyClass(IntWritable.class);  //
        job1.setMapOutputValueClass(IntWritable.class);  //

        job1.setMapperClass(NetflixMapper.class);
        job1.setReducerClass(NetflixReducer.class);

        job1.setInputFormatClass(TextInputFormat.class);
        job1.setOutputFormatClass(TextOutputFormat.class);
        FileInputFormat.setInputPaths(job1, new Path(args[0]));
        FileOutputFormat.setOutputPath(job1, new Path(args[1]));
        job1.waitForCompletion(true);

//        ------- JOB - 2 -------
        Job job2 = Job.getInstance();
        job2.setJobName("Netflix_yxk9640_2");
        job2.setJarByClass(Netflix.class);

        job2.setOutputKeyClass(DoubleWritable.class);
        job2.setOutputValueClass(IntWritable.class);

        job2.setMapOutputKeyClass(DoubleWritable.class);
        job2.setMapOutputValueClass(IntWritable.class);

        job2.setMapperClass(NetflixMapper2.class);
        job2.setReducerClass(NetflixReducer2.class);

        job2.setInputFormatClass(TextInputFormat.class);
        job2.setOutputFormatClass(TextOutputFormat.class);
        FileInputFormat.setInputPaths(job2, new Path("temp/part-r-00000"));
        FileOutputFormat.setOutputPath(job2, new Path(args[2]));
        job2.waitForCompletion(true);


    }


}
