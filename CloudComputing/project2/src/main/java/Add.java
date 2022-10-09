import java.io.*;
import java.util.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.util.*;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;


class Triple implements Writable {
    public int i;
    public int j;
    public double value;
	
    Triple () {}
	
    Triple ( int i, int j, double v ) { this.i = i; this.j = j; value = v; }
	
    public void write ( DataOutput out ) throws IOException {
        out.writeInt(i);
        out.writeInt(j);
        out.writeDouble(value);

    }

    public void readFields ( DataInput in ) throws IOException {
        i = in.readInt();
        j = in.readInt();
        value = in.readDouble();
    }

    @Override
    public String toString () {
        return ""+i+"\t"+j+"\t"+value;
    }
}

class Block implements Writable {
    final static int rows = 100;
    final static int columns = 100;
    public double[][] data;
    Pair pair = new Pair();
    Triple triple = new Triple();

    Block () {
        data = new double[rows][columns];
    }

    public void write ( DataOutput out ) throws IOException {//serialisation
        Block inBlock = new Block();

        for (int i = 0; i < rows; i++){
            for (int j = 0; j < columns; j++){
                out.writeDouble(data[i][j]);
            }
        }
    }

    public void readFields ( DataInput in ) throws IOException {//deserialisation
        Block outBlock = new Block();
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < columns; j++){
                data[i][j] = in.readDouble();
            }
        }
    }

    @Override
    public String toString () {
        String s = "\n";
        for ( int i = 0; i < rows; i++ ) {
            for ( int j = 0; j < columns; j++ )
                s += String.format("\t%.3f",data[i][j]);
            s += "\n";
        }
        return s;
    }
}

class Pair implements WritableComparable<Pair> {
    public int i;
    public int j;
	
    Pair () {}
	
    Pair ( int i, int j ) { this.i = i; this.j = j; }
	
    public void write ( DataOutput out ) throws IOException {
        out.writeInt(i);
        out.writeInt(j);
    }

    public void readFields ( DataInput in ) throws IOException {
        i = in.readInt();
        j = in.readInt();
    }

    @Override
    public int compareTo ( Pair o ) {
        return this.i == o.i? j-o.j : this.i-o.i; //Logic
    }

    @Override
    public String toString () {
        return ""+i+"\t"+j;
    }
}

public class Add extends Configured implements Tool {
    final static int rows = Block.rows;
    final static int columns = Block.columns;

    /* ... */


    public static class SparseToBlockMapper extends Mapper<Object, Text, Pair, Triple>{
        @Override
        public void map(Object key, Text value,Context context) throws IOException, InterruptedException {
            Scanner inputM = new Scanner(value.toString());
            int i,j;
            Double valueM;
            while ( inputM.hasNext() ){
                String[] keyM = inputM.nextLine().split(",");
                i = Integer.parseInt(keyM[0]);
                j =Integer.parseInt(keyM[1]);
                valueM = Double.parseDouble(keyM[2]);
                context.write(new Pair(Math.round(i/rows),Math.round(j/columns)),new Triple(Math.round(i%rows),Math.round(j%columns),valueM));
            }

            inputM.close();
        }
    }

    public static class SparseToBlockReducer extends Reducer<Pair, Triple, Pair,Block>{
        @Override
        public void reduce(Pair key, Iterable<Triple> values, Context context) throws IOException, InterruptedException {
            Block SparsetoBlock = new Block();
            for ( Triple v: values){
                SparsetoBlock.data[v.i][v.j]= v.value;
            }
            context.write(key, SparsetoBlock); //sequence
        }
    }

      public static class AddMMapper extends Mapper<Pair, Block,Pair, Block>{
        @Override
        public void map(Pair key, Block value,Context context) throws IOException, InterruptedException {
                context.write(key,value);
        }
    }

    public static class AddNMapper extends Mapper<Pair, Block, Pair, Block>{
        @Override
        public void map(Pair key, Block value, Context context) throws IOException, InterruptedException {
            context.write(key,value);
           }
    }

    public static class ResultReducer extends Reducer<Pair,Block,Pair,Block>{
        @Override
        protected void reduce(Pair key, Iterable<Block> values, Context context) throws IOException, InterruptedException {

            Block resBlock = new Block();
//            res.data[0][0]=0;

            for(Block b: values){
                for (int i = 0; i < b.rows; i++){
                    for (int j = 0; j < b.columns; j++){
                        resBlock.data[i][j] += b.data[i][j];
                    }
                }
            }
            context.write(key, resBlock);
        }
    }


    @Override
    public int run ( String[] args ) throws Exception {
        /* ... */

        //job description goes here check for simple and join programs
        Configuration conf = getConf();
        //Sparse to Dense for matrix M
        Job jobM = Job.getInstance(conf,"JobM");
        jobM.setJobName("SparseBlockM");
        jobM.setJarByClass(Add.class);

        jobM.setOutputKeyClass(Pair.class);
        jobM.setOutputValueClass(Block.class);

        jobM.setMapOutputKeyClass(Pair.class);
        jobM.setMapOutputValueClass(Triple.class);

        jobM.setMapperClass(SparseToBlockMapper.class);
        jobM.setReducerClass(SparseToBlockReducer.class);

        jobM.setInputFormatClass(TextInputFormat.class);
        jobM.setOutputFormatClass(SequenceFileOutputFormat.class);// used for sequence
//        jobM.setOutputFormatClass(TextOutputFormat.class);// used for standard
        FileInputFormat.setInputPaths(jobM, new Path(args[0]));
        FileOutputFormat.setOutputPath(jobM, new Path(args[2]));
        jobM.waitForCompletion(true);

        //Sparse to Block matrix-N Block
        Job jobN = Job.getInstance(conf, "jobN");
        jobN.setJobName("SparseBlockN");
        jobN.setJarByClass(Add.class);

        jobN.setOutputKeyClass(Pair.class);
        jobN.setOutputValueClass(Block.class);

        jobN.setMapOutputKeyClass(Pair.class);
        jobN.setMapOutputValueClass(Triple.class);

        jobN.setMapperClass(SparseToBlockMapper.class);
        jobN.setReducerClass(SparseToBlockReducer.class);

        jobN.setInputFormatClass(TextInputFormat.class);
        jobN.setOutputFormatClass(SequenceFileOutputFormat.class);//sequence mode
//        jobN.setOutputFormatClass(TextOutputFormat.class);// used for std mode
        FileInputFormat.setInputPaths(jobN, new Path(args[1]));
        FileOutputFormat.setOutputPath(jobN, new Path(args[3]));
        jobN.waitForCompletion(true) ;


//        Addition
        Job jobResult = Job.getInstance(conf,"jobResult");
        jobResult.setJobName("Addition");
        jobResult.setJarByClass(Add.class);

        jobResult.setOutputKeyClass(Pair.class); //Reducer-Key
        jobResult.setOutputValueClass(Block.class); //Reducer-Value

        jobResult.setMapOutputKeyClass(Pair.class);
        jobResult.setMapOutputValueClass(Block.class);

        jobResult.setReducerClass(ResultReducer.class);
        jobResult.setOutputFormatClass(TextOutputFormat.class);
        MultipleInputs.addInputPath(jobResult,new Path(args[2]+"/part-r-00000"), SequenceFileInputFormat.class,AddMMapper.class);// seq
//        MultipleInputs.addInputPath(jobResult,new Path("left_tmp/part-r-00000"), TextInputFormat.class,AddMMapper.class);
        MultipleInputs.addInputPath(jobResult,new Path(args[3]+"/part-r-00000"), SequenceFileInputFormat.class,AddNMapper.class);//seq
//        MultipleInputs.addInputPath(jobResult,new Path("right_tmp/part-r-00000"), TextInputFormat.class,AddNMapper.class);
        FileOutputFormat.setOutputPath(jobResult,new Path(args[4]));
        jobResult.waitForCompletion(true);

        return 0;
    }

    public static void main ( String[] args ) throws Exception {
    	ToolRunner.run(new Configuration(),new Add(),args);
    }
}
