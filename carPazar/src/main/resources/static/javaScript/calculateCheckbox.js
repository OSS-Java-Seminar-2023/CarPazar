let equipmentCheckboxes = document.getElementsByName("additional-equipment");
let extrasCheckboxes = document.getElementsByName("extra-features");

let equipmentVar = document.getElementsByName("additionalEquipment")[0];
let extrasVar = document.getElementsByName("extraFeatures")[0];

function onChangeEquip() {
	let totalEquip = 0;
	equipmentCheckboxes.forEach(function(checkbox) {
		if (checkbox.checked) {
			totalEquip += Math.pow(2, parseInt(checkbox.value));
		}
	});
	equipmentVar.value = totalEquip;

	let totalExtras = 0;
	extrasCheckboxes.forEach(function(checkbox) {
		if (checkbox.checked) {
			totalExtras += Math.pow(2, parseInt(checkbox.value));
		}
	});
	extrasVar.value = totalExtras;
}