function AopenConfirmationDialog(title, id) {
    var confirmDialog = document.createElement("div");
    confirmDialog.id = "confirmDialog";
    confirmDialog.className = "modal";
    confirmDialog.innerHTML = `
        <div class="modal-content">
            <p>Are you sure you want to delete listing: ${title}?</p>
            <button class="delete-button" onclick="deleteListing('${id}')">Yes</button>
            <button class="close-button" onclick="closeConfirmationDialog2()">No</button>
        </div>
    `;

    document.body.appendChild(confirmDialog);
}


function closeConfirmationDialog2() {
    var dialog = document.getElementById("confirmDialog");
    if (dialog) {
        dialog.parentNode.removeChild(dialog);
    }
}

async function deleteListing(id) {
    const response = await fetch(`/deleteListing/${id}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        }
    });

    if (response.ok) {
        window.location.href = '/mylistings';
    } else {
        console.error('Deletion failed');
        window.location.href = '/notFound';
    }
}