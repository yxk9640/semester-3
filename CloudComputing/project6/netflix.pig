MOVIE = LOAD '$G' USING PigStorage(':') AS ( movie:int);
USER = LOAD '$G' USING PigStorage(',') AS ( user:int,rating:double);
usr = FILTER USER BY user is not null;
usrF = group usr by user;
--E = foreach V generate SUM(MOVIE.x);


--count = foreach usr generate COUNT(te.user);
--SUM = foreach usr generate SUM(te.rating);
sum = foreach usrF generate SUM(usr.rating)/COUNT(usr.rating) AS (avg:double);
sumDeci = foreach sum generate FLOOR(avg*10)/10 as (avg:double);
avgDeci = group sumDeci BY avg;
O = foreach avgDeci generate group,(int)COUNT(sumDeci.avg) as count;
--final = foreach avg generate group,(int)COUNT(sum.avg) as count;
STORE O INTO '$O' USING PigStorage (' ');