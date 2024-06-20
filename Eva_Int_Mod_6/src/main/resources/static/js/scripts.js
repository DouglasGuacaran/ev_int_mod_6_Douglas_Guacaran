$(document).ready(function(){
    console.log("Estoy en la función")
})

document.getElementById('loginForm').addEventListener('submit', function(event) {
    event.preventDefault();

    var username = document.getElementById('username').value;
    var password = document.getElementById('password').value;

    if (username === '' || password === '') {
        alert('Por favor complete todos los campos.');
        return;
    }
    
});
