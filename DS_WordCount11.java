import java.io.IOException;
import java.util.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;
import org.apache.hadoop.util.*;

public class WordCount extends Configured implements Tool 
{
	public static void main(String args[]) throws Exception
	{
    		int res = ToolRunner.run(new WordCount(), args);
    		System.exit(res);
  	}
	public int run(String[] args) throws Exception 
	{
		Path inputPath = new Path(args[0]);
    		Path outputPath = new Path(args[1]);

		Configuration conf = getConf();
    		Job job = new Job(conf, this.getClass().toString());
    		job.setJarByClass(WordCount.class);

    		FileInputFormat.setInputPaths(job, inputPath);
    		FileOutputFormat.setOutputPath(job, outputPath);

    		job.setJobName("WordCount");
  
 		job.setMapperClass(Map.class);
    		job.setCombinerClass(Reduce.class);
    		job.setReducerClass(Reduce.class);
    		job.setMapOutputKeyClass(Text.class);
    		job.setMapOutputValueClass(IntWritable.class);
    		job.setOutputKeyClass(Text.class);
    		job.setOutputValueClass(IntWritable.class);
    		job.setInputFormatClass(TextInputFormat.class);
    		job.setOutputFormatClass(TextOutputFormat.class);
   
   		return job.waitForCompletion(true) ? 0 : 1;
	}

	public static class Map extends Mapper<LongWritable, Text, Text, IntWritable> 
	{
		private final static IntWritable one = new IntWritable(1);
		private Text word = new Text();

    		@Override
    		public void map(LongWritable key, Text value, Mapper.Context context) throws IOException, InterruptedException 
    		{
      			String line = value.toString();
      			StringTokenizer tokenizer = new StringTokenizer(line);
      			while (tokenizer.hasMoreTokens()) 
      			{
        			word.set(tokenizer.nextToken());
        			context.write(word, one);
      			}
    		}
	}

	public static class Reduce extends Reducer<Text, IntWritable, Text, IntWritable> 
	{
		@Override
    		public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException 
    		{
      			int sum = 0;
      			for(IntWritable value : values) 
      			{
        			sum += value.get();
      			}		
			context.write(key, new IntWritable(sum));
    		}		
  	}
}

/*
This code is a Java implementation of the Word Count example in Hadoop MapReduce.
It counts the occurrences of each word in a given input text and produces the word frequencies as output.
Here's an explanation of the code:

- The code begins with the import statements, which import the necessary classes and libraries for Hadoop MapReduce.

- The `WordCount` class extends the `Configured` class and implements the `Tool` interface, which allows it to be executed as a Hadoop job.

- The `main` method serves as the entry point for the program. It calls `ToolRunner.run()`
to execute the `WordCount` class with the provided command-line arguments and exits the program with the return value.

- The `run` method is the implementation of the `Tool` interface's `run()` method.
It configures and runs the MapReduce job.

- Inside the `run` method, the input and output paths are obtained from the command-line arguments.
Then, a new `Configuration` object is created to hold the job configuration.

- The `Job` object is instantiated using the configuration and the class itself as the job class.

- Various settings for the job are defined using methods such as `setJarByClass()`, `setMapperClass()`, `setReducerClass()`, and others.
These methods define the mapper and reducer classes, the input and output formats, the key-value types, and more.

- Finally, the `job.waitForCompletion()` method is called to submit the job for execution and wait for its completion.
The method returns `true` if the job is successful, `false` otherwise.
The return value of the `run()` method is based on the success of the job.

- The `Map` class is the mapper implementation for the Word Count job.
It extends the `Mapper` class and overrides the `map()` method.
In the `map()` method, each input line is tokenized, and each word is emitted as a key with a value of 1.

- The `Reduce` class is the reducer implementation for the Word Count job. It extends the `Reducer` class and overrides the `reduce()` method.
In the `reduce()` method, the values associated with each key are summed up to calculate the total count for that key.

The code follows the typical structure of a Hadoop MapReduce program, with the `main` method starting the job execution,
the `run` method defining the job configuration, and the `Map` and `Reduce` classes implementing the mapper and reducer logic, respectively.
*/
