let $ln:='&#xa;'
let $space:='&#9;'
for $x in distinct-values(doc("reed.xml")//course/title)
return ($ln,
<title>{$x}</title>,
$ln,$space,<instructor>{distinct-values(doc("reed.xml")//course[title=$x]/instructor)}</instructor>
)

(:java -cp saxon.jar net.sf.saxon.Query -q:q2.xq -o:"o2.xml":)
(:java -cp saxon.jar net.sf.saxon.Query -qs:'fn:doc("reed.xml")//course[reg_num=10778]//subj':)
