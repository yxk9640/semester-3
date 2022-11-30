let $ln:='&#xa;'
let $space:='&#9;'
for $x in distinct-values(doc("reed.xml")//course/subj)
return ('&#xa;',
$x,
count(doc("reed.xml")//course[subj=$x]/crse)
)


(:java -cp saxon.jar net.sf.saxon.Query -q:q3.xq -o:"o3.xml":)
(:java -cp saxon.jar net.sf.saxon.Query -qs:'fn:doc("reed.xml")//course[reg_num=10778]//subj':)
