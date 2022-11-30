let $ln:='&#xa;'
let $space:='&#9;'
for $x in distinct-values(doc("reed.xml")//course/instructor)
return ($ln,
$x,
count(doc("reed.xml")//course[instructor=$x]/title)
)


(:java -cp saxon.jar net.sf.saxon.Query -q:q4.xq -o:"o4.xml":)
(:java -cp saxon.jar net.sf.saxon.Query -qs:'fn:doc("reed.xml")//course[reg_num=10778]//subj':)
