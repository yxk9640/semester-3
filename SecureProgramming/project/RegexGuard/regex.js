// const name = 'Bruce Schneier'

var i = 0;
const pattern = new RegExp(/(^[+]*[-\s(\s\.0-9]{0,2}[-\s\.0-9]{1,3}[\s)\s]{0,3}[-\s\.0-9]*$)|([0-9]{5,})/);
// console.log(++i + "." + pattern.test("12345"));
// console.log(++i + "." + pattern.test("(703)111-2121"));
// console.log(++i + "." + pattern.test("123-1234"));
// console.log(++i + "." + pattern.test("+1(703)111-2121"));
// console.log(++i + "." + pattern.test("+32(21)212-2324"));
// console.log(++i + "." + pattern.test("1 (703) 123-1234"));
// console.log(++i + "." + pattern.test("011 701 111 1234"));
// console.log(++i + "." + pattern.test("12345.12345"));
// console.log(++i + "." + pattern.test("011 1 703 111 1234"));

// console.log("------ False -----");
// console.log(++i + "." + pattern.test("123"));
// console.log(++i + "." + pattern.test("1/703/123/1234"));
// console.log(++i + "." + pattern.test("Nr 102-123-1234"));
// console.log(++i + "." + pattern.test("<script>alert(“XSS”)</script>"));
// console.log(++i + "." + pattern.test("7031111234"));
// console.log(++i + "." + pattern.test("+1234 (201) 123-1234"));
// console.log(++i + "." + pattern.test("(001) 123-1234"));
// console.log(++i + "." + pattern.test("+01 (703)  123-1234"));
// console.log(++i + "." + pattern.test("(703) 123-1234 ext 204"));


const Namepattern = new RegExp(/^([a-z]|[A-Z]|[\s]|,|[?!.*']|-)*$/);
console.log(Namepattern.test("Bruce Schneier"));
console.log(Namepattern.test("Schneier, Bruce"));
console.log(Namepattern.test("Schneier, Bruce Wayne"));
console.log(Namepattern.test("O'Malley, JohnF"));
console.log(Namepattern.test("John O'Malley-Smith"));
console.log(Namepattern.test("Cher"));


console.log("------ False -----");
console.log(Namepattern.test("John O''MalleySmith"));
console.log(Namepattern.test("<Script>alert(“XSS”)</Script>"));
console.log(Namepattern.test("select * from users; "));