# Hadoop-MapReduce-JAVA
Project setup have configured Hadoop cluster in AWS EC2 instance, and cluster have one master node and two slave nodes. Agenda of this project is to take a large dataset and reduce it using the Hadoop jobs and then represent the large data using the bar and the pie charts for better analysis for the user. The dataset used in our application is not specific, any large dataset can be run on the Hadoop jobs for the required output. 

Below is output format of HDFS of my "Hadoop Mapreduce JAVA" API project.


state   Republican Democratic Total Votes 2016 Total Votes 2012
['AL',  718084,	   1306925,      2078165,	     2064699]
['AR',	378729,	   677904,       1108615,	     1062831]
['AZ',	936250,	   1021154,      2062810,	     2041519]
['CA',	7230699,   3841134,     11733523,	     10538656]

We have considered the US Voting Poll data as our dataset. The entire data is around 4000 records and over 70 columns. We have the data in the form of .csv file. The csv file is directly imported to the Hadoop cluster by the master and then sent to the mapper and later to the reducer. The data is then categorized into four categories: Democratic, Republican, US Voting Poll 2012 and US Voting Poll 2016. The categorized data is then represented in the form of the charts along with the Hadoop jobs generated output of the sorted data.



