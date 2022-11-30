let $ln:='&#xa;'
let $space:='&#9;'
for $x in doc("reed.xml")//course
where $x/subj='MATH'
and $x/place/building = 'LIB'
and $x/place/room = '204'
order by $x/title
return <course>($ln,
$x/title,
$x/instructor,
$x/time/start_time,
$x/time/end_time,  '&#xa;')</course>


(:java -cp saxon.jar net.sf.saxon.Query -q:q1.xq -o:"o1.xml":)
(:java -cp saxon.jar net.sf.saxon.Query -qs:'fn:doc("reed.xml")//course[reg_num=10778]//subj':)
