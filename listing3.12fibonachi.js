function fibs(n){
  if (n==1||n==2){
    return 1
  } else {
    return fibs(n-1)+fibs(n-2)
  }
}
document.write("<h4>Числа Фибоначчи</h4>")
for(var k=1;k<=20;k++){
  document.write(fibs(k)+" ")
}