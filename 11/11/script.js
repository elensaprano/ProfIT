let userData = [];

class User {
    constructor(name, password){
        this.name = name;
        this.password = password;
    }
}

function signUp () {

    alert('Начинаем регистрацию');
    let userName = prompt("Введите логин");
    let password = hashFunction(prompt("Введите пароль"),16);
    userData.push(new User (userName, password));
    console.log(userData);
}

function signIn () {

    alert("Вход в аккаунт");
    let name = prompt("Введите логин");
    let password = hashFunction(prompt("Введите пароль"),16);
    console.log(password);
    let foundedUser;

    for(let i = 0; i < userData.length; i++) {
       if (userData[i].name == name) {
            foundedUser = userData[i];
            break;
       } 
    }

    if (foundedUser != undefined && foundedUser.password == password) {
        alert ("Вы вошли в свой аккаунт!");
    } else {
        alert ("Введены некорректные данные!");
    }

}

function hashFunction (password, notation) {

    let uniCode = 0;

    for (let i = 0; i < password.length; i++) {

        let code = password.charCodeAt(i);
        uniCode += code;
    }
    return (uniCode % 256).toString(notation);
}