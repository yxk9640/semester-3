<instructor>{
let $ln:='&#xa;'
let $space:='&#9;'
for $x in distinct-values(doc("reed.xml")//course/instructor)
return ($ln,
<instructor_Name>{$x}</instructor_Name>,$ln,
$ln,doc("reed.xml")//course[instructor=$x]/title
)
}</instructor>



(:java -cp saxon.jar net.sf.saxon.Query -q:q5.xq -o:"o5.xml":)
(:java -cp saxon.jar net.sf.saxon.Query -qs:'fn:doc("reed.xml")//course[reg_num=10778]//subj':)
