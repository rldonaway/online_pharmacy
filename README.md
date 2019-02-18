# Insight Data Engineering
## Online Pharmacy Challenge

#### Robert L. Donaway
#### rldonaway@gmail.com
#### 2/17/2019

This project, written in Java 8, contains my solution for the challenge to write code
that will read a large file and run a query to obtain aggregate data for an
online pharmacy.

I chose not to use any third-party libraries to accomplish this, since I assumed that
the point was to demonstrate our ability to write reusable, well factored code. If
I were solving this problem under normal circumstances, I would use existing libaries
that do a much better job with file IO and CSV parsing than I did. Some CSV libraries
can handle more complicated issues with quoted strings and commas. I know this is
a more basic problem than we would be solving at Insight, but still I found it an
enjoyable thing to do. Thank you for the opportunity!

The basic design was to have one class that produced the query output that did not
depend on input or output methods. Designing it this way, among other things, makes
it easier to test. The class implementing the query is `DrugPrescriberQuery`. It 
implements the `Query` interface so that other queries with this interface can be 
used with the input and output (I/O) classes. The I/O classes implement 
`InputResource` and `OutputResource` so that the implementation does not depend on 
using files. For example, the data might be streamed over the network. The concrete
resource classes used in this project are `FileInputBasic` and `FileOutputBasic`. 

## Setup
The project also does not use any third-party build tools. In a more realistic 
project, I would like to use Maven but that requires a download and installation. 
Instead, I am using a simple shell script to compile and run the project.

