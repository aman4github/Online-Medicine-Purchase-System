// function validateName() {
//     const fullName = document.getElementById('name').value;
//     const resultElement = document.getElementById('result');
//     resultElement.innerHTML = '';

//     if (fullName.length < 5) {
//         resultElement.innerHTML = 'Full Name should be at least 5 characters.';
//         resultElement.style.color = 'red';
//     } else {
//         resultElement.innerHTML = 'Full Name is valid.';
//         resultElement.style.color = 'green';
//     }
// }

function validateName() {
    const fullName = document.getElementById('name').value;
    const resultElement = document.getElementById('result');
    resultElement.innerHTML = '';

    if (fullName.length < 6) {
        resultElement.innerHTML = 'Full Name should be at least 6 characters.';
        resultElement.style.color = 'red';
    } else if (!fullName.includes(' ')) {
        resultElement.innerHTML = 'Full Name must include a space.';
        resultElement.style.color = 'red';
    } else {
        const nameParts = fullName.split(' ');
        if (nameParts.length < 2) {
            resultElement.innerHTML = 'Full Name must have a word after the space.';
            resultElement.style.color = 'red';
        } else {
            resultElement.innerHTML = 'Full Name is valid.';
            resultElement.style.color = 'green';
        }
    }
}


function validateDOB() {
    const dobInput = document.getElementById('dob').value;
    const resultElement = document.getElementById('result');
    resultElement.innerHTML = '';

    // Parse the date of birth and current date
    const date = new Date(dobInput);
    const currentDate = new Date();

    // Calculate age
    const age = currentDate.getFullYear() - date.getFullYear();

    // Check if the age is at least 18
    if (age < 18) {
        resultElement.textContent = 'You must be at least 18 years old.';
        resultElement.style.color = 'red';
        resultElement.style.display = 'flex';
    } else {
        resultElement.style.display = 'none';
    }
}

// function validatePhoneNumber() {
//     console.log('Function called');
//     const phoneNumber = document.getElementById('phone').value;
//     const resultElement = document.getElementById('result');
//     resultElement.innerHTML = '';
//     const numericPhoneNumber = phoneNumber.replace(/\D/g, '');
//     if (numericPhoneNumber.length === 10) {
//         resultElement.innerHTML = 'Phone number is valid.';
//         resultElement.style.color = 'green';
//     } else {
//         resultElement.innerHTML = 'Please enter a 10-digit phone number.';
//         resultElement.style.color = 'red';
//     }
// }

function validatePhoneNumber() {
    const phoneNumber = document.getElementById('phone').value;
    const resultElement = document.getElementById('result');
    resultElement.innerHTML = '';

    // Remove any non-numeric characters
    const numericPhoneNumber = phoneNumber.replace(/\D/g, '');

    if (numericPhoneNumber.length === 10) {
        resultElement.innerHTML = 'Phone number is valid.';
        resultElement.style.color = 'green';
        // resultElement.style.backgroundColor = '';
    } else {
        resultElement.innerHTML = 'Please enter a 10-digit phone number.';
        resultElement.style.color = 'red';
    }
}

// let phoneTimeout;

// function validatePhoneNumber() {
//     const phoneNumber = document.getElementById('phone').value;
//     const resultElement = document.getElementById('result');
//     resultElement.innerHTML = '';

//     // Clear any previous timeout to ensure only one validation occurs after typing
//     clearTimeout(phoneTimeout);

//     // Add a delay of 500 milliseconds after the user stops typing
//     phoneTimeout = setTimeout(() => {
//         const numericPhoneNumber = phoneNumber.replace(/\D/g, '');
//         if (numericPhoneNumber.length === 10) {
//             resultElement.innerHTML = 'Phone number is valid.';
//             resultElement.style.color = 'green';
//         } else {
//             resultElement.innerHTML = 'Please enter a 10-digit phone number.';
//             resultElement.style.color = 'red';
//         }
//     }, 500);
// }

function clearValidationMessage() {
    const resultElement = document.getElementById('result');
    resultElement.innerHTML = '';
}

document.getElementById('name').addEventListener('focus', clearValidationMessage);
document.getElementById('dob').addEventListener('focus', clearValidationMessage);
document.getElementById('email').addEventListener('focus', clearValidationMessage);
document.getElementById('phone').addEventListener('focus', clearValidationMessage);
document.getElementById('gender').addEventListener('focus', clearValidationMessage);


const form = document.querySelector("form"),
        nextBtn = form.querySelector(".nextBtn"),
        backBtn = form.querySelector(".backBtn"),
        allInput = form.querySelectorAll(".first input");


nextBtn.addEventListener("click", ()=> {
    allInput.forEach(input => {
        if(input.value != ""){
            form.classList.add('secActive');
        }else{
            form.classList.remove('secActive');
        }
    })
})

backBtn.addEventListener("click", () => form.classList.remove('secActive'));

