function validateAge() {
	let birthDateInput = document.getElementById('birthDate');
	let birthDate = new Date(birthDateInput.value);
	let today = new Date();
	const minAge = 18;

	let age = today.getFullYear() - birthDate.getFullYear();
	let monthDiff = today.getMonth() - birthDate.getMonth();

	if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birthDate.getDate())) {
		age--;
	}

	if (age < minAge) {
		alert('You must be at least 18 years old.');
		return false;
	}
	return true;
}
function nextStep(showId, hideId) {
	document.getElementById(hideId).style.display = "none";
	document.getElementById(showId).style.display = "block";
}
function hideStep2() {
	document.getElementById('step2').style.display = "none";
}

document.addEventListener('DOMContentLoaded',
function() {
	var nextButton = document.getElementById('nextButton');
	var requiredFields = document.querySelectorAll('#step1 [required]');

	nextButton.addEventListener('click',
	function() {
		var allFieldsValid = Array.from(requiredFields).every(function(field) {
			return field.checkValidity();
		});

		if (allFieldsValid && validateAge()) {
			nextStep('step2', 'step1');
		} else {
			alert('Please fill in all required fields.');
		}
	});
});
function checkPass() {
	var password = document.getElementById('password').value;
	var password2 = document.getElementById('password2').value;
	if (password2 != password) {
		alert('Passwords do not match!');
		return false;
	}
	return true;
}
function showAlert() {
	alert('Button clicked!');
}