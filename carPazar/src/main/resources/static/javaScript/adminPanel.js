function openConfirmationDialog(username) {
    var confirmDialog = document.createElement("div");
    confirmDialog.id = "confirmDialog";
    confirmDialog.className = "modal";
    confirmDialog.innerHTML = `
        <div class="modal-content">
            <p>Are you sure you want to delete user: ${username}?</p>
            <button class="delete-button" onclick="deleteUser('${username}')">Yes</button>
            <button class="close-button" onclick="closeConfirmationDialog()">No</button>
        </div>
    `;

    document.body.appendChild(confirmDialog);
}


function closeConfirmationDialog() {
    var dialog = document.getElementById("confirmDialog");
    if (dialog) {
        dialog.parentNode.removeChild(dialog);
    }
}

async function deleteUser(username) {
    const response = await fetch(`/deleteUser/${username}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        }
    });

    if (response.ok) {
        window.location.href = '/adminPanel';
    } else {
        console.error('Deletion failed');
        window.location.href = '/notFound';
    }
}
