//функция результатом возвращает внутреннюю функцию

function makeFibs(){
  var a=0, b=1;
  return next;
  function next(){
    b=a+b;
    a=b-a;
    return a;
 }
}
var nextFib=makeFibs();
for(var k=1;k<=15;k++){
  document.write(nextFib()+"|");
}