$(document).ready(function(){
    console.log("Estoy en la función")
})

document.getElementById('loginForm').addEventListener('submit', function(event) {
    event.preventDefault();

    var email = document.getElementById('email').value;
    var password = document.getElementById('password').value;

    if (email === '' || password === '') {
        alert('Por favor complete todos los campos.');
        return;
    }
        
});
