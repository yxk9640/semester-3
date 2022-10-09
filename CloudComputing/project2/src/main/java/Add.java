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
    Pair p = new Pair();
    Triple t = new Triple();

    Block () {
        data = new double[rows][columns];
    }

    public void write ( DataOutput out ) throws IOException {

        out.writeDouble(data[p.i][p.j]);
    }

    public void readFields ( DataInput in ) throws IOException {
        data[t.i][t.j] = in.readDouble();
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
        return i == o.i? j-o.j : i-o.i;// check for logical errors
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
//N matrix mapper
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
            Block dm = new Block();
            for ( Triple v: values){
                   dm.data[v.i][v.j]= v.value;
            }
            context.write(key, dm);

        }
    }



    public static class AddMMapper extends Mapper<Object, Text, Pair, Block>{
        @Override
        public void map(Object key, Text value,Context context) throws IOException, InterruptedException {
            context.write(new Pair(), new Block());
        }
    }

    public static class AddNMapper extends Mapper<Object, Text, Pair, Block>{
        @Override
        public void map(Object key, Text value,Context context) throws IOException, InterruptedException {
            context.write(new Pair(),new Block());
           }
    }

    public static class ResultReducer extends Reducer<Pair,Block,Pair,Block>{
        @Override
        public void reduce(Pair key, Iterable<Block> values, Context context)
                throws IOException, InterruptedException {
            Block blockM = new Block();

            Block blockN = new Block();

            for (Block b:values){
                blockM.data = b.data;
            }

//            for (int i = 0; i < blockM.data.length; i++){
//                for (int j = 0; j < blockN.data.length; j++){
//                        blockM.data[i][j] = blockN.data[i][j];
//                }
//            }
            context.write(key,blockM);
        }
    }



    @Override
    public int run ( String[] args ) throws Exception {
        /* ... */
        //job description goes here check for simple and join programs
        //Mapper job for matrix M
        Job jobM = Job.getInstance();
        jobM.setJobName("SparseBlockM");
        jobM.setJarByClass(Add.class);

        jobM.setOutputKeyClass(Pair.class);
        jobM.setOutputValueClass(Block.class);

        jobM.setMapOutputKeyClass(Pair.class);
        jobM.setMapOutputValueClass(Triple.class);

        jobM.setMapperClass(SparseToBlockMapper.class);
        jobM.setReducerClass(SparseToBlockReducer.class);

        jobM.setInputFormatClass(TextInputFormat.class);
        jobM.setOutputFormatClass(TextOutputFormat.class);
        FileInputFormat.setInputPaths(jobM, new Path(args[0]));
        FileOutputFormat.setOutputPath(jobM, new Path(args[2]));
        jobM.waitForCompletion(true);

        Job jobN = Job.getInstance();
        jobN.setJobName("SparseBlockN");
        jobN.setJarByClass(Add.class);

        jobN.setOutputKeyClass(Pair.class);
        jobN.setOutputValueClass(Block.class);

        jobN.setMapOutputKeyClass(Pair.class);
        jobN.setMapOutputValueClass(Triple.class);

        jobN.setMapperClass(SparseToBlockMapper.class);
        jobN.setReducerClass(SparseToBlockReducer.class);

        jobN.setInputFormatClass(TextInputFormat.class);
        jobN.setOutputFormatClass(TextOutputFormat.class);
        FileInputFormat.setInputPaths(jobN, new Path(args[1]));
        FileOutputFormat.setOutputPath(jobN, new Path(args[3]));
        jobN.waitForCompletion(true) ;



        //Mapper job for matrix Block
        Job jobResult = Job.getInstance();
        jobResult.setJobName("Addition");
        jobResult.setJarByClass(Add.class);

        jobResult.setOutputKeyClass(Pair.class); //Reducer
        jobResult.setOutputValueClass(Block.class); //Reducer

        jobResult.setMapOutputKeyClass(Pair.class);
        jobResult.setMapOutputValueClass(Block.class);

        jobResult.setReducerClass(ResultReducer.class);
        jobResult.setOutputFormatClass(TextOutputFormat.class);
        MultipleInputs.addInputPath(jobResult,new Path("left_tmp/part-r-00000"),TextInputFormat.class,AddMMapper.class);
        MultipleInputs.addInputPath(jobResult,new Path("right_tmp/part-r-00000"),TextInputFormat.class,AddNMapper.class);
        FileOutputFormat.setOutputPath(jobResult,new Path(args[4]));
        jobResult.waitForCompletion(true);
        return 0;

    }

    public static void main ( String[] args ) throws Exception {
    	ToolRunner.run(new Configuration(),new Add(),args);
    }
}